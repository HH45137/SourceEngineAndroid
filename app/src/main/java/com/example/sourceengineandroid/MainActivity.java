package com.example.sourceengineandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.molihuan.pathselector.PathSelector;
import com.molihuan.pathselector.entity.FileBean;
import com.molihuan.pathselector.entity.FontBean;
import com.molihuan.pathselector.fragment.BasePathSelectFragment;
import com.molihuan.pathselector.listener.CommonItemListener;
import com.molihuan.pathselector.utils.MConstants;
import com.molihuan.pathselector.utils.Mtools;

import org.libsdl.app.SDLActivity;
import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static String srcengDir = null;
    public static List<String> gameModList = null;
    public static int chioseModIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
    }

    private void openGameDir() {

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
        EditText edit_gamepath = findViewById(R.id.edit_gamepath);
        edit_gamepath.setText(srcengDir);

        openGameDir();
    }

    public void runGame(View view) {
        if (!isSelectGameRootDir()) {
            return;
        }

        Spinner tSpinner = findViewById(R.id.spinner_game_mods);
        chioseModIndex = tSpinner.getSelectedItemPosition();
        if (chioseModIndex < 0) {
            new AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage("请选择要启动的mod！")
                    .setPositiveButton("OK", (dialog, which) -> {

                    })
                    .show();
            return;
        }

        Intent intent = new Intent(this, SDLActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void refreshGameMods(View view) {
        if (!isSelectGameRootDir()) {
            return;
        }

        // choice game mod
        Spinner spinnerGameMods_Item = findViewById(R.id.spinner_game_mods);
        ArrayAdapter adapter = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinnerGameMods_Item.setAdapter(adapter);
        for (String item : gameModList) {
            adapter.add(item);
        }
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
            if (sl.isEmpty()) {
                return null;
            }
        }

        return sl;
    }

    private boolean isSelectGameRootDir() {

        if (MainActivity.srcengDir == null) {
            new AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage("请选择游戏根目录里的一个文件比如 hl2.exe！")
                    .setPositiveButton("OK", (dialog, which) -> {

                    })
                    .show();
            return false;
        }

        gameModList = searchGameMods();
        if (gameModList == null) {
            new AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage("请选择正确的游戏目录")
                    .setPositiveButton("OK", (dialog, which) -> {

                    })
                    .show();
            return false;
        }

        return true;
    }

}