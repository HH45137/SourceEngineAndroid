package com.valvesoftware;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Environment;

import com.example.sourceengineandroid.MainActivity;
import com.nillerusr.ExtractAssets;

import java.util.Locale;

public class ValveActivity2 {
    public static native void setArgs(String args);
    public static native int setenv(String name, String value, int overwrite);

    public static void initNatives(Context context, Intent intent) {
        ExtractAssets.extractAssets(context);

        setenv( "APP_MOD_LIB", MainActivity.srengDir, 1 );
        setenv( "EXTRAS_VPK_PATH", MainActivity.srengDir, 1 );
        setenv( "LANG", Locale.getDefault().toString(), 1 );
        setenv( "APP_DATA_PATH", context.getApplicationInfo().dataDir, 1);
        setenv( "APP_LIB_PATH", context.getApplicationInfo().nativeLibraryDir, 1);
        setenv( "VALVE_GAME_PATH", MainActivity.srengDir, 1 );

        setArgs("-game hl2 -nobackgroundlevel"); // 我们不需要多余的命令行参数，因为引擎启动器将在新版本使用-nobackgroundlevel来禁用加载背景而不是-console
    }
}
