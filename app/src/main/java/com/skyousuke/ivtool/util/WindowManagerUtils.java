package com.skyousuke.ivtool.util;

import android.content.Context;
import android.view.View;
import android.view.WindowManager;

/**
 * @author S.Kyousuke <surasek@gmail.com>
 */
public class WindowManagerUtils {

    public static void keepInScreen(View view, WindowManager.LayoutParams params, int screenWidth, int screenHeight) {
        int maxX = screenWidth - view.getWidth();
        int maxY = screenHeight - view.getHeight();
        if (params.x > maxX) params.x = maxX;
        else if (params.x < 0) params.x = 0;
        if (params.y > maxY) params.y = maxY;
        else if (params.y < 0) params.y = 0;
    }

    public static float pxToDp(final Context context, final float px) {
        return px / context.getResources().getDisplayMetrics().density;
    }

    public static float dpToPx(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

}
