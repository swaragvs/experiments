# proguard-rules.pro

-keep class org.tensorflow.** { *; }

-keep class org.tensorflow.lite.** { *; }

-keep class com.google.** { *; }



# Keep model metadata accessors used by tflite-support

-keepclassmembers class * {

    @org.tensorflow.lite.support.metadata.* *;

}