package com.home.http_crawler.http_client.di

import akka.actor.ActorSystem
import com.home.http_crawler.http_client.api.HttpClient.HttpClient
import com.home.http_crawler.http_client.impl.AkkaHttpClient
import zio._

object AkkaHttpClientDI {

  type Dependencies = Has[ActorSystem]

  val live: URLayer[Has[ActorSystem], HttpClient] = AkkaHttpClient.live

}
