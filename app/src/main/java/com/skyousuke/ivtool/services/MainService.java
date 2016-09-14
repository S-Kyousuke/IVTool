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

package com.skyousuke.ivtool.services;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.view.WindowManager;

import com.skyousuke.ivtool.windows.MainWindow;
import com.skyousuke.ivtool.windows.ResultWindow;

/**
 * @author S.Kyousuke <surasek@gmail.com>
 */
public class MainService extends Service {

    private MainWindow mainWindow;
    private ResultWindow resultWindow;

    private MainServiceBinder binder;
    private Handler handler;

    @Override
    public void onCreate() {
        super.onCreate();

        WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        binder = new MainServiceBinder();
        handler = new Handler();

        resultWindow = new ResultWindow(this, windowManager, handler);
        mainWindow = new MainWindow(this, windowManager, handler);

        resultWindow.setMainWindow(mainWindow);
        mainWindow.setResultWindow(resultWindow);

        updateScreenSize();
        mainWindow.show();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        updateScreenSize();
    }

    private void updateScreenSize() {
        final int screenWidth = getResources().getDisplayMetrics().widthPixels;
        final int screenHeight = getResources().getDisplayMetrics().heightPixels;

        mainWindow.updateScreenSize(screenWidth, screenHeight);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onDestroy() {
        if (mainWindow.hasShown()) mainWindow.hide();
        if (resultWindow.hasShown()) resultWindow.hide();
        super.onDestroy();
    }

    public void onHeadPressed() {
        if (resultWindow.hasShown()) {
            resultWindow.hide();
        }
        else mainWindow.toggleVisibility();
    }

    public class MainServiceBinder extends Binder {
        public MainService getService() {
            return MainService.this;
        }
    }

}
