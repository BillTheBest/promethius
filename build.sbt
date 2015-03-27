name := "promethius"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  "org.scalaz" %% "scalaz-core" % "7.1.1",
  "com.typesafe.akka" %% "akka-actor" % "2.2.3",
  "com.typesafe.akka" %% "akka-contrib" % "2.2.3",
  "com.amazonaws" % "aws-java-sdk" % "1.8.0"
)     

play.Project.playScalaSettings

