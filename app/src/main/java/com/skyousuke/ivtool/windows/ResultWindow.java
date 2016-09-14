/*
 * Copyright 2016 Surasek Nusati <surasek@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.skyousuke.ivtool.windows;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.skyousuke.ivtool.IVResult;
import com.skyousuke.ivtool.Pokemon;
import com.skyousuke.ivtool.R;
import com.skyousuke.ivtool.view.BackKeyListener;
import com.skyousuke.ivtool.view.CustomRelativeLayout;
import com.skyousuke.ivtool.view.IVResultAdapter;

import java.util.List;

/**
 * @author S.Kyousuke <surasek@gmail.com>
 */
public class ResultWindow implements BackKeyListener {

    private final WindowManager.LayoutParams layoutParams =
            new WindowManager.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    LayoutParams.TYPE_PHONE, LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                    PixelFormat.TRANSLUCENT);

    private final Context context;
    private final WindowManager windowManager;
    private final Handler handler;

    private IVResultAdapter ivResultAdapter;

    private CustomRelativeLayout layout;

    private TextView pokemonView;
    private TextView cpView;
    private TextView hpView;
    private ProgressBar maxIVProgress;
    private ProgressBar minIVProgress;
    private TextView maxIVPercentView;
    private TextView minIVPercentView;
    private TextView combinationView;

    private MainWindow mainWindow;

    @SuppressLint("InflateParams")
    public ResultWindow(Context context, WindowManager windowManager, Handler handler) {
        this.context = context;
        this.windowManager = windowManager;
        this.handler = handler;

        layout = (CustomRelativeLayout) LayoutInflater.from(context).inflate(R.layout.result, null);
        pokemonView = (TextView) layout.findViewById(R.id.result_pokemon);
        cpView = (TextView) layout.findViewById(R.id.result_cp);
        hpView = (TextView) layout.findViewById(R.id.result_hp);
        maxIVProgress = (ProgressBar) layout.findViewById(R.id.progress_result_max_iv);
        minIVProgress = (ProgressBar) layout.findViewById(R.id.progress_result_min_iv);
        maxIVPercentView = (TextView) layout.findViewById(R.id.result_max_iv);
        minIVPercentView = (TextView) layout.findViewById(R.id.result_min_iv);
        combinationView = (TextView) layout.findViewById(R.id.result_combination);
        ListView ivList = (ListView) layout.findViewById(R.id.list_result_iv);
        Button closeButton = (Button) layout.findViewById(R.id.button_result_close);

        ivResultAdapter = new IVResultAdapter(context);
        ivList.setAdapter(ivResultAdapter);

        layoutParams.gravity = Gravity.TOP | Gravity.CENTER;
        layoutParams.screenOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT;
        layout.setBackKeyListener(this);

        closeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                backToToolWindow();
            }
        });
    }

    public void show() {
        windowManager.addView(layout, layoutParams);
    }

    public void hide() {
        windowManager.removeView(layout);
    }

    public boolean hasShown() {
        return layout.getParent() != null;
    }

    private void backToToolWindow() {
        hide();
        if (mainWindow != null) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mainWindow.enableBackKey(true);
                }
            }, 100);
        }
    }

    @Override
    public void onBackKeyPressed() {
        backToToolWindow();
    }

    public void updateResult(Pokemon pokemon, int cp, int hp, int minIV, int maxIV,
                             String minIVText, String maxIVText, List<IVResult> ivResults) {
        pokemonView.setText(pokemon.toString());
        cpView.setText(context.getString(R.string.result_cp, cp));
        hpView.setText(context.getString(R.string.result_hp, hp));
        minIVProgress.setProgress(minIV);
        maxIVProgress.setProgress(maxIV);
        minIVPercentView.setText(minIVText);
        maxIVPercentView.setText(maxIVText);
        combinationView.setText(context.getString(R.string.possible_combinations, ivResults.size()));
        ivResultAdapter.updateIVResults(ivResults);
    }

    public void setMainWindow(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }
}
