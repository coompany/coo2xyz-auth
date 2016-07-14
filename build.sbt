name := "coo2xyz-auth"
organization := "xyz.lascuolaopensource"
version := "1.0"
scalaVersion := "2.11.8"


resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  "Twitter Maven" at "https://maven.twttr.com"
)


assemblyMergeStrategy in assembly := {
  case "Dockerfile" => MergeStrategy.discard
  case x if x.endsWith("io.netty.versions.properties") => MergeStrategy.first
  case other => MergeStrategy.defaultMergeStrategy(other)
}


val versions = new {
  val finatra = "2.2.0"
  val logback = "1.1.7"
}


libraryDependencies ++= Seq(
  "com.twitter" %% "finatra-http" % versions.finatra,
  "com.twitter" %% "finatra-httpclient" % versions.finatra,
  "ch.qos.logback" % "logback-classic" % versions.logback
)
