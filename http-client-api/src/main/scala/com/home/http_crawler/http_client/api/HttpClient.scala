package com.home.http_crawler.http_client.api

import com.home.http_crawler.http_client.model.{FailedHttpResponse, SuccessfulHttpResponse}
import zio._

object HttpClient {

  type HttpClient = Has[Service]

  def get(url: String): ZIO[HttpClient, FailedHttpResponse, SuccessfulHttpResponse] =
    ZIO.service[Service].flatMap(_.get(url))

  trait Service {
    def get(url: String): IO[FailedHttpResponse, SuccessfulHttpResponse]
  }

}
