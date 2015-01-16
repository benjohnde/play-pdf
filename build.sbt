releaseSettings

ReleaseKeys.crossBuild := true

name := "play-pdf"

organization := "nl.rhinofly"

scalaVersion := "2.11.4"

crossScalaVersions := Seq("2.10.4", scalaVersion.value)

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play" % "2.3.6" % "provided",
  "org.xhtmlrenderer" % "flying-saucer-core" % "9.0.7",
  "org.xhtmlrenderer" % "flying-saucer-pdf" % "9.0.7",
  "net.sf.jtidy" % "jtidy" % "r938"
)

resolvers ++= Seq(
  "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"
)
  
credentials += Credentials(Path.userHome / ".ivy2" / ".credentials") 

publishTo := {
  val repo = if (version.value endsWith "SNAPSHOT") "snapshot" else "release"
  Some("Rhinofly Internal " + repo.capitalize + " Repository" at "http://maven-repository.rhinofly.net:8081/artifactory/libs-" + repo + "-local")
}