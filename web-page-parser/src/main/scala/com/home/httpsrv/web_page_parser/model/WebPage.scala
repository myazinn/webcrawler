package com.home.httpsrv.web_page_parser.model

final case class WebPage(title: Option[Title])

final case class Title(value: String) extends AnyVal