package com.home.httpsrv.boot

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import com.home.httpsrv.boot.di.BootDI
import com.home.httpsrv.http_srv.services.HttpService
import zio._
import zio.console._
import zio.internal.Platform

object Boot extends App {
  def run(args: List[String]): URIO[ZEnv, ExitCode] = {
    Managed.make(Task(ActorSystem("super-duper-application")))(sys => UIO(sys.terminate)).use {
      implicit actorSystem =>
        val app = for {
          routes <- HttpService.routes
          _ <- ZIO.fromFuture(_ => Http().bindAndHandle(routes, "0.0.0.0", 8080))
          _ <- getStrLn
        } yield ExitCode.success
        app.provideSomeLayer[zio.ZEnv] {
          val runtimeL: ULayer[Has[Runtime[Any]]] =
            ZLayer.succeed(Runtime((), Platform.fromExecutionContext(actorSystem.getDispatcher)))
          val systemL: ULayer[Has[ActorSystem]] = ZLayer.succeed(actorSystem)
          (runtimeL ++ systemL) >>> BootDI.live
        }
    }.orDie
  }
}
