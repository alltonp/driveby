import scala.util.Try

name := "driveby"

organization := "im.mange"

version := Try(sys.env("TRAVIS_BUILD_NUMBER")).map("0.3." + _).getOrElse("1.0-SNAPSHOT")

scalaVersion:= "2.11.6"

resolvers ++= Seq(
  "Sonatype OSS Releases" at "http://oss.sonatype.org/content/repositories/releases/"
)

val flyScalaVersion = "2.1.6"
val nscalaTimeVersion = "1.8.0"
val scalaTestVersion = "[2.2.1,2.3.0]"
val seleniumVersion = "2.45.0"
val phantomjsDriverVersion = "1.2.1"

libraryDependencies ++= Seq(
  "com.github.nscala-time" %% "nscala-time" % nscalaTimeVersion,
//  "com.github.detro" % "phantomjsdriver" % phantomjsDriverVersion
  //TODO: temporary workaround: https://github.com/detro/ghostdriver/issues/397
  "com.codeborne" % "phantomjsdriver" % phantomjsDriverVersion
    exclude("org.seleniumhq.selenium", "selenium-server")
    exclude("org.seleniumhq.selenium", "selenium-safari-driver")
    exclude("org.seleniumhq.selenium", "selenium-android-driver")
    exclude("org.seleniumhq.selenium", "selenium-htmlunit-driver")
    exclude("org.seleniumhq.selenium", "selenium-iphone-driver")
    exclude("org.seleniumhq.selenium", "selenium-java")
    exclude("org.seleniumhq.selenium", "selenium-api")
    exclude("org.seleniumhq.selenium", "selenium-remote-driver")
  ,
  //"com.github.detro" % "phantomjsdriver" % "1.2.0" exclude("org.seleniumhq.selenium", "selenium-server"),
  "im.mange" %% "little-server" % "0.0.7" % "test",
  "org.scalatest" % "scalatest_2.11" % scalaTestVersion % "provided",
//  "org.seleniumhq.selenium" % "selenium-remote-driver" % "2.45.0"

//    exclude("cglib", "cglib-nodep")                        // selenium-remote-driver
//    exclude("commons-codec", "commons-codec")              // selenium-remote-driver
//    exclude("net.java.dev.jna", "jna")                     // selenium-remote-driver
//    exclude("net.java.dev.jna", "platform")                // selenium-remote-driver
//    exclude("org.scalatest", "scalatest_2.11"),
  "org.seleniumhq.selenium" % "selenium-firefox-driver" % seleniumVersion,
  "org.seleniumhq.selenium" % "selenium-ie-driver" % seleniumVersion,
  "org.seleniumhq.selenium" % "selenium-chrome-driver" % seleniumVersion,
//  "org.scala-lang.modules" %% "scala-xml" % "1.0.3" % "test",
  "com.flyobjectspace" %% "flyscala" % flyScalaVersion /*withSources()*/
//  "org.pegdown" % "pegdown" % "1.0.2" % "test"
//  "junit" % "junit" % "4.9"// % "test"
)

net.virtualvoid.sbt.graph.Plugin.graphSettings

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
