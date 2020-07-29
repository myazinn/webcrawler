import sbt._

object Dependencies {

  lazy val akkaVersion = "2.6.8"
  lazy val akkaHttpVersion = "10.1.11"
  lazy val zioVersion = "1.0.0-RC21-2"
  lazy val playJsonVersion = "2.9.0"
  lazy val playJsonSupportVersion = "1.32.0"

  object akka {
    val http = "com.typesafe.akka" %% "akka-http" % akkaHttpVersion
    val actor = "com.typesafe.akka" %% "akka-actor" % akkaVersion
    val stream = "com.typesafe.akka" %% "akka-stream" % akkaVersion
  }

  object play {
    val json = "com.typesafe.play" %% "play-json" % playJsonVersion
    val `json-support` = "de.heikoseeberger" %% "akka-http-play-json" % playJsonSupportVersion
  }

  object zio {
    val core = "dev.zio" %% "zio" % zioVersion
  }

}
