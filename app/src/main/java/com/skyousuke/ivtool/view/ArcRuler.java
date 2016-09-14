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

package com.skyousuke.ivtool.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author S.Kyousuke <surasek@gmail.com>
 */
public class ArcRuler extends View {

    private static final float STROKE_WIDTH = 3;

    private Paint basePaint;
    private Paint rulerPaint;

    private float angle;

    public ArcRuler(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        basePaint = new Paint();
        basePaint.setColor(Color.BLACK);
        basePaint.setStrokeWidth(STROKE_WIDTH);

        rulerPaint = new Paint();
        rulerPaint.setColor(Color.RED);
        rulerPaint.setStrokeWidth(STROKE_WIDTH);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        View parent = (View) getParent();

        int desiredWidth = parent.getWidth() - parent.getPaddingLeft() - parent.getPaddingRight();
        int desiredHeight = desiredWidth / 2;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        //Measure Width
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            width = Math.min(desiredWidth, widthSize);
        } else {
            width = desiredWidth;
        }

        //Measure Height
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            height = Math.min(desiredHeight, heightSize);
        } else {
            height = desiredHeight;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    public void onDraw(Canvas canvas) {
        final float width = getWidth();
        final float height = getHeight();
        final float originX = width / 2;
        final float originY = height - STROKE_WIDTH;

        final float radius = width / 2;
        final float rulerX = radius + (float) (radius * Math.cos(Math.toRadians(angle)));
        final float rulerY = getHeight() - STROKE_WIDTH - (float) (radius * Math.sin(Math.toRadians(angle)));

        canvas.drawLine(0, originY, width, originY, basePaint);
        canvas.drawLine(originX, originY, rulerX, rulerY, rulerPaint);
    }

    public void setAngle(float value) {
        angle = value;
        if (angle > 180 )angle %= 180;
        invalidate();
    }

    public void addAngle(float value) {
        setAngle(angle + value);
    }
}
