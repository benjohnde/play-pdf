import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "pdf"
    val appVersion      = "0.4"

    val appDependencies = Seq(
      "org.xhtmlrenderer" % "core-renderer" % "R8",
        "net.sf.jtidy" % "jtidy" % "r938"
	)

    val main = PlayProject(appName, appVersion, appDependencies).settings(
      // Add your own project settings here      
    )

}
