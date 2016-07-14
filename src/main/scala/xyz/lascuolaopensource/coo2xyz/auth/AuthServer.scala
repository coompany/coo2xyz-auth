package xyz.lascuolaopensource.coo2xyz.auth

import com.twitter.finagle.http.{Request, Response}
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.{CommonFilters, LoggingMDCFilter, TraceIdMDCFilter}
import com.twitter.finatra.http.routing.HttpRouter


object AuthServerMain extends AuthServer

class AuthServer extends HttpServer {


	override protected def disableAdminHttpServer = true

	override protected def configureHttp(router: HttpRouter) = {
		router
			.filter[LoggingMDCFilter[Request, Response]]
			.filter[TraceIdMDCFilter[Request, Response]]
			.filter[CommonFilters]
			.add[AuthController]
	}

}
