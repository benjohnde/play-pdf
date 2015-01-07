name := "pdf-sample"

version := "0.6"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.2"

libraryDependencies ++= Seq(
  "pdf" %% "pdf" % "0.6-SNAPSHOT")
