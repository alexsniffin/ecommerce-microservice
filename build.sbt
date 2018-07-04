organization in ThisBuild := "org.sandbox"
version in ThisBuild := "1.0-SNAPSHOT"

// the Scala version that will be used for cross-compiled libraries
scalaVersion in ThisBuild := "2.12.4"

val macwire = "com.softwaremill.macwire" %% "macros" % "2.3.0" % "provided"
val scalaTest = "org.scalatest" %% "scalatest" % "3.0.4" % Test

lazy val `e-commerce-sandbox` = (project in file("."))
  .aggregate(`e-commerce-sandbox-api`, `e-commerce-sandbox-impl`, `e-commerce-sandbox-stream-api`, `e-commerce-sandbox-stream-impl`)

lazy val `e-commerce-sandbox-api` = (project in file("e-commerce-sandbox-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )

lazy val `e-commerce-sandbox-impl` = (project in file("e-commerce-sandbox-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslPersistenceCassandra,
      lagomScaladslKafkaBroker,
      lagomScaladslTestKit,
      macwire,
      scalaTest
    )
  )
  .settings(lagomForkedTestSettings: _*)
  .dependsOn(`e-commerce-sandbox-api`)

lazy val `e-commerce-sandbox-stream-api` = (project in file("e-commerce-sandbox-stream-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )

lazy val `e-commerce-sandbox-stream-impl` = (project in file("e-commerce-sandbox-stream-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslTestKit,
      macwire,
      scalaTest
    )
  )
  .dependsOn(`e-commerce-sandbox-stream-api`, `e-commerce-sandbox-api`)
