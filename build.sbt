inThisBuild(Seq(
  scalaOrganization := "org.typelevel",
  scalaVersion := "2.12.3-bin-typelevel-4"
))

lazy val commonSettings = Seq (
  version := "0.0.1",
  name := "SimpleCqrsScala"
)

lazy val commandSide = project in file("CommandSide")
lazy val querySide = project in file("QuerySide")

lazy val simpleCqrsScala = (project in file("."))
  .aggregate(commandSide, querySide)
  .dependsOn(commandSide, querySide)
  .settings(commonSettings: _*)

initialCommands in console := """
  |import java.util.UUID
  |import SimpleCqrsScala.CommandSide._
  |import SimpleCqrsScala.CommandSide.Domain._
  |import scalaz._
  |import scalaz.Scalaz._
""".stripMargin

logLevel := Level.Info
