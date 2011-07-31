import sbt._
import Keys._

object XRay extends Build{
    lazy val main = Project("akkaapi", file(".")) settings(
        name := "akkaapi",
        organization := "com.verknowsys",
        version := "0.1.0-SNAPSHOT",
        scalaVersion := "2.9.0-1",
        crossScalaVersions += "2.8.1",
        libraryDependencies <+= scalaVersion("org.scala-lang" % "scala-compiler" % _ % "provided")
    )
}
