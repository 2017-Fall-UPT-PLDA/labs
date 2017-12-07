import sbt._

name := "PLDA-lab"

scalaVersion := "2.12.4"

scalacOptions += "-Ypartial-unification"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.4" % "test"

libraryDependencies += "org.typelevel" %% "cats-core" % "1.0.0-RC1"
libraryDependencies += "com.chuusai" %% "shapeless" % "2.3.2"


val circeVersion = "0.9.0-M2"

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)

libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.6"


libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.5.7",
  "com.typesafe.akka" %% "akka-testkit" % "2.5.7" % Test
)

libraryDependencies += "io.monix" %% "monix" % "3.0.0-M2"


