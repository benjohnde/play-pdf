name := """example"""
version := "1.1.2"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.12.2"

libraryDependencies += "de.benjohn.play" %% "pdf" % "1.1.2"
