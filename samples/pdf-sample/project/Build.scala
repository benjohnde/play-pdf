import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

    val appName         = "pdf-sample"
    val appVersion      = "0.2"

    val appDependencies = Seq(
      "pdf" % "pdf_2.10" % "0.4.0"
    )

    val main = play.Project(appName, appVersion, appDependencies).settings(
      resolvers += Resolver.url("My GitHub Play Repository", url("http://www.joergviola.de/releases/"))(Resolver.ivyStylePatterns)
    )

}
