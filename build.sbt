import AssemblyKeys._ 

name          := "example-service-1"

version       := "1.0-SNAPSHOT"

scalaVersion  := "2.10.0"

libraryDependencies ++= Seq(  
                              "net.databinder"        %%  "unfiltered-filter-async" % "0.6.7",
                              "net.databinder"        %%  "unfiltered-jetty"        % "0.6.7",
                              "com.typesafe.akka"     %%  "akka-actor"              % "2.1.1",
                              "ch.qos.logback"        %   "logback-classic"         % "1.0.9",
                              "ch.qos.logback"        %   "logback-core"            % "1.0.9",
                              "javax.mail"            %   "mail"                    % "1.4.6",
                              "com.typesafe.akka"     %%  "akka-testkit"            % "2.1.1" % "test"
                            )



seq(assemblySettings: _*)

seq(buildInfoSettings: _*)

sourceGenerators in Compile <+= buildInfo

buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion)

buildInfoPackage := "example"

scalacOptions ++= Seq(
  "-deprecation",
  "-unchecked",
  //"-Xmigration",
  "-Xcheckinit",
  "-optimise",
  //"-Xlog-implicits",
  "-Xlog-reflective-calls",
  //"-Xcheck-null",
  "-Ywarn-all",
  "-Xlint",
  "-feature",
  "-language:postfixOps",
  "-encoding", "utf8"
)

