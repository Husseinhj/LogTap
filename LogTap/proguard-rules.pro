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

# Ktor / OkHttp / Coroutines are reflection-light but can warn
-keep class io.ktor.** { *; }
-dontwarn io.ktor.**
-dontwarn okhttp3.**
-dontwarn okio.**

-dontwarn kotlinx.coroutines.**

# If using kotlinx-serialization anywhere in your payloads
-keepclassmembers class ** {
    @kotlinx.serialization.SerialName *;
}
-keepattributes *Annotation*, Signature, InnerClasses, EnclosingMethod, Exceptions, SourceFile, LineNumberTable