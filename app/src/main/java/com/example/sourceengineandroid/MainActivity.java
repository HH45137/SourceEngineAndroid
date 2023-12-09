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

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            Log.d("Debug: ", "Permission obtained successfully.");
        }else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 201);
        }
    }

    private String openDir() {
        final String[] retStr = {null};

        //如果没有权限会自动申请权限
        PathSelector.build(this, MConstants.BUILD_DIALOG)//Dialog构建方式
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
                .setTitlebarMainTitle(new FontBean("Select game dir."))
                .setTitlebarBG(Color.rgb(255,127,39))
                .setRadio()
                .show();//开始构建

        return retStr[0];
    }

    public void start(View view) {
        checkPermission();
        String gameRootDir = openDir();
        Log.d("Debug: ", "Game root dir = " + gameRootDir);

        Launcher.run("");
    }
}