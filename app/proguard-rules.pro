# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in E:\Android_Studio\SDK/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}


-keep class com.amap.api.**{*;}
-keep class com.autonavi.aps.amapapi.model.AmapLoc
-dontwarn com.amap.api.**

-keep class com.facebook.**{*;}
-dontwarn com.facebook.**

-keep class com.nostra13.universalimageloader.**{*;}

-keep class com.bumptech.glide.**{*;}

-keep class com.android.volley.**{*;}

-keep class com.bm.library.**{*;}

-keep class android.support.v7.**{*;}

-keep class bolts.**{*;}