name := """example"""

version := "1.1.2"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  "de.benjohn.play" %% "pdf" % "1.1.2"
)
