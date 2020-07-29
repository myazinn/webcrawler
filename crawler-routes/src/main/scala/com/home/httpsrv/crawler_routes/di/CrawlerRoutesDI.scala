package com.home.httpsrv.crawler_routes.di

import com.home.httpsrv.crawler_routes.routes.CrawlerRoutes
import com.home.httpsrv.crawler_routes.routes.CrawlerRoutes.CrawlerRoutes
import com.home.httpsrv.crawler_srv.services.CrawlerService.CrawlerService
import com.home.httpsrv.web_page_parser.services.HttpUtils.HttpUtils
import zio.URLayer

object CrawlerRoutesDI {

  type Dependencies = HttpUtils with CrawlerService

  val live: URLayer[Dependencies, CrawlerRoutes] = CrawlerRoutes.live

}
