dependencies {
  api(Dependencies.okHttp)
  api(project(":misk-inject"))
  api(Dependencies.wireRuntime) // AnyMessage
  api(Dependencies.wireGrpcClient) // GrpcStatus
  implementation(Dependencies.kotlinStdLibJdk8)
  implementation(Dependencies.okio)
  testImplementation(Dependencies.junitApi)
  testImplementation(Dependencies.kotlinTest)
}

afterEvaluate {
  project.tasks.dokka {
    outputDirectory = "$rootDir/docs/0.x"
    outputFormat = "gfm"
  }
}

apply(from = "$rootDir/gradle-mvn-publish.gradle")
