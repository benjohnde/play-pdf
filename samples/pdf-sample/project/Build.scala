import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "pdf-sample"
    val appVersion      = "0.1"

    val appDependencies = Seq(
      "pdf" % "pdf_2.9.1" % "0.1"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
      resolvers += Resolver.url("My GitHub Play Repository", url("http://joergviola.github.com/releases/"))(Resolver.ivyStylePatterns)
    )

}
