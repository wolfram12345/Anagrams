name := "newAnagrams"

version := "1.0"

scalaVersion := "2.12.2"

val scalatraVersion = "2.5.0"

resolvers += "Scalaz Bintray Repo" at "https://dl.bintray.com/scalaz/releases"

lazy val root = (project in file(".")).settings(
  organization := "com.example",
  scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature"),
  libraryDependencies ++= Seq(
    "org.scalatra" %% "scalatra" % scalatraVersion,
    "org.scalatra" %% "scalatra-specs2" % scalatraVersion % "test",
    "javax.servlet" % "javax.servlet-api" % "3.0.1" % "provided"
  )
).settings(jetty(): _*)
