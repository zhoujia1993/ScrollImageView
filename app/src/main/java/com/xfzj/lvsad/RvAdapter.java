package com.xfzj.lvsad;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RvAdapter extends RecyclerView.Adapter implements ImageCallback {

    private final Context context;
    private final ArrayList<String> datas;
    private ScrollImageView scrollImageView;

    public RvAdapter(Context context, ArrayList<String> datas) {
        this.context = context;
        this.datas = datas;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        if (viewType == 0) {
            holder = new RvHolder(scrollImageView);

        } else {
            holder = new RvHolder(new TextView(context));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        View view = holder.itemView;
        if (getItemViewType(position) == 0) {

        } else {
            ((TextView) view).setText(datas.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position == 4 ? 0 : 1;
    }

    @Override
    public void setImageView(@NotNull ScrollImageView imageView) {
        scrollImageView = imageView;
    }

    private static class RvHolder extends RecyclerView.ViewHolder {

        public RvHolder(View itemView) {
            super(itemView);
        }
    }
}
