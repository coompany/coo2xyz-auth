package xyz.lascuolaopensource.coo2xyz.auth.controllers

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller


class AssetsController extends Controller{

	get("/assets/:*") { request: Request =>
		response.ok.file(s"${request.params("*")}")
	}

//	get("/assets/web/:*") { request: Request =>
//		response.ok.file(request.params("*"))
//	}

}
