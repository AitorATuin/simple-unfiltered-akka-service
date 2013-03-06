import AssemblyKeys._ 

name          := "example-service-1"

version       := "1.0-SNAPSHOT"

scalaVersion  := "2.10.0"

libraryDependencies ++= Seq(  
                              "net.databinder"        %%  "unfiltered-filter-async" % "0.6.4",
                              "net.databinder"        %%  "unfiltered-jetty"        % "0.6.4",
                              "com.typesafe.akka"     %% "akka-actor"               % "2.1.0",
                              "ch.qos.logback"        %   "logback-classic"         % "1.0.9",
                              "ch.qos.logback"        %   "logback-core"            % "1.0.9",
                              "javax.mail"            %   "mail"                    % "1.4.6" 
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

