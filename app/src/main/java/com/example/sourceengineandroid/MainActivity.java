package com.example.sourceengineandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.molihuan.pathselector.PathSelector;
import com.molihuan.pathselector.entity.FileBean;
import com.molihuan.pathselector.entity.FontBean;
import com.molihuan.pathselector.fragment.BasePathSelectFragment;
import com.molihuan.pathselector.listener.CommonItemListener;
import com.molihuan.pathselector.utils.MConstants;
import com.molihuan.pathselector.utils.Mtools;

import org.libsdl.app.SDLActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static String srengDir = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private String openGame() {
        final String[] retStr = {null};

        PathSelector.build(this, MConstants.BUILD_DIALOG)
                .setMorePopupItemListeners(
                        new CommonItemListener("OK") {
                            @Override
                            public boolean onClick(View v, TextView tv, List<FileBean> selectedFiles, String currentPath, BasePathSelectFragment pathSelectFragment) {
                                StringBuilder builder = new StringBuilder();
                                builder.append("you selected:\n");
                                for (FileBean fileBean : selectedFiles) {
                                    builder.append(fileBean.getPath() + "\n");
                                }
                                Mtools.toast(builder.toString());
                                Log.d("Debug: ", builder.toString());
                                retStr[0] = builder.toString();

                                return false;
                            }
                        }
                )
                .setTitlebarMainTitle(new FontBean("Select liblauncher.so"))
                .setTitlebarBG(Color.rgb(255,127,39))
                .setRadio()
                .show();

        return retStr[0];
    }

    public void setPath(View view) {
        MainActivity.srengDir = openGame();

        if (MainActivity.srengDir == null) {
            MainActivity.srengDir = "/storage/emulated/0/srceng/";
        }
    }

    public void runGame(View view) {
        Intent intent = new Intent(this, SDLActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
}