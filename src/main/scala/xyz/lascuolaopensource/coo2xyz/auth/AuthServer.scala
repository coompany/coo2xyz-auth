package xyz.lascuolaopensource.coo2xyz.auth

import com.twitter.finagle.http.{Request, Response}
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.{CommonFilters, LoggingMDCFilter, TraceIdMDCFilter}
import com.twitter.finatra.http.routing.HttpRouter
import com.typesafe.config.ConfigFactory
import xyz.lascuolaopensource.coo2xyz.auth.controllers.{AssetsController, AuthController}


object AuthServerMain extends AuthServer

class AuthServer extends HttpServer {

	private val config = {
		val env = System.getenv("xyz-env") match {
			case s: String if !s.isEmpty => s
			case s => "dev"
		}
		ConfigFactory.load(env + ".conf")
	}

	override protected def defaultFinatraHttpPort = config.getString("http.port")

	 override def defaultHttpPort = config.getString("http.port").tail.toInt

	override protected def disableAdminHttpServer = true

	override protected def configureHttp(router: HttpRouter) = {
		router
			.filter[LoggingMDCFilter[Request, Response]]
			.filter[TraceIdMDCFilter[Request, Response]]
			.filter[CommonFilters]
			.add[AuthController]
			.add[AssetsController]
	}

}
