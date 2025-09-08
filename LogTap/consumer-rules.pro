# --- Silence JDK management API on Android (used by Ktor debug detector)
-dontwarn java.lang.management.**
-dontwarn io.ktor.util.debug.**

# --- SLF4J binder (not used on Android)
-dontwarn org.slf4j.impl.StaticLoggerBinder

# --- Keep essentials already in your rules (repeat if not present)
-keep class com.github.husseinhj.logtap.** { *; }
-keep class io.ktor.** { *; }
-dontwarn io.ktor.**
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn kotlinx.coroutines.**
-keepclassmembers class ** { @kotlinx.serialization.SerialName *; }
-keepattributes *Annotation*, Signature, InnerClasses, EnclosingMethod, Exceptions, SourceFile, LineNumberTable