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

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.os.Vibrator;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.skyousuke.ivtool.R;
import com.skyousuke.ivtool.services.MainService.MainServiceBinder;
import com.skyousuke.ivtool.util.WindowManagerUtils;

/**
 * @author S.Kyousuke <surasek@gmail.com>
 */
public class HeadService extends Service {

    private int screenHeight;
    private int screenWidth;

    private WindowManager windowManager;
    private RelativeLayout headLayout;
    private RelativeLayout removeLayout;
    private ImageView head;
    private ImageView remove;

    private MainService mainService;
    private boolean mainServiceBounded;

    private boolean removeHead;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MainServiceBinder binder = (MainServiceBinder) iBinder;
            mainService = binder.getService();
            mainServiceBounded = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mainService = null;
            mainServiceBounded = false;
        }
    };

    @SuppressLint("InflateParams")
    @Override
    public void onCreate() {
        super.onCreate();
        updateScreenSize();

        final Toast toast = Toast.makeText(this,
                "Tap floating icon to show/hide window", Toast.LENGTH_SHORT);
        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
        if (v != null) v.setGravity(Gravity.CENTER);
        toast.show();

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        headLayout = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.tool_head, null);
        removeLayout = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.remove, null);
        head = (ImageView) headLayout.findViewById(R.id.head);
        remove = (ImageView) removeLayout.findViewById(R.id.remove);

        final GestureDetector gestureDetector = new GestureDetector(this, new SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                if (!mainServiceBounded) {
                    Intent mIntent = new Intent(HeadService.this, MainService.class);
                    bindService(mIntent, connection, BIND_AUTO_CREATE);
                } else {
                    mainService.onHeadPressed();
                }
                return true;
            }
        });

        final WindowManager.LayoutParams headParams =
                new WindowManager.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        LayoutParams.TYPE_PRIORITY_PHONE,
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                        PixelFormat.TRANSLUCENT);
        headParams.gravity = Gravity.TOP | Gravity.START;

        final WindowManager.LayoutParams removePrams =
                new WindowManager.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        LayoutParams.TYPE_PRIORITY_PHONE,
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                        PixelFormat.TRANSLUCENT);
        removePrams.gravity = Gravity.TOP | Gravity.START;

        windowManager.addView(removeLayout, removePrams);
        windowManager.addView(headLayout, headParams);

        removeLayout.setVisibility(View.GONE);

        headLayout.post(new Runnable() {
            @Override
            public void run() {
                headParams.x = screenWidth / 2 - headLayout.getWidth() / 2;
                headParams.y = screenHeight / 2 - headLayout.getHeight();
                windowManager.updateViewLayout(headLayout, headParams);
                addFadeOutAnimation();
            }
        });

        headLayout.setOnTouchListener(new View.OnTouchListener() {
            int x, y;
            float touchX, touchY;

            ScaleAnimation shrink = new ScaleAnimation(1, 0.85f, 1, 0.85f,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);

            ScaleAnimation grow = new ScaleAnimation(0.85f, 1f, 0.85f, 1f,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);

            {
                shrink.setDuration(200);
                shrink.setFillAfter(true);
                grow.setDuration(200);
                grow.setAnimationListener(new AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        addFadeOutAnimation();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
            }

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x = headParams.x;
                        y = headParams.y;

                        head.clearAnimation();
                        head.startAnimation(shrink);

                        touchX = motionEvent.getRawX();
                        touchY = motionEvent.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        final int removeWidth = removeLayout.getWidth();
                        final int removeHeight = removeLayout.getHeight();
                        final int headWidth = headLayout.getWidth();
                        final int headHeight = headLayout.getHeight();

                        headParams.x = (int) (x + motionEvent.getRawX() - touchX);
                        headParams.y = (int) (y + motionEvent.getRawY() - touchY);

                        removePrams.x = screenWidth / 2 - removeWidth / 2;
                        removePrams.y = (int) (screenHeight * 0.88f - removeHeight / 2);

                        float additionX = (headParams.x - removePrams.x) * 0.15f;
                        float additionY = (headParams.y - removePrams.y) * 0.15f;

                        float moveArea = WindowManagerUtils.pxToDp(HeadService.this, 48);
                        if (additionX > moveArea) {
                            additionX = moveArea;
                        } else if (additionX < -moveArea) {
                            additionX = -moveArea;
                        }
                        if (additionY > moveArea) {
                            additionY = moveArea;
                        } else if (additionY < -moveArea) {
                            additionY = -moveArea;
                        }

                        removePrams.x += additionX;
                        removePrams.y += additionY;

                        WindowManagerUtils.keepInScreen(head, headParams, screenWidth, screenHeight);
                        WindowManagerUtils.keepInScreen(remove, removePrams, screenWidth, screenHeight);

                        int headCenterX = headParams.x + headWidth / 2;
                        int headCenterY = headParams.y + headHeight / 2;
                        int removeCenterX = removePrams.x + removeWidth / 2;
                        int removeCenterY = removePrams.y + removeHeight / 2;

                        float squareDistance = (headCenterX - removeCenterX) * (headCenterX - removeCenterX)
                                + (headCenterY - removeCenterY) * (headCenterY - removeCenterY);

                        final int REMOVE_SQUARE_DISTANCE = (removeWidth) * (removeWidth);
                        if (squareDistance < REMOVE_SQUARE_DISTANCE) {
                            headParams.x = removeCenterX - headWidth / 2;
                            headParams.y = removeCenterY - headHeight / 2;
                            if (!removeHead) {
                                removeHead = true;
                                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                                v.vibrate(20);
                            }
                        } else {
                            removeHead = false;
                        }
                        windowManager.updateViewLayout(removeLayout, removePrams);
                        windowManager.updateViewLayout(headLayout, headParams);

                        if (removeLayout.getVisibility() == View.GONE) {
                            final int MIN_SQUARE_DISTANCE = (screenHeight / 16) * (screenHeight / 16);
                            boolean moveEnough = ((x - headParams.x) * (x - headParams.x)
                                    + (y - headParams.y) * (y - headParams.y)) > MIN_SQUARE_DISTANCE;
                            if (moveEnough) {
                                removeLayout.setVisibility(View.VISIBLE);
                            }
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (removeHead) {
                            stopSelf();
                        }
                        head.startAnimation(grow);
                        removeLayout.setVisibility(View.GONE);
                }
                gestureDetector.onTouchEvent(motionEvent);
                return false;
            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast toast = Toast.makeText(this,
                "Drag icon down to close this app", Toast.LENGTH_SHORT);
        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
        if (v != null) v.setGravity(Gravity.CENTER);
        toast.show();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        updateScreenSize();
    }

    private void updateScreenSize() {
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        windowManager.removeView(headLayout);
        windowManager.removeView(removeLayout);

        if (mainServiceBounded) {
            unbindService(connection);
            mainServiceBounded = false;
        }
        super.onDestroy();
    }

    private void addFadeOutAnimation() {
        final AlphaAnimation alpha = new AlphaAnimation(1, 0.6f);
        alpha.setFillAfter(true);
        alpha.setStartOffset(1000);
        alpha.setDuration(500);
        head.setAnimation(alpha);
    }

}
