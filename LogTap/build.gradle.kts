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
        singleVariant("release") { withJavadocJar(); withSourcesJar() }
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
                groupId = "com.github.husseinhj"
                artifactId = "logtap"
                version = System.getenv("PUBLISH_VERSION") ?: "0.1.0"

                pom {
                    name.set("LogTap")
                    description.set("Realtime HTTP/WebSocket + Logger viewer for Android (OkHttp3)")
                    url.set("https://github.com/husseinhj/LogTap")
                    licenses {
                        license {
                            name.set("Apache-2.0")
                            url.set("https://www.apache.org/licenses/LICENSE-2.0")
                        }
                    }
                    developers {
                        developer {
                            id.set("husseinhj")
                            name.set("Hussein Habibi Juybari")
                            email.set("hussein.juybari@gmail.com")
                        }
                    }
                    scm {
                        url.set("https://github.com/husseinhj/LogTap")
                        connection.set("scm:git:github.com/husseinhj/LogTap.git")
                        developerConnection.set("scm:git:ssh://git@github.com/husseinhj/LogTap.git")
                    }
                }
            }
        }
    }
}

signing {
    // Use in-memory keys from GitHub Secrets
    useInMemoryPgpKeys(
        System.getenv("SIGNING_KEY"),    // Base64-encoded ASCII-armored key
        System.getenv("SIGNING_PASSWORD")
    )
    sign(publishing.publications)
}