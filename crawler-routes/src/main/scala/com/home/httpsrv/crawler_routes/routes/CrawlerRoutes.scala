package com.home.httpsrv.crawler_routes.routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.home.httpsrv.crawler_srv.services.CrawlerService
import com.home.httpsrv.crawler_routes.json.JsonFormatter._
import com.home.httpsrv.crawler_srv.model.{CrawlerError, CrawlerResult}
import com.home.httpsrv.web_page_parser.services.HttpUtils
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport._
import zio._

import scala.util.{Failure, Success}

object CrawlerRoutes {

  type CrawlerRoutes = Has[Service]

  trait Service {
    def routes: Route
  }

  private[crawler_routes] val live = ZLayer.fromServices[HttpUtils.Service, CrawlerService.Service, Service] {
    (utils, service) =>

      import utils.onCompleteZio

      new Service {
        val routes: Route =
          (get & path("links") & parameters('url.*)) { urls =>
            onCompleteZio(service.getTitles(urls.toSeq).either) {
              case Failure(exc) =>
                complete(StatusCodes.InternalServerError, s"Unexpected error [$exc]")
              case Success(Left(CrawlerError.UnknownCrawlerError(exc))) =>
                complete(StatusCodes.InternalServerError, s"Unknown error during web crawling [$exc]")
              case Success(Left(CrawlerError.FailedHttpRequestError(status))) =>
                complete(StatusCodes.BadRequest, s"Specified resource returned unexpected status code [$status]")
              case Success(Right(CrawlerResult.Success(links))) => complete(links)
            }
          }
      }
  }
}
