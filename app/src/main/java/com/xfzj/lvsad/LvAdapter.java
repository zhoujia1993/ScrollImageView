package com.xfzj.lvsad;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class LvAdapter extends BaseAdapter implements ImageCallback {
    private final Context context;
    private final ArrayList<String> datas;
    private ScrollImageView scrollImageView;

    public LvAdapter(Context context, ArrayList<String> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public String getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 4 ? 0 : 1;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            if (getItemViewType(position) == 0) {
                convertView = scrollImageView;
            } else {
                convertView = new TextView(context);
            }
        }
        if (getItemViewType(position) == 0) {


        } else {
            ((TextView) convertView).setText(datas.get(position));
        }
        return convertView;
    }


    @Override
    public void setImageView(@NotNull ScrollImageView imageView) {
        scrollImageView = imageView;
    }
}
