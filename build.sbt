import sbt._

name := "PLDA-lab"

scalaVersion := "2.12.3"

scalacOptions += "-Ypartial-unification"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.4" % "test"

libraryDependencies += "org.typelevel" %% "cats-core" % "1.0.0-RC1"

val circeVersion = "0.9.0-M2"

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)

libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.6"

