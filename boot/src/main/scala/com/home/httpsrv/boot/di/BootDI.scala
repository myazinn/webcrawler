package com.home.httpsrv.boot.di

import akka.actor.ActorSystem
import com.home.http_crawler.http_client.di.AkkaHttpClientDI
import com.home.httpsrv.crawler_routes.di.CrawlerRoutesDI
import com.home.httpsrv.crawler_srv.di.CrawlerServiceDI
import com.home.httpsrv.http_srv.di.HttpServiceDI
import com.home.httpsrv.http_srv.services.HttpService.HttpService
import com.home.httpsrv.web_page_parser.di.{HttpUtilsDI, WebPageParserDI}
import zio._

object BootDI {

  type Dependencies = Has[Runtime[Any]] with Has[ActorSystem]

  lazy val live: URLayer[Dependencies, HttpService] = httpService

  private lazy val utils = ZLayer.requires[Has[Runtime[Any]]] >>> HttpUtilsDI.live

  private lazy val crawlerService = (WebPageParserDI.live ++ AkkaHttpClientDI.live) >>> CrawlerServiceDI.live
  private lazy val crawlerRoutes = (utils ++ crawlerService) >>> CrawlerRoutesDI.live

  private lazy val httpService = crawlerRoutes >>> HttpServiceDI.live

}
