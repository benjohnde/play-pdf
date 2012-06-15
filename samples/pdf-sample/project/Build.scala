import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "pdf-sample"
    val appVersion      = "0.2"

    val appDependencies = Seq(
      "pdf" % "pdf_2.9.1" % "0.3"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
      resolvers += Resolver.url("My GitHub Play Repository", url("http://www.joergviola.de/releases/"))(Resolver.ivyStylePatterns)
    )

}
