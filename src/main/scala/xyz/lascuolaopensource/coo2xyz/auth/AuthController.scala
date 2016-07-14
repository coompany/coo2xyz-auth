package xyz.lascuolaopensource.coo2xyz.auth

import com.twitter.finagle.http.{MediaType, Request}
import com.twitter.finatra.http.Controller


class AuthController extends Controller {

	get("/") { request: Request =>
		info("Called root route")

	  	response
			.ok
			.contentType(MediaType.PlainText)
			.body("Hello " + request.params.getOrElse("name", "unnamed"))
	}

}
