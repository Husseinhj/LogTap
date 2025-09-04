plugins {
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    `maven-publish`
    signing
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
        repositories {
            maven {
                name = "Central"
                url = uri("https://central.sonatype.com/repository/maven-releases/")
                credentials {
                    // Use Central Portal Publishing Token
                    username = System.getenv("OSSRH_USERNAME") // token id
                    password = System.getenv("OSSRH_PASSWORD") // token secret
                }
            }
        }
    }

}

signing {
    useInMemoryPgpKeys(
        System.getenv("SIGNING_KEY"),
        System.getenv("SIGNING_PASSWORD")
    )
    sign(publishing.publications)
}