package com.home.http_crawler.http_client.model

sealed trait SuccessfulHttpResponse
object SuccessfulHttpResponse {
  final case class PureHttpResponse(payload: String) extends SuccessfulHttpResponse
}

sealed trait FailedHttpResponse
object FailedHttpResponse {
  final case class UnsuccessfulResponseStatus(status: Int) extends FailedHttpResponse
  final case class UnknownError(exc: Exception) extends FailedHttpResponse
}