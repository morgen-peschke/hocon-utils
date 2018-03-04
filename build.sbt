inThisBuild(Seq(
  version      := "0.1.0",
  name         := "hocon-utils",
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

val CatsVersion = "1.0.1"
val ScalaTestVersion = "3.0.4"
val MonocleVersion = "1.5.0"

libraryDependencies ++= Seq(
  "com.typesafe" % "config" % "1.3.2",
  "com.github.scopt" %% "scopt" % "3.7.0",
  "com.beachape" %% "enumeratum" % "1.5.12",
  "org.typelevel" %% "cats-core" % CatsVersion,
  "com.github.julien-truffaut" %% "monocle-core"  % MonocleVersion,
  "com.github.julien-truffaut" %% "monocle-macro" % MonocleVersion,
  "org.scalactic" %% "scalactic" % ScalaTestVersion % Test,
  "org.scalatest" %% "scalatest" % ScalaTestVersion % Test)
