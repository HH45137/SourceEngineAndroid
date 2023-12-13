package com.nillerusr;

import android.content.SharedPreferences;
import java.io.FileOutputStream;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;

import android.content.res.AssetManager;
import android.util.Log;
import android.content.Context;
import android.content.pm.ApplicationInfo;

public class ExtractAssets
{
    public static String TAG = "ExtractAssets";
    static SharedPreferences mPref;

    public static final String VPK_NAME = "extras_dir.vpk";
    public static int PAK_VERSION = 9;

    private static int chmod(String path, int mode)
    {
        try {
            Log.d(TAG, "chmod " + Integer.toOctalString(mode) + " " + path + ": " + Runtime.getRuntime().exec("chmod " + Integer.toOctalString(mode) + " " + path).waitFor());
        } catch (Exception e) {
            Log.d(TAG, "chmod: Runtime not worked: " + e.toString());
        }
        try {
            return ((Integer) Class.forName("android.os.FileUtils").getMethod("setPermissions", new Class[]{String.class, Integer.TYPE, Integer.TYPE, Integer.TYPE}).invoke(null, new Object[]{path, Integer.valueOf(mode), Integer.valueOf(-1), Integer.valueOf(-1)})).intValue();
        } catch (Exception e2) {
            Log.d(TAG, "chmod: FileUtils not worked: " + e2.toString());
            return -1;
        }
    }

    public static void extractVPK(Context context, Boolean force)
    {
        ApplicationInfo appinf = context.getApplicationInfo();

        FileOutputStream os = null;
        try {
            if( mPref == null )
                mPref = context.getSharedPreferences("mod", 0);

            File file = new File( context.getFilesDir().getPath() +"/"+ VPK_NAME );
            if( !file.exists() )
                force = true;

            if( mPref.getInt( "pakversion", 0 ) == PAK_VERSION && !force )
                return;

            InputStream is = context.getAssets().open(VPK_NAME);
            os = new FileOutputStream( context.getFilesDir().getPath() +"/"+ VPK_NAME);
            byte[] buffer = new byte[8192];
            while (true) {
                int length = is.read(buffer);
                if (length <= 0)
                    break;

                os.write(buffer, 0, length);
            }

            SharedPreferences.Editor editor = mPref.edit();
            editor.putInt( "pakversion", PAK_VERSION );
            editor.commit();

            chmod(appinf.dataDir, 0777);
            chmod(context.getFilesDir().getPath(), 0777);
            chmod(context.getFilesDir().getPath() +"/"+ VPK_NAME, 0777);
        }
        catch (Exception e) {
            Log.e("SRCAPK", "Failed to extract vpk:" + e.toString());
        }
    }

    public static void extractAssets(Context context) {
        chmod(context.getApplicationInfo().dataDir, 511);
        chmod(context.getFilesDir().getPath(), 511);
        extractVPK(context);
        extractAsset(context, "DroidSansFallback.ttf", Boolean.valueOf(false));
        extractAsset(context, "LiberationMono-Regular.ttf", Boolean.valueOf(false));
        extractAsset(context, "dejavusans-boldoblique.ttf", Boolean.valueOf(false));
        extractAsset(context, "dejavusans-bold.ttf", Boolean.valueOf(false));
        extractAsset(context, "dejavusans-oblique.ttf", Boolean.valueOf(false));
        extractAsset(context, "dejavusans.ttf", Boolean.valueOf(false));
        extractAsset(context, "Itim-Regular.otf", Boolean.valueOf(false));
        extractAsset(context, "jf-openhuninn-2.0.ttf", Boolean.valueOf(false));
    }

    public static void extractAsset(Context context, String str, Boolean bool) {
        AssetManager am = context.getAssets();
        try {
            File file = new File(context.getFilesDir().getPath() + "/" + str);
            Boolean valueOf = Boolean.valueOf(file.exists());
            if (bool.booleanValue() || !valueOf.booleanValue()) {
                InputStream open = am.open(str);
                FileOutputStream file2 = new FileOutputStream(context.getFilesDir().getPath() + "/tmp");
                byte[] bArr = new byte[8192];
                while (true) {
                    int read = open.read(bArr);
                    if (read <= 0) {
                        break;
                    }
                    file2.write(bArr, 0, read);
                }
                file2.close();
                File file3 = new File(context.getFilesDir().getPath() + "/tmp");
                if (valueOf.booleanValue()) {
                    file.delete();
                }
                file3.renameTo(new File(context.getFilesDir().getPath() + "/" + str));
                chmod(context.getFilesDir().getPath() + "/" + str, 511);
            }
        } catch (Exception e) {
            Log.e("SRCAPK", "Failed to extract vpk:" + e.toString());
        }
    }

    public static void extractVPK(Context context) {
        boolean z = false;
        if (mPref == null) {
            mPref = context.getSharedPreferences("mod", 0);
        }
        if (mPref.getInt("pakversion", 0) != PAK_VERSION) {
            z = true;
        }
        extractAsset(context, VPK_NAME, Boolean.valueOf(z));
        SharedPreferences.Editor editor = mPref.edit();
        editor.putInt("pakversion", PAK_VERSION);
        editor.commit();
    }
}