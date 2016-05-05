import sbt._
import Keys._

object ApplicationBuild extends Build {

    val appName         = "play-pdf"
    val appVersion      = "0.7-SNAPSHOT"

    val appDependencies = Seq(
      "org.xhtmlrenderer" % "flying-saucer-pdf" % "9.0.8",
      "nu.validator.htmlparser" % "htmlparser" % "1.4"
    )

    val main = Project(appName, file(".")).enablePlugins(play.sbt.PlayScala).settings(
      libraryDependencies ++= appDependencies,
      version := appVersion,
      javacOptions ++= Seq("-Xlint"),
      scalaVersion := "2.11.7",
      // hack to suppress javadoc error, see: https://play.lighthouseapp.com/projects/82401/tickets/898-javadoc-error-invalid-flag-g-when-publishing-new-module-local#ticket-898-7
      publishArtifact in(Compile, packageDoc) := false,
      organization := "de.joergviola"
    )

}
