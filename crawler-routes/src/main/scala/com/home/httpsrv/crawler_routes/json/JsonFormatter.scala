package com.home.httpsrv.crawler_routes.json

import com.home.httpsrv.web_page_parser.model.Title
import play.api.libs.json._

object JsonFormatter {

  implicit lazy val titleJsonFormatter: Format[Title] = Json.valueFormat

}
