package com.home.httpsrv.web_page_parser.di

import com.home.httpsrv.web_page_parser.services.WebPageParser
import com.home.httpsrv.web_page_parser.services.WebPageParser.WebPageParser
import zio.ULayer

object WebPageParserDI {

  val live: ULayer[WebPageParser] = WebPageParser.live

}
