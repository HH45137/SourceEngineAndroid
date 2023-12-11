package com.valvesoftware;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Environment;

import java.util.Locale;

public class ValveActivity2 {
    public static native void setArgs(String args);
    public static native int setenv(String name, String value, int overwrite);

    public static void initNatives(Context context, Intent intent) {
        setenv( "APP_MOD_LIB", "/storage/emulated/0/srceng/", 1 );
        setenv( "EXTRAS_VPK_PATH", "/storage/emulated/0/srceng/", 1 );
        setenv( "LANG", Locale.getDefault().toString(), 1 );
        setenv( "APP_DATA_PATH", context.getApplicationInfo().dataDir, 1);
        setenv( "APP_LIB_PATH", context.getApplicationInfo().nativeLibraryDir, 1);
        setenv( "VALVE_GAME_PATH", "/storage/emulated/0/srceng/", 1 );

        setArgs("-game hl2 -console");
    }
}
