addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.8.5")

addSbtPlugin("com.eed3si9n" % "sbt-buildinfo" % "0.2.0")

resolvers += Resolver.url("sbt-plugin-releases",
  new URL("http://artifacts.inconcert/artifactory/sbt-plugin-releases/"))(Resolver.ivyStylePatterns)

