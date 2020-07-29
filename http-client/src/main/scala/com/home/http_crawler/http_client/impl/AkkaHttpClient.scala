package com.home.http_crawler.http_client.impl

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpMethods, HttpRequest, HttpResponse, Uri}
import com.home.http_crawler.http_client.api.HttpClient
import com.home.http_crawler.http_client.api.HttpClient.HttpClient
import com.home.http_crawler.http_client.model.{FailedHttpResponse, SuccessfulHttpResponse}
import zio._

import scala.concurrent.duration._

object AkkaHttpClient {

  private[http_client] val live: URLayer[Has[ActorSystem], HttpClient] = ZLayer.fromService {
    implicit actorSystem =>
      new HttpClient.Service {

        private val timeout = 5.second

        override def get(url: String): IO[FailedHttpResponse, SuccessfulHttpResponse] = {
          val request = HttpRequest(HttpMethods.GET, Uri(url))
          for {
            akkaResponse <- sendRequest(request)
            response <- akkaResponseToResponse(akkaResponse)
          } yield response
        }

        private def sendRequest(request: HttpRequest): IO[FailedHttpResponse, HttpResponse] = {
          ZIO.fromFuture(_ => Http().singleRequest(request)).refineOrDie {
            case exc: Exception => FailedHttpResponse.UnknownError(exc)
          }
        }

        private def akkaResponseToResponse(akkaResponse: HttpResponse): IO[FailedHttpResponse, SuccessfulHttpResponse] = {
          if (akkaResponse.status.isSuccess()) {
            ZIO.fromFuture(_ => akkaResponse.entity.toStrict(timeout))
              .map(_.data.utf8String)
              .refineOrDie {
                case exc: Exception => FailedHttpResponse.UnknownError(exc)
              }.map(SuccessfulHttpResponse.PureHttpResponse)
          } else {
            ZIO.fail(FailedHttpResponse.UnsuccessfulResponseStatus(akkaResponse.status.intValue()))
          }
        }
      }
  }

}
