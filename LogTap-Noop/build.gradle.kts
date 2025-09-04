plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    `maven-publish`
}

android {
    namespace = "com.github.husseinhj.logtap"
    compileSdk = 36

    buildTypes { release {} }
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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    implementation(libs.okhttp)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

publishing {
    publications {
        // You can use register or create. Register defers configuration and is safer.
        register<MavenPublication>("release") {
            // IMPORTANT: defer accessing the variant component until after the Android
            // plugin has created it.
            afterEvaluate {
                from(components["release"])
            }

            groupId = "com.github.husseinhj"
            artifactId = "logtap-noop"
            version = System.getenv("PUBLISH_VERSION") ?: "0.1.0"

            pom {
                name.set("LogTap (no-op)")
                description.set("No-op artifact for release builds of LogTap")
                url.set("https://github.com/Husseinhj/LogTap")
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://opensource.org/licenses/MIT")
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
                    url.set("https://github.com/Husseinhj/LogTap")
                    connection.set("scm:git:https://github.com/Husseinhj/LogTap.git")
                    developerConnection.set("scm:git:ssh://git@github.com/Husseinhj/LogTap.git")
                }
            }
        }
    }

    // Central Portal (no staging)
    repositories {
        maven {
            name = "Central"
            url = uri("https://central.sonatype.com/repository/maven-releases/")
            credentials {
                username = System.getenv("OSSRH_USERNAME") // token id
                password = System.getenv("OSSRH_PASSWORD") // token secret
            }
        }
    }
}

//
//signing {
//    useInMemoryPgpKeys(
//        System.getenv("SIGNING_KEY"),       // base64(ASCII-armored private key)
//        System.getenv("SIGNING_PASSWORD")
//    )
//    sign(publishing.publications)
//}