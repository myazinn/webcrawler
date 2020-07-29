package com.home.httpsrv.web_page_parser.services

import com.home.httpsrv.web_page_parser.model.{Title, WebPage}
import zio._

object WebPageParser {

  type WebPageParser = Has[Service]

  trait Service {
    def parse(pageStr: String): UIO[WebPage]
  }

  private val titleRegexp = """(?<=<title>)(.+)(?=</title>)""".r

  private[web_page_parser] val live: ULayer[WebPageParser] = ZLayer.succeed {
    new Service {
      override def parse(pageStr: String): UIO[WebPage] = UIO {
        val title = titleRegexp.findFirstIn(pageStr)
        WebPage(title.map(Title))
      }
    }

  }
}
