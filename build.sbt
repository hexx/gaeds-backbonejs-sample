seq(appengineSettings: _*)

webappResources in Compile <+= (crossTarget in Compile)(_ / "webapp_managed")

seq(coffeeSettings: _*)

(resourceManaged in (Compile, CoffeeKeys.coffee)) <<= (crossTarget in Compile)(_ / "webapp_managed" / "js")

scalaVersion := "2.9.2"

libraryDependencies ++= Seq(
  "net.databinder" %% "unfiltered-filter" % "0.6.4",
  "net.databinder" %% "unfiltered-json" % "0.6.4",
  "net.liftweb" %% "lift-json" % "2.5-M1",
  "com.github.hexx" %% "gaeds" % "0.2.0",
  "javax.servlet" % "servlet-api" % "2.5" % "provided",
  "org.mortbay.jetty" % "jetty" % "6.1.26" % "container"
)
