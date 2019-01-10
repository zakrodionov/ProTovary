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
-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
-renamesourcefileattribute SourceFile

-keepattributes *Annotation*
-keepattributes Signature
-keepattributes MethodParameters


#okhttp3 https://github.com/square/okhttp/blob/5fe3cc2d089810032671d6135ad137af6f491d28/README.md#proguard
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn org.conscrypt.**
# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

# Retrofit 2.X
## https://square.github.io/retrofit/ ##
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Exceptions

-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

#EventBus http://greenrobot.org/eventbus/documentation/proguard/
-keepclassmembers class * {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

-dontwarn me.**

# Gson specific classes
-keep class sun.misc.Unsafe { *; }

#Hawk
-dontwarn com.orhanobut**
# Facebook Conceal

# Keep our interfaces so they can be used by other ProGuard rules.
# See http://sourceforge.net/p/proguard/bugs/466/
-keep,allowobfuscation @interface com.facebook.crypto.proguard.annotations.DoNotStrip
-keep,allowobfuscation @interface com.facebook.crypto.proguard.annotations.KeepGettersAndSetters

# Do not strip any method/class that is annotated with @DoNotStrip
-keep @com.facebook.crypto.proguard.annotations.DoNotStrip class *
-keepclassmembers class * {
    @com.facebook.crypto.proguard.annotations.DoNotStrip *;
}

-keepclassmembers @com.facebook.crypto.proguard.annotations.KeepGettersAndSetters class * {
  void set*(***);
  *** get*();
}

-keep class com.shockwave.**

-keep class android.support.v8.renderscript.** { *; }

-keepclassmembers class * extends java.lang.Enum {
    <fields>;
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-ignorewarnings
-keep class * {
    public private *;
}