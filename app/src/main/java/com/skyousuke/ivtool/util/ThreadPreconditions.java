package com.skyousuke.ivtool.util;

import android.os.Looper;

import com.skyousuke.ivtool.BuildConfig;

/**
 * @author S.Kyousuke <surasek@gmail.com>
 */
public class ThreadPreconditions {
    public static void checkOnMainThread() {
        if (BuildConfig.DEBUG) {
            if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
                throw new IllegalStateException("This method should be called from the Main Thread");
            }
        }
    }
}
