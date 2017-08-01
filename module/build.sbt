name := "pdf"
organization := "de.benjohn.play"
version := "1.1.2"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.12.2"

libraryDependencies += "org.xhtmlrenderer" % "flying-saucer-pdf" % "9.1.1"
libraryDependencies += "nu.validator.htmlparser" % "htmlparser" % "1.4"
