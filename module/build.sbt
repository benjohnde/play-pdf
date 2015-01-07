name := "pdf"

version := "0.6-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.2"

libraryDependencies ++= Seq(
  "org.xhtmlrenderer" % "core-renderer" % "R8",
  "net.sf.jtidy" % "jtidy" % "r938")