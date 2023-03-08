inThisBuild(
  Seq(
    organization := "ch.epfl.scala",
    homepage := Some(url("https://github.com/adpi2/sbt-plugin-example-diamond")),
    licenses := Seq("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
    developers := List(
      Developer("adpi2", "Adrien Piquerez", "adrien.piquerez@gmail.com", url("https://github.com/adpi2/"))
    ),
    scmInfo := Some(
      ScmInfo(
        browseUrl = url("https://github.com/adpi2/sbt-plugin-example-diamond"),
        connection = "https://github.com/adpi2/sbt-plugin-example-diamond.git"
      )
    ),
    scalaVersion := "2.12.17"
  )
)

lazy val root = project
  .in(file("."))
  .aggregate(diamond, left, right, bottom)
  .settings(
    publish / skip := true
  )

lazy val diamond = project
  .in(file("diamond"))
  .enablePlugins(SbtPlugin)
  .settings(
    name := "sbt-plugin-example-diamond",
    description :=
      """|An empty artifact that we use to test resolving a diamond graph of dependencies of sbt plugins.
         |This is the top dependency of the diamond. It depends on sbt-plugin-example-left and sbt-plugin-example-right.
         """.stripMargin
  )
  .dependsOn(left, right)

lazy val left = project
  .in(file("left"))
  .enablePlugins(SbtPlugin)
  .settings(
    name := "sbt-plugin-example-left",
    description :=
      """|An empty artifact that we use to test resolving a diamond graph of dependencies of sbt plugins.
         |This is the left dependency of the diamond. It depends on sbt-plugin-example-bottom.
      """.stripMargin
  )
  .dependsOn(bottom)

lazy val right = project
  .in(file("right"))
  .enablePlugins(SbtPlugin)
  .settings(
    name := "sbt-plugin-example-right",
    description :=
      """|An empty artifact that we use to test resolving a diamond graph of dependencies of sbt plugins.
         |This is the right dependency of the diamond. It depends on sbt-plugin-example-bottom.
      """.stripMargin,
    addSbtPlugin("ch.epfl.scala" % "sbt-plugin-example-bottom" % "0.9.0")
  )

lazy val bottom = project
  .in(file("bottom"))
  .enablePlugins(SbtPlugin)
  .settings(
    name := "sbt-plugin-example-bottom",
    description :=
      """|An empty artifact that we use to test resolving a diamond graph of dependencies of sbt plugins.
         |This is the bottom dependency of the diamond.
      """.stripMargin
  )

def oldStyleArtifacts: Def.Initialize[Task[Map[Artifact, File]]] =
  Classpaths.packaged(Classpaths.defaultArtifactTasks)
