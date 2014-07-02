import sbt._
import Keys._

object Ydirect extends Build {

  lazy val project = Project("yandex-direct", file(".")) settings (buildSettings: _*)

  lazy val buildSettings: Seq[Setting[_]] = (
    projectSettings ++ dependencySettings ++ resolversSettings ++ publishSettings)

  lazy val projectSettings: Seq[Setting[_]] = Seq(
    name := "yandex-direct",
    organization := "com.github.krispo",
    scalaVersion := "2.11.1",
    version := "0.1-SNAPSHOT")

  lazy val dependencySettings: Seq[Setting[_]] = Seq(
    libraryDependencies ++= Seq(
      "com.typesafe.play" %% "play-json" % "2.3.1",
      "com.typesafe.play" %% "play-ws" % "2.3.1",
      "org.specs2" %% "specs2" % "2.3.12" % "test"))

  lazy val resolversSettings: Seq[Setting[_]] = Seq(
    resolvers ++= Seq(
      "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"))

  lazy val publishSettings: Seq[Setting[_]] = PublishSettings.publishSettings

}

object PublishSettings {
  val projectUrl = "https://github.com/krispo/yandex-direct.git"
  val licenseName = "MIT"
  val licenseUrl = "http://opensource.org/licenses/MIT"
  val licenseDistribution = "repo"
  val scmUrl = projectUrl
  val scmConnection = "scm:git:" + scmUrl
  val developerId = "krispo"
  val developerName = "Konstantin Skipor"
  val developerEmail = "konstantin.skipor@phystech.edu"

  def generatePomExtra(scalaVersion: String): xml.NodeSeq = {
    <url>{ projectUrl }</url>
    <licenses>
      <license>
        <name>{ licenseName }</name>
        <url>{ licenseUrl }</url>
        <distribution>{ licenseDistribution }</distribution>
      </license>
    </licenses>
    <scm>
      <url>{ scmUrl }</url>
      <connection>{ scmConnection }</connection>
    </scm>
    <developers>
      <developer>
        <id>{ developerId }</id>
        <name>{ developerName }</name>
        <email>{ developerEmail }</email>
      </developer>
    </developers>
  }

  lazy val publishSettings: Seq[Setting[_]] = Seq(
    publishMavenStyle := true,
    publishArtifact in Test := false,
    pomIncludeRepository := (_ => false),
    publishTo <<= version { (v: String) =>
      val nexus = "https://oss.sonatype.org/"
      if (v.trim.endsWith("SNAPSHOT"))
        Some("snapshots" at nexus + "content/repositories/snapshots")
      else
        Some("releases" at nexus + "service/local/staging/deploy/maven2")
    },
    pomExtra <<= (scalaVersion)(generatePomExtra),
    credentials += Credentials(Path.userHome / ".ivy2" / ".credentials"))
}
 