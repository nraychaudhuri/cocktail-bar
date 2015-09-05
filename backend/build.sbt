name := "backend"

val akkaVer = "2.3.11"
val logbackVer = "1.1.2"
val quavaVer = "18.0"

val prompt = { state: State =>

  val extracted = Project.extract(state)
  import extracted._

  (name in currentRef get structure.data).map { name =>
    "[" + name + "] $ "
  }.getOrElse("> ")

}

shellPrompt := prompt

libraryDependencies ++= Seq(
  "com.typesafe.akka"        %% "akka-actor"                 % akkaVer,
  "com.typesafe.akka"        %% "akka-slf4j"                 % akkaVer,
  "ch.qos.logback"           %  "logback-classic"            % logbackVer,
  "com.google.guava"         %  "guava"                      % quavaVer

)

