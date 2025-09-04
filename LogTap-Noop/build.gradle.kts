plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    `maven-publish`
}

android {
    namespace = "com.github.husseinhj.logtap"
    compileSdk = 36

    buildTypes { release {} }
    publishing { singleVariant("release") { withSourcesJar() } }

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
        create<MavenPublication>("release") {
            groupId = "com.github.husseinhj"
            artifactId = "logtap-noop"
            version = System.getenv("PUBLISH_VERSION") ?: "0.1.0"
            afterEvaluate { from(components["release"]) }
            pom {
                name.set("LogTap (no-op)")
                description.set("No-op artifact for release builds of LogTap")
                url.set("https://github.com/husseinhj/LogTap")
                licenses { license { name.set("Apache-2.0"); url.set("https://www.apache.org/licenses/LICENSE-2.0") } }
                scm { url.set("https://github.com/husseinhj/LogTap") }
            }
        }
    }
}