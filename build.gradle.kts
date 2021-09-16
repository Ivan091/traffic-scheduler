plugins {
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    application
    java
}
application {
    mainClass.set("edu.Application")
}
repositories {
    mavenCentral()
}
dependencyManagement {
    imports {
        mavenBom("org.springframework:spring-framework-bom:5.3.5")
        mavenBom("org.springframework.boot:spring-boot-dependencies:2.4.4")
    }
    dependencies {
        dependencySet("io.springfox:3.0.0") {
            entry("springfox-swagger-ui")
            entry("springfox-boot-starter")
        }
        dependencySet("com.fasterxml.jackson.core:2.12.4") {
            entry("jackson-annotations")
            entry("jackson-databind")
        }
        dependency("org.postgresql:postgresql:42.2.23")
    }
}
dependencies {
    implementation("org.springframework:spring-beans")
    implementation("org.springframework:spring-core")
    implementation("org.springframework:spring-context")
    implementation("org.springframework:spring-core")
    implementation("org.springframework.data:spring-data-jpa")
    implementation("org.springframework.boot:spring-boot-autoconfigure")
    implementation("org.springframework.boot:spring-boot-starter-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("org.postgresql:postgresql")
    testImplementation("org.mockito:mockito-core:3.11.2")
    testImplementation("org.mockito:mockito-junit-jupiter:3.11.2")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.7.2")
}
tasks.test {
    useJUnitPlatform()
}