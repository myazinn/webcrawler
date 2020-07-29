package com.home.httpsrv.http_srv.di

import com.home.httpsrv.crawler_routes.routes.CrawlerRoutes.CrawlerRoutes
import com.home.httpsrv.http_srv.services.HttpService
import com.home.httpsrv.http_srv.services.HttpService.HttpService
import zio.URLayer

object HttpServiceDI {

  type Dependencies = CrawlerRoutes

  val live: URLayer[Dependencies, HttpService] = HttpService.live

}
