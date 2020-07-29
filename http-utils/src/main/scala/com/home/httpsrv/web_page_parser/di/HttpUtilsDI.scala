package com.home.httpsrv.web_page_parser.di

import com.home.httpsrv.web_page_parser.services.HttpUtils
import com.home.httpsrv.web_page_parser.services.HttpUtils.HttpUtils
import zio.{Has, Runtime, URLayer}

object HttpUtilsDI {

  val live: URLayer[Has[Runtime[Any]], HttpUtils] = HttpUtils.live

}
