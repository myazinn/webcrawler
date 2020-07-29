package com.home.httpsrv.web_page_parser.services

import akka.http.scaladsl.server.Directive1
import akka.http.scaladsl.server.Directives._
import zio._

import scala.util.Try

object HttpUtils {

  type HttpUtils = Has[Service]

  trait Service {
    def onCompleteZio[T](zio: Task[T]): Directive1[Try[T]]
  }

  private[web_page_parser] val live: URLayer[Has[Runtime[Any]], HttpUtils] = ZLayer.fromService { runtime =>
    new Service {
      def onCompleteZio[T](zio: Task[T]): Directive1[Try[T]] =
        onComplete(runtime.unsafeRunToFuture(zio))
    }
  }

}
