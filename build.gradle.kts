import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
    id("org.springframework.boot") version "2.5.5"
    java
}
repositories {
    mavenCentral()
}
dependencies {
    val springPlatform = platform("org.springframework.boot:spring-boot-dependencies:2.6.3")
    implementation(springPlatform)
    annotationProcessor(springPlatform)

    implementation("org.springframework:spring-beans")
    implementation("org.springframework:spring-core")
    implementation("org.springframework:spring-context")
    implementation("org.springframework:spring-core")
    implementation("org.springframework.data:spring-data-jdbc")
    implementation("org.springframework.boot:spring-boot-autoconfigure")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation("org.apache.commons:commons-math3:3.6.1")

    runtimeOnly("org.postgresql:postgresql:42.2.24")

    testImplementation("org.mockito:mockito-core")
    testImplementation("org.mockito:mockito-junit-jupiter")
    testImplementation("org.junit.jupiter:junit-jupiter-engine")


    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    val projectLombok = "org.projectlombok:lombok:1.18.22"
    compileOnly(projectLombok)
    annotationProcessor(projectLombok)
    testCompileOnly(projectLombok)
    testAnnotationProcessor(projectLombok)
}
tasks.test {
    useJUnitPlatform()
}
tasks.processResources {
    dependsOn(tasks.compileJava)
}
tasks.bootRun {
    args = listOf("realTime")
}
tasks.register<BootRun>("bootBatch") {
    group = "application"
    classpath = sourceSets.main.get().runtimeClasspath
    mainClass.set("edu.Application")
    args = listOf("batch")
}
tasks.register<BootRun>("bootCheck") {
    group = "application"
    classpath = sourceSets.main.get().runtimeClasspath
    mainClass.set("edu.Application")
    args = listOf("check")
}