import Dependencies._

ThisBuild / name := "HttpCrawler"
ThisBuild / organization := "com.home"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.13.3"

lazy val `http-utils` = (project in file("http-utils"))
  .settings(
    libraryDependencies ++= Seq(zio.core, akka.http)
  )

lazy val `web-page-parser` = (project in file("web-page-parser"))
  .settings(
    libraryDependencies ++= Seq(zio.core)
  )

lazy val `http-client` = (project in file("http-client"))
  .settings(
    libraryDependencies ++= Seq(zio.core, akka.http, akka.actor, akka.stream)
  )
  .dependsOn(`http-client-api`)

lazy val `http-client-api` = (project in file("http-client-api"))
  .settings(
    libraryDependencies ++= Seq(zio.core)
  )

lazy val `crawler-service` = (project in file("crawler-service"))
  .settings(
    libraryDependencies ++= Seq(zio.core)
  )
  .dependsOn(`http-client-api`, `web-page-parser`)

lazy val `crawler-routes` = (project in file("crawler-routes"))
  .settings(
    libraryDependencies ++= Seq(zio.core, play.json, play.`json-support`, akka.http)
  )
  .dependsOn(`http-utils`, `crawler-service`)

lazy val `http-srv` = (project in file("http-srv"))
  .settings(
    libraryDependencies ++= Seq(zio.core, akka.http)
  )
  .dependsOn(`crawler-routes`)

lazy val boot = (project in file("boot"))
  .settings(
    libraryDependencies ++= Seq(zio.core, play.json, play.`json-support`, akka.http)
  )
  .dependsOn(`http-utils`, `web-page-parser`, `http-client`, `http-client-api`, `crawler-routes`, `http-srv`)

