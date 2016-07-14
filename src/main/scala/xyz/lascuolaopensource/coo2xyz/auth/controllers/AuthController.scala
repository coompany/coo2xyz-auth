package xyz.lascuolaopensource.coo2xyz.auth.controllers

import com.twitter.finagle.http.{MediaType, Request}
import com.twitter.finatra.http.Controller
import xyz.lascuolaopensource.coo2xyz.auth.views
import xyz.lascuolaopensource.coo2xyz.auth.views.TestView


class AuthController extends Controller {

	get("/") { request: Request =>
	  	views.view("test", TestView("my string"))
	}

	get("/hi") { request: Request =>
		info("Called hi route")

	  	response
			.ok
			.contentType(MediaType.PlainText)
			.body("Hello " + request.params.getOrElse("name", "unnamed"))
	}

}
