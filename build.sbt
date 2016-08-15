name := "coo2xyz-auth"
organization := "xyz.lascuolaopensource"
version := "1.0"
scalaVersion := "2.11.8"

enablePlugins(JavaAppPackaging)
enablePlugins(SbtWeb)

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  "Twitter Maven" at "https://maven.twttr.com"
)


assemblyMergeStrategy in assembly := {
  case "Dockerfile" => MergeStrategy.discard
  case x if x.endsWith("io.netty.versions.properties") => MergeStrategy.first
  case other => MergeStrategy.defaultMergeStrategy(other)
}


sourceDirectory in Assets := (sourceDirectory in Compile).value / "resources" / "styles"
LessKeys.compress := true
(managedClasspath in Runtime) += (packageBin in Assets).value


val versions = new {
  val finatra = "2.2.0"
  val bijection = "0.9.2"
  val slick = "3.1.1"
  val postgresql = "9.4.1209"
  val flyway = "4.0.3"
  val logback = "1.1.7"
  val config = "1.3.0"
}


libraryDependencies ++= Seq(
  "com.twitter" %% "finatra-http" % versions.finatra,
  "com.twitter" %% "finatra-httpclient" % versions.finatra,
  "com.twitter" %% "bijection-core" % versions.bijection,
  "com.twitter" %% "bijection-util" % versions.bijection,
  "com.typesafe.slick" %% "slick" % versions.slick,
  "com.typesafe.slick" %% "slick-hikaricp" % versions.slick,
  "org.postgresql" % "postgresql" % versions.postgresql,
  "org.flywaydb" % "flyway-core" % versions.flyway,
  "ch.qos.logback" % "logback-classic" % versions.logback,
  "com.typesafe" % "config" % versions.config
)
