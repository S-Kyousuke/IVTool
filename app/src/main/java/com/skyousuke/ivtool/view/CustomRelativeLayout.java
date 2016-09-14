package com.skyousuke.ivtool.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.RelativeLayout;

/**
 * @author S.Kyousuke <surasek@gmail.com>
 */
public class CustomRelativeLayout extends RelativeLayout {

    private BackKeyListener backKeyListener;

    public CustomRelativeLayout(Context context) {
        super(context);
    }

    public CustomRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (backKeyListener != null) backKeyListener.onBackKeyPressed();
        }
        return super.dispatchKeyEvent(event);
    }

    public void setBackKeyListener(BackKeyListener backKeyListener) {
        this.backKeyListener = backKeyListener;
    }

}
