package com.skyousuke.ivtool.view;

import android.content.Context;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.TextViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.skyousuke.ivtool.R;
import com.skyousuke.ivtool.util.ViewHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author S.Kyousuke <surasek@gmail.com>
 */
public class HintSpinnerAdapter extends BaseAdapter {

    private final Context context;
    private List<Object> data;
    private final String hint;
    private boolean showHint;
    private int color;

    public HintSpinnerAdapter(Context context, Object[] data, String hint, int color) {
        this.context = context;
        this.data = new ArrayList<>();
        this.hint = hint;
        Collections.addAll(this.data, data);
        this.data.add(hint);
        this.color = color;
    }

    @Override
    public int getCount() {
        if (!showHint) return data.size() - 1;
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.simple_spinner, parent, false);
        }
        TextView text = ViewHolder.get(convertView, R.id.simple_spinner_text);
        Object item = getItem(position);
        text.setText(item.toString());
        if (isEqualHint(item)) {
            text.setPaintFlags(text.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            text.setTextColor(ContextCompat.getColor(context, R.color.dark_gray));
        }
        else {
            if (color == ContextCompat.getColor(context, R.color.dark_yellow)) {
                TextViewCompat.setTextAppearance(text, R.style.ShadowText);
            }
            else
                TextViewCompat.setTextAppearance(text, R.style.NoShadowText);
            text.setTextColor(color);
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.simple_dropdown, parent, false);
        }
        TextView text = ViewHolder.get(convertView, R.id.simple_dropdown_text);
        text.setText(getItem(position).toString());
        return convertView;
    }

    public boolean isEqualHint(Object object) {
        return hint != null && object != null && object.equals(hint);
    }

    public void showHint(Spinner spinner) {
        spinner.setSelection(data.size() - 1);
        showHint = true;
    }

    public void hideHint() {
        showHint = false;
    }

    public void setColor(int color) {
        this.color = color;
        notifyDataSetChanged();
    }
}
