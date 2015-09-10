name := "cocktail-bar"

val scalaVer = "2.11.7"

lazy val compileOptions = Seq(
  "-unchecked",
  "-deprecation",
  "-language:_",
  "-target:jvm-1.8",
  "-encoding", "UTF-8"
)

lazy val commonSettings = Seq(
  organization := "functionalconf",
  version := "1.0.0",
  scalaVersion := scalaVer,
  scalacOptions ++= compileOptions,
  unmanagedSourceDirectories in Compile := List((scalaSource in Compile).value,(javaSource in Compile).value),
  unmanagedSourceDirectories in Test := List((scalaSource in Test).value),
  EclipseKeys.createSrc := EclipseCreateSrc.Default + EclipseCreateSrc.Resource,
  EclipseKeys.eclipseOutput := Some(".target"),
  EclipseKeys.withSource := true,
  EclipseKeys.skipParents in ThisBuild := true,
  parallelExecution in Test := false,
  logBuffered in Test := false,
  parallelExecution in ThisBuild := false
)

val backend = project.in(file("backend"))
                .settings(commonSettings:_*)

val frontend = project.in(file("frontend"))
				.enablePlugins(PlayScala)
                .settings(commonSettings:_*)


val root = project.in(file("."))
              .aggregate(backend, frontend)
