package xyz.lascuolaopensource.coo2xyz.auth.views

import com.twitter.finatra.response.Mustache


@Mustache("layout")
case class LayoutView(renderedHtml: String)

