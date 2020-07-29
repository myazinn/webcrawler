package com.home.httpsrv.http_srv.services

import akka.http.scaladsl.server.Route
import com.home.httpsrv.crawler_routes.routes.CrawlerRoutes
import zio._

object HttpService {

  type HttpService = Has[Service]

  val routes: URIO[HttpService, Route] = ZIO.service[Service].map(_.routes)

  trait Service {
    def routes: Route
  }

  private[http_srv] val live = ZLayer.fromService[CrawlerRoutes.Service, Service] {
    crawlerRoutes =>

      new Service {
        val routes: Route = Route.seal(crawlerRoutes.routes)
      }
  }

}
