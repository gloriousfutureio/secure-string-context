import sbt._
import sbt.Keys._

organizationName := "Jeff May"

organization := "me.jeffmay"

name := "secure-string-context"

version := "1.1.0"

// TODO: Cross-compile
scalaVersion := "2.10.4"

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
  "org.scalacheck" %% "scalacheck" % "1.11.3" % "test",
  "org.scalatest" %% "scalatest" % "2.1.0" % "test",
  shapeless.value % "test",
  "com.typesafe.play" %% "play-functional" % "2.4.0-M2"
)

sources in(Compile, doc) := Seq.empty

// enable publishing the jar produced by `test:package`
publishArtifact in(Test, packageBin) := true

// enable publishing the test sources jar
publishArtifact in(Test, packageSrc) := true

scalacOptions ++= Seq(
  "-feature",
  "-deprecation",
  "-Xfatal-warnings",
  "-Xlint",
  "-Ywarn-dead-code",
  "-encoding", "UTF-8"
)
