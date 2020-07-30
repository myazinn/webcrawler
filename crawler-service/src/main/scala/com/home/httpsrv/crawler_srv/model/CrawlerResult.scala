package com.home.httpsrv.crawler_srv.model

import com.home.httpsrv.web_page_parser.model.Title

sealed trait CrawlerResult
object CrawlerResult {
  final case class Success(links: Map[String, Option[Title]]) extends CrawlerResult
}

sealed trait CrawlerError
object CrawlerError {
  final case class MalformedUrlError(exc: Exception) extends CrawlerError
  final case class FailedHttpRequestError(status: Int) extends CrawlerError
  final case class UnknownCrawlerError(exc: Exception) extends CrawlerError
}
