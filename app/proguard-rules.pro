# Preserve the line number information and hide the original source file name
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile
-keep class com.racobos.meeptechnicaltest.data.entities.** { *; }