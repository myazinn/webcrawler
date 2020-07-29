package com.home.httpsrv.crawler_srv.di

import com.home.http_crawler.http_client.api.HttpClient.HttpClient
import com.home.httpsrv.crawler_srv.services.CrawlerService
import com.home.httpsrv.crawler_srv.services.CrawlerService.CrawlerService
import com.home.httpsrv.web_page_parser.services.WebPageParser.WebPageParser
import zio.URLayer

object CrawlerServiceDI {

  type Dependencies = HttpClient with WebPageParser

  val live: URLayer[Dependencies, CrawlerService] = CrawlerService.live

}
