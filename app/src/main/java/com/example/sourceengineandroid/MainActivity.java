package com.example.sourceengineandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static String srcengDir = null;
    public static List<String> gameModList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void openGame() {

        PathSelector.build(this, MConstants.BUILD_DIALOG)
                .setMorePopupItemListeners(
                        new CommonItemListener("OK") {
                            @Override
                            public boolean onClick(View v, TextView tv, List<FileBean> selectedFiles, String currentPath, BasePathSelectFragment pathSelectFragment) {
                                StringBuilder builder = new StringBuilder();
                                for (FileBean fileBean : selectedFiles) {
                                    builder.append(fileBean.getPath());
                                }
                                Mtools.toast(builder.toString());
                                Log.d("Debug: ", builder.toString());
                                srcengDir = new File(builder.toString()).getParent();
                                return false;
                            }
                        }
                )
                .setTitlebarMainTitle(new FontBean("Select any file in srceng folder"))
                .setTitlebarBG(Color.rgb(255, 127, 39))
                .setRadio()
                .setSelectFileTypes()
                .show();
    }

    public void setPath(View view) {
        openGame();
    }

    public void runGame(View view) {
        if (MainActivity.srcengDir == null) {
            new AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage("请选择游戏根目录里的一个文件比如 hl2.exe！")
                    .setPositiveButton("OK", (dialog, which) -> {

                    })
                    .show();
            return;
        }

        gameModList = searchGameMods();
        if (gameModList == null) {
            new AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage("请选择正确的游戏目录")
                    .setPositiveButton("OK", (dialog, which) -> {

                    })
                    .show();
            return;
        }

        Intent intent = new Intent(this, SDLActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private List<String> searchGameMods() {
        List<String> sl = new ArrayList<>();

        if (srcengDir != null) {
            File tDir = new File(srcengDir);
            if (!tDir.isDirectory()) {
                return null;
            }

            String[] tDirs = tDir.list();
            for (String tStr : tDirs) {
                File tDir2 = new File(tDir + "/" + tStr);
                if (!tDir2.isDirectory()) {
                    continue;
                }

                // Find gameinfo.txt
                File tGameinfotxt = new File(tDir2 + "/" + "gameinfo.txt");
                if (tGameinfotxt.exists()) {
                    sl.add(tDir2.getName());
                }
            }
        }

        return sl;
    }
}