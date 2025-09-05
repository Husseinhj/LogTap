import java.util.Properties

plugins {
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    `maven-publish`
//    signing
}

android {
    namespace = "com.github.husseinhj.logtap"
    compileSdk = 36
    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }

    defaultConfig {
        minSdk = 29

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {

    implementation(libs.okhttp)
    implementation(libs.okio)
    implementation(libs.bundles.ktor)
    implementation(libs.serialization.json)
    implementation(libs.coroutines.android)
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])
                groupId = "io.github.husseinhj"
                artifactId = "logtap" // or logtap-noop
                version = System.getenv("PUBLISH_VERSION") ?: "0.1.0"
                pom {
                    name.set("LogTap")
                    description.set("Realtime HTTP/WebSocket + Logger inspector for Android")
                    url.set("https://github.com/Husseinhj/LogTap")
                    licenses { license { name.set("MIT"); url.set("https://opensource.org/licenses/MIT") } }
                    scm {
                        url.set("https://github.com/Husseinhj/LogTap")
                        connection.set("scm:git:https://github.com/Husseinhj/LogTap.git")
                        developerConnection.set("scm:git:ssh://git@github.com/Husseinhj/LogTap.git")
                    }
                    developers { developer { id.set("husseinhj"); name.set("Hussein Habibi Juybari") } }
                }
            }
        }
//        repositories {
//            maven {
//                name = "Central"
//                url = uri("https://central.sonatype.com/repository/maven-releases/")
//                credentials {
//                    // Use Central Portal Publishing Token
//                    username = getSecret("OSSRH_USERNAME") // token id
//                    password = getSecret("OSSRH_PASSWORD")// token secret
//                }
//            }
//        }
    }

}

//signing {
//    useInMemoryPgpKeys(
//        getSecret("SIGNING_KEY"),
//        getSecret("SIGNING_PASSWORD")
//    )
//    sign(publishing.publications)
//}

fun getSecret(name: String): String? {
    // 1) env (CI)
    System.getenv(name)?.let { return it }

    // 2) gradle.properties (root or ~/.gradle)
    providers.gradleProperty(name).orNull?.let { return it }

    // 3) local.properties (manual load)
    val lp = Properties()
    val f = rootProject.file("local.properties")
    if (f.exists()) f.inputStream().use { lp.load(it) }
    val key = lp.getProperty(name)

    println("===========> KEY $name is ${if (key == null) "NULL" else "HAS IT"} <============")

    return key
}