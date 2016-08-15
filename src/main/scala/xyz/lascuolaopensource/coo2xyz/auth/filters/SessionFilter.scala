package xyz.lascuolaopensource.coo2xyz.auth.filters

import java.util.UUID

import com.twitter.finagle.{Service, SimpleFilter}
import com.twitter.finagle.http.{Cookie, Request, Response}
import com.twitter.inject.Logging
import com.twitter.util.Future


class SessionFilter extends SimpleFilter[Request, Response] with Logging {

	val sessionCookieKey = "finatra_xyz"

	override def apply(request: Request, service: Service[Request, Response]): Future[Response] = {
		info(s"Cookies: ${request.cookies.mkString(", ")}")

		val sessionCookie = request.cookies.get(sessionCookieKey)
		if (sessionCookie.isDefined) {
			info(s"Found session cookie: ${sessionCookie.get}")
		} else {
			val cookie = new Cookie(name = sessionCookieKey, value = UUID.randomUUID().toString)
			request.response.cookies.add(cookie)
			info(s"req.res.cook: ${request.response.cookies.keys.mkString(", ")}")
		}

		service(request)
	}

}
