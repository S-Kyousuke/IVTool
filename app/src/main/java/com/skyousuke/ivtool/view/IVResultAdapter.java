package com.skyousuke.ivtool.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.skyousuke.ivtool.IVResult;
import com.skyousuke.ivtool.R;
import com.skyousuke.ivtool.util.TextFormat;
import com.skyousuke.ivtool.util.ThreadPreconditions;
import com.skyousuke.ivtool.util.ViewHolder;

import java.util.Collections;
import java.util.List;

/**
 * @author S.Kyousuke <surasek@gmail.com>
 */
public class IVResultAdapter extends BaseAdapter {

    private List<IVResult> ivResults = Collections.emptyList();

    private final Context context;

    public IVResultAdapter(Context context) {
        this.context = context;
    }

    public void updateIVResults(List<IVResult> ivResults) {
        ThreadPreconditions.checkOnMainThread();
        this.ivResults = ivResults;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return ivResults.size();
    }

    @Override
    public IVResult getItem(int position) {
        return ivResults.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.iv_row, parent, false);
        }
        TextView levelView = ViewHolder.get(convertView, R.id.iv_row_level);
        TextView attackView = ViewHolder.get(convertView, R.id.iv_row_attack);
        TextView defenseView = ViewHolder.get(convertView, R.id.iv_row_defense);
        TextView staminaView = ViewHolder.get(convertView, R.id.iv_row_stamina);
        TextView percentView = ViewHolder.get(convertView, R.id.iv_row_percent);

        IVResult ivResult = getItem(position);

        float level = ivResult.getLevel();
        int intLevel = (int) level;
        if (level == intLevel) {
            levelView.setText(String.valueOf(intLevel));
        } else levelView.setText(TextFormat.formatDecimal(level, 1));

        attackView.setText(String.valueOf(ivResult.getAttack()));
        defenseView.setText(String.valueOf(ivResult.getDefense()));
        staminaView.setText(String.valueOf(ivResult.getStamina()));
        String percentText = TextFormat.formatDecimal(ivResult.getPercent(), 1) + " %";
        percentView.setText(percentText);

        return convertView;
    }
}
