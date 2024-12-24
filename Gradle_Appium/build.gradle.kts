plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Удалите JUnit, если он не нужен
    // testImplementation(platform("org.junit:junit-bom:5.10.0"))
    // testImplementation("org.junit.jupiter:junit-jupiter")
    // testImplementation("org.junit.jupiter:junit-jupiter-api:5.11.3")

    testImplementation("org.testng:testng:7.10.2")
    implementation("io.appium:java-client:9.3.0")
    implementation("org.seleniumhq.selenium:selenium-java:4.26.0")
    implementation ("com.google.code.gson:gson:2.10.1")

}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8" // Устанавливаем кодировку UTF-8 для компиляции
}

tasks.test {
    useTestNG() // Измените на useTestNG()
}
