package xyz.lascuolaopensource.coo2xyz.auth

import com.github.mustachejava.DefaultMustacheFactory
import com.twitter.finatra.http.marshalling.mustache.MustacheService


package object views {

	val mustache = new MustacheService(new DefaultMustacheFactory())

	def view(template: String, obj: Any) = LayoutView(mustache.createString(s"templates/$template.mustache", obj))

}
