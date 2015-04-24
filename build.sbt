
name := "secure-string-context"

organization := "me.jeffmay"

organizationName := "Jeff May"

version := "2.0.0"

crossScalaVersions := Seq("2.11.6", "2.10.4")

scalacOptions ++= {
  // the deprecation:false flag is only supported by scala >= 2.11.3, but needed for scala >= 2.11.0 to avoid warnings
  CrossVersion.partialVersion(scalaVersion.value) match {
    case Some((2, scalaMinor)) if scalaMinor >= 11 =>
      // For scala versions >= 2.11.3
      Seq("-Xfatal-warnings", "-deprecation:false")
    case Some((2, scalaMinor)) if scalaMinor < 11 =>
      // For scala versions 2.10.x
      Seq("-Xfatal-warnings", "-deprecation")
  }
} ++ Seq(
  "-feature",
  "-Ywarn-dead-code",
  "-encoding", "UTF-8"
)

// Needed for testing compilation
val shapeless = Def setting (
  CrossVersion partialVersion scalaVersion.value match {
    case Some((2, scalaMajor)) if scalaMajor >= 11 =>
      "com.chuusai" %% "shapeless" % "2.0.0"
    case Some((2, 10)) =>
      "com.chuusai" %  "shapeless" % "2.0.0" cross CrossVersion.full
  }
)

libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-reflect" % scalaVersion.value,
  "org.scalacheck" %% "scalacheck" % "1.12.1" % "test",
  "org.scalatest" %% "scalatest" % "2.2.4" % "test",
  shapeless.value % "test",
  "com.typesafe.play" %% "play-functional" % "2.4.0-M2"
)

// disable compilation of ScalaDocs, since this always breaks on links
sources in(Compile, doc) := Seq.empty

// disable publishing empty ScalaDocs
publishArtifact in (Compile, packageDoc) := false

bintraySettings ++ bintrayPublishSettings

licenses += ("Apache-2.0", url("http://opensource.org/licenses/apache-2.0"))
