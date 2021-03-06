inThisBuild(Seq(
  scalaOrganization := "org.typelevel",
  scalaVersion := "2.12.3-bin-typelevel-4"
))

lazy val prjcSettings = Seq (
  version := "0.0.1",
  name := "CommandSide"
)

lazy val commandSide = (project in file(".")).settings(prjcSettings: _*)

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  Resolver.sonatypeRepo("snapshots")
)

val monocleVersion = "1.4.0"

libraryDependencies ++= Seq(
  "org.scalaz" %% "scalaz-core" % "7.3.0-M13",
  "org.scalaz" %% "scalaz-concurrent" % "7.3.0-M13",
	
  "co.fs2" %% "fs2-core" % "0.10.0-M6",
  "co.fs2" %% "fs2-io" % "0.10.0-M6",

  "org.typelevel" %% "cats-effect" % "0.4",
	
  "com.github.julien-truffaut"  %%  "monocle-core"    % monocleVersion,
  "com.github.julien-truffaut"  %%  "monocle-generic" % monocleVersion,
  "com.github.julien-truffaut"  %%  "monocle-macro"   % monocleVersion,        
  "com.github.julien-truffaut"  %%  "monocle-state"   % monocleVersion,     
  "com.github.julien-truffaut"  %%  "monocle-refined" % monocleVersion,
  "com.github.julien-truffaut"  %%  "monocle-law"     % monocleVersion % "test",

  "org.specs2" %% "specs2-core" % "3.8.9" % "test",

  "org.scalacheck" %% "scalacheck" % "1.13.5" % "test"
) map { _ withSources() }

scalacOptions in Test ++= Seq("-Yrangepos")

scalacOptions ++= Seq (
  "-feature",
  "-deprecation",
  "-Ywarn-unused-import",
  "-unchecked",
  "-Yno-adapted-args",
  "-Ywarn-value-discard",
  "-Ywarn-dead-code",
  "-target:jvm-1.8"
)

addCompilerPlugin("org.scalamacros" %% "paradise" % "2.1.0" cross CrossVersion.patch)
