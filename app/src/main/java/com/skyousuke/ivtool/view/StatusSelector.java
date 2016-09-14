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
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.skyousuke.ivtool.R;
import com.skyousuke.ivtool.util.TextFormat;

/**
 * @author S.Kyousuke <surasek@gmail.com>
 */
public class StatusSelector extends RelativeLayout {

    private int minValue;
    private int maxValue;
    private int value;
    private int step;
    private int divider;
    private int decimalPlaces;

    private TextView valueTextView;
    private TextView labelTextView;
    private SeekBar bar;

    private StatusListener listener;

    public StatusSelector(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        /* Setup variable */
        View rootView = inflate(context, R.layout.status_selector, this);
        valueTextView = (TextView) rootView.findViewById(R.id.valueText);
        labelTextView = (TextView) rootView.findViewById(R.id.labelText);
        bar = (SeekBar) rootView.findViewById(R.id.valueBar);
        divider = 1;

        /* Set up views attribute */
        TypedArray ta;
        int fontSize;
        int labelWidth;
        int valueWidth;
        final String labelText;
        Drawable thumb;
        Drawable progressDrawable;

        ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.StatusSelector, 0, 0);
        try {
            fontSize = ta.getDimensionPixelSize(R.styleable.StatusSelector_fontSize, 24);
            labelWidth = ta.getDimensionPixelSize(R.styleable.StatusSelector_labelWidth, 0);
            valueWidth = ta.getDimensionPixelSize(R.styleable.StatusSelector_valueWidth, 0);
            labelText = ta.getString(R.styleable.StatusSelector_labelText);
            thumb = ta.getDrawable(R.styleable.StatusSelector_thumb);
            progressDrawable = ta.getDrawable(R.styleable.StatusSelector_progressDrawable);
        } finally {
            ta.recycle();
        }

        valueTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize);
        if (valueWidth != 0)
            valueTextView.setWidth(valueWidth);

        labelTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize);
        if (labelText != null) labelTextView.setText(labelText);
        if (labelWidth != 0)
            labelTextView.setWidth(labelWidth);

        if (thumb != null)
            bar.setThumb(thumb);
        if (progressDrawable != null)
            bar.setProgressDrawable(progressDrawable);

        bar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean formUser) {
                if (formUser) {
                    setValue(minValue + progress * step);
                    updateValueText();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        setBarRange(minValue, maxValue);
        setValue(value);
    }

    public void setMinValue(int minValue) {
        setBarRange(minValue, maxValue);
    }

    public void setMaxValue(int maxValue) {
        setBarRange(minValue, maxValue);
    }

    public void setBarRange(int minValue, int maxValue) {
        setBarRange(minValue, maxValue, 1);
    }

    public void setBarRange(int minValue, int maxValue, int step) {
        this.step = step;
        this.maxValue = (maxValue / step) * step;
        this.minValue = (minValue / step) * step;
        if (minValue > maxValue) minValue = maxValue;
        bar.setMax((maxValue - minValue) / step);
        setValue(value);
    }

    public void setValue(int value) {
        value = (value / step) * step;
        if (value > maxValue)
            this.value = maxValue;
        else if (value < minValue)
            this.value = minValue;
        else this.value = value;

        updateValueText();
        updateBar();
        if (listener != null) listener.onStatusChange(getDividedValue());
    }

    private void updateBar() {
        bar.post(new Runnable() {
            @Override
            public void run() {
                bar.setProgress((value - minValue) / step);
            }
        });
    }

    private void updateValueText() {
        if (divider == 1) valueTextView.setText(String.valueOf(value));
        else if (divider > 1) {
            valueTextView.setText(TextFormat.formatDecimal(getDividedValue(), decimalPlaces));
        }
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("state", super.onSaveInstanceState());
        bundle.putInt("minValue", minValue);
        bundle.putInt("maxValue", maxValue);
        bundle.putInt("value", value);
        bundle.putInt("step", step);
        return bundle;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            minValue = bundle.getInt("minValue");
            maxValue = bundle.getInt("maxValue");
            value = bundle.getInt("value");
            step = bundle.getInt("step");
            state = bundle.getParcelable("state");
        }
        super.onRestoreInstanceState(state);
        setValue(value);
    }

    public float getDividedValue() {
        return (float) value / divider;
    }

    public float getDividedMaxValue()  {
        return (float) maxValue / divider;
    }

    public float getDividedMinValue()   {
        return (float) minValue / divider;
    }

    public int getDivider() {
        return divider;
    }

    public void setDecimalFormat(int divider, int decimalPlaces) {
        this.divider = divider;
        this.decimalPlaces = decimalPlaces;
        updateValueText();
    }

    public void setListener(StatusListener listener) {
        this.listener = listener;
    }

}
