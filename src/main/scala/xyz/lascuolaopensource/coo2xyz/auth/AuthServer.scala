package xyz.lascuolaopensource.coo2xyz.auth

import com.twitter.finagle.http.{Request, Response}
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.{CommonFilters, LoggingMDCFilter, TraceIdMDCFilter}
import com.twitter.finatra.http.routing.HttpRouter
import com.typesafe.config.ConfigFactory
import org.flywaydb.core.Flyway
import slick.jdbc.DatabaseUrlDataSource
import xyz.lascuolaopensource.coo2xyz.auth.controllers.{AssetsController, AuthController}
import xyz.lascuolaopensource.coo2xyz.auth.filters.SessionFilter


object AuthServerMain extends AuthServer {

	private[auth] val config = ConfigFactory.load()

	override protected def start() = {
		val flyway = new Flyway
		val dbSource = new DatabaseUrlDataSource
		dbSource.setUrl(config.getString("pgdb.properties.url"))
		flyway.setDataSource(dbSource)
		flyway.baseline()
		flyway.migrate

		super.start
	}

}

class AuthServer extends HttpServer {

	override protected def defaultFinatraHttpPort = AuthServerMain.config.getString("http.port")

	override def defaultHttpPort = AuthServerMain.config.getString("http.port").tail.toInt

	override protected def disableAdminHttpServer = true

	override protected def configureHttp(router: HttpRouter) = {
		router
			.filter[LoggingMDCFilter[Request, Response]]
			.filter[TraceIdMDCFilter[Request, Response]]
			.filter[CommonFilters]
			.add[SessionFilter, AuthController]
			.add[AssetsController]
	}

}
