# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Keep LogTap public API (and avoid accidental stripping of handlers)
-keep class com.github.husseinhj.logtap.** { *; }

# Ktor CIO server / networking
-keep class io.ktor.** { *; }
-dontwarn io.ktor.**

# OkHttp / Okio (usually safe, but silence edge warnings)
-dontwarn okhttp3.**
-dontwarn okio.**

# Kotlin coroutines
-keep class kotlinx.coroutines.** { *; }
-dontwarn kotlinx.coroutines.**

# Kotlinx serialization (if you log/pretty-print serializable payloads)
-keep class kotlinx.serialization.** { *; }
-keepclassmembers class ** {
    @kotlinx.serialization.SerialName *;
}
-keepattributes *Annotation*, Signature, InnerClasses, EnclosingMethod

# WebSocket message models (if using reflection anywhere)
-keep class **$Companion { *; }