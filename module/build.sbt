name := "pdf"
organization := "de.benjohn.play"
version := "1.0"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.xhtmlrenderer" % "flying-saucer-pdf" % "9.1.1",
  "nu.validator.htmlparser" % "htmlparser" % "1.4"
)
