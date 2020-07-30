package com.home.httpsrv.crawler_srv.services

import com.home.http_crawler.http_client.api.HttpClient
import com.home.http_crawler.http_client.model.{FailedHttpResponse, SuccessfulHttpResponse}
import com.home.httpsrv.crawler_srv.model.{CrawlerError, CrawlerResult}
import com.home.httpsrv.web_page_parser.services.WebPageParser
import zio._

object CrawlerService {

  type CrawlerService = Has[Service]

  trait Service {
    def getTitles(urls: Seq[String]): IO[CrawlerError, CrawlerResult]
  }

  private[crawler_srv] val live =
    ZLayer.fromServices[HttpClient.Service, WebPageParser.Service, Service] { (httpClient, pageParser) =>
      new Service {
        override def getTitles(urls: Seq[String]): IO[CrawlerError, CrawlerResult] = {
          ZIO.foreachPar(urls) { url =>
            httpClient.get(url).flatMap {
              case SuccessfulHttpResponse.PureHttpResponse(payload) => pageParser.parse(payload).map(_.title).map(url -> _)
            }
          }.map(_.toMap).bimap(httpErrorToCrawlerError, CrawlerResult.Success)
        }

        private def httpErrorToCrawlerError(httpResponse: FailedHttpResponse): CrawlerError = httpResponse match {
          case FailedHttpResponse.UnsuccessfulResponseStatus(status) => CrawlerError.FailedHttpRequestError(status)
          case FailedHttpResponse.MalformedUrlError(exc) => CrawlerError.MalformedUrlError(exc)
          case FailedHttpResponse.UnknownError(exc) => CrawlerError.UnknownCrawlerError(exc)
        }
      }
    }

}
