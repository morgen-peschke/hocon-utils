val LogbackVersion   = "1.2.3"
val CirceVersion     = "0.9.1"
val ScalaTestVersion = "3.0.4"

inThisBuild(Seq(
  version := "0.0.1-SNAPSHOT",
  name := "hocon-utils",
  organization := "com.github.morgen-peschke",
  scalaOrganization := "org.typelevel",
  scalaVersion      := "2.12.4-bin-typelevel-4"
))

scalacOptions ++= Seq(
  "-encoding",
  "UTF-8",
  "-deprecation",
  "-unchecked",
  "-feature",
  "-Ypartial-unification",
  "-Ywarn-adapted-args",
  "-Ywarn-inaccessible",
  "-Ywarn-unused",
  "-Ywarn-dead-code",
  "-Ywarn-unused-import",
  "-Ywarn-value-discard",
  "-Xfatal-warnings")

scalacOptions in (Compile, console) := Seq(
  "-encoding",
  "UTF-8",
  "-feature",
  "-Ypartial-unification"
)

libraryDependencies ++= Seq(
  "io.circe"                   %% "circe-core"          % CirceVersion,
  "io.circe"                   %% "circe-generic"       % CirceVersion,
  "io.circe"                   %% "circe-parser"        % CirceVersion,
  "ch.qos.logback"             %  "logback-classic"     % LogbackVersion,
  "com.monovore"               %% "decline"             % "0.4.0",
  "com.typesafe.scala-logging" %% "scala-logging"       % "3.7.2",
  "com.typesafe"               %  "config"              % "1.3.2",
  "org.scalactic"              %% "scalactic"           % ScalaTestVersion % Test,
  "org.scalatest"              %% "scalatest"           % ScalaTestVersion % Test)
