package xyz.lascuolaopensource.coo2xyz.auth.controllers

import javax.inject.Inject

import com.fasterxml.jackson.annotation.JsonProperty
import com.twitter.finagle.http.{MediaType, Request}
import com.twitter.finatra.http.Controller
import com.twitter.finatra.request.FormParam
import com.twitter.finatra.validation.NotEmpty
import com.twitter.util.{Future, Promise}
import xyz.lascuolaopensource.coo2xyz.auth.db.{User, UsersRepo}
import xyz.lascuolaopensource.coo2xyz.auth.views


case class LoginRequest(
	@JsonProperty("email_field") @NotEmpty @FormParam email: String,
	@JsonProperty("password_field") @NotEmpty @FormParam password: String,
	@Inject http: Request)

class AuthController extends Controller {

//	get("/") { request: Request =>
//		info(s"ctrl: ${request.response.cookies.keys.mkString(", ")}")
//		val resp = response.ok
//		// request.cookies.foreach { case (s, c) => resp.cookie(c) }
//		resp.body(views.view("test", TestView("my string")))
//	}

	get("/") { request: Request =>
	    response.temporaryRedirect.location("/login")
	}

	get("/login") { request: Request =>
		views.view("login")
	}

	post("/login") { request: LoginRequest =>
		info(s"${request.http.response.cookies.getValue("finatra_xyz").getOrElse("no cookie in ctrl")}")
		UsersRepo.findByLogin(request.email, request.password) map {
			case Some(user) => response.ok.contentType(MediaType.PlainText).body(s"Logged in as ${user.email}")
			case None => response.unauthorized
		}
	}

	get("/signup") { request: Request =>
		views.view("signup")
	}

	get("/hi") { request: Request =>
		info("Called hi route")

	  	response
			.ok
			.contentType(MediaType.PlainText)
			.body("Hello " + request.params.getOrElse("name", "unnamed"))
	}

}
