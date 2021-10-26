-optimizationpasses 7
-verbose

# Remove logs
-assumenosideeffects class android.util.Log {
public static boolean isLoggable(java.lang.String, int);
public static int d(...);
public static int w(...);
public static int v(...);
public static int i(...);
public static int e(...);
}


-assumenosideeffects class timber.log.Timber* {
public static boolean isLoggable(java.lang.String, int);
public static *** d(...);
public static *** w(...);
public static *** v(...);
public static *** i(...);
public static *** e(...);
public static *** plant(...);
}

-assumenosideeffects class java.lang.Exception* {
public void printStackTrace();
}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
