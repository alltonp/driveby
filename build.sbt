import scala.util.Try


name := "driveby"

organization := "im.mange"

version := Try(sys.env("TRAVIS_BUILD_NUMBER")).map("0.3." + _).getOrElse("1.0-SNAPSHOT")

scalaVersion:= "2.11.4"

resolvers ++= Seq(
  "Sonatype OSS Releases" at "http://oss.sonatype.org/content/repositories/releases/"
)

val flyScalaVersion = "2.1.5"
val jettyVersion = "8.1.14.v20131031"
//  val nscalaTimeVersion = "0.4.2"
val nscalaTimeVersion = "0.2.0"
val scalaTestVersion = "2.1.3"
val seleniumVersion = "2.44.0"
val specs2Version = "1.14"
val phantomjsDriverVersion = "1.1.0"

libraryDependencies ++= Seq(
  "org.eclipse.jetty" % "jetty-webapp" % jettyVersion,
  "com.github.nscala-time" %% "nscala-time" % nscalaTimeVersion,
  "com.github.detro.ghostdriver" % "phantomjsdriver" % phantomjsDriverVersion exclude("org.seleniumhq.selenium", "selenium-server"),
  "org.specs2" %% "specs2" % specs2Version,
  "org.scalatest" % "scalatest_2.10" % scalaTestVersion,
  "org.seleniumhq.selenium" % "selenium-firefox-driver" % seleniumVersion,
  "org.seleniumhq.selenium" % "selenium-ie-driver" % seleniumVersion,
  "org.seleniumhq.selenium" % "selenium-chrome-driver" % seleniumVersion,
  "com.flyobjectspace" %% "flyscala" % flyScalaVersion, /*withSources()*/
  "org.pegdown" % "pegdown" % "1.0.2" % "test",
  "junit" % "junit" % "4.9"// % "test"
)

sonatypeSettings

publishTo <<= version { project_version ⇒
  val nexus = "https://oss.sonatype.org/"
  if (project_version.trim.endsWith("SNAPSHOT"))
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

publishMavenStyle := true

publishArtifact in Test := false

homepage := Some(url("https://github.com/alltonp/driveby"))

licenses +=("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0.html"))

credentials += Credentials("Sonatype Nexus Repository Manager", "oss.sonatype.org", System.getenv("SONATYPE_USER"), System.getenv("SONATYPE_PASSWORD"))

pomExtra :=
    <scm>
      <url>git@github.com:alltonp/driveby.git</url>
      <connection>scm:git:git@github.com:alltonp/driveby.git</connection>
    </scm>
    <developers>
      <developer>
        <id>alltonp</id>
      </developer>
    </developers>
