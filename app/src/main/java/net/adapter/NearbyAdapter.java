package net.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import net.basicmodel.R;
import net.utils.OnItemClickListener;
import net.utils.ScreenUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Copyright (C) 2021,2021/8/11, a Tencent company. All rights reserved.
 * <p>
 * User : v_xhangxie
 * <p>
 * Desc :
 */
public class NearbyAdapter extends RecyclerView.Adapter<NearbyAdapter.ViewHolder> {

    ArrayList<String> typeList;
    ArrayList<String> bgList;
    Context context;
    Activity activity;
    OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public NearbyAdapter(ArrayList<String> typeList, ArrayList<String> bgList, Context context,Activity activity) {
        this.typeList = typeList;
        this.bgList = bgList;
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_nearby, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        ViewGroup.LayoutParams params1 = holder.root.getLayoutParams();
        params1.width = ScreenUtils.getScreenSize(activity)[1] / 3;
        holder.root.setLayoutParams(params1);
        ViewGroup.LayoutParams params = holder.imageView.getLayoutParams();
        params.width = ScreenUtils.getScreenSize(activity)[1] / 5;
        params.height = ScreenUtils.getScreenSize(activity)[1] / 5;
        holder.imageView.setLayoutParams(params);
        holder.type.setText(typeList.get(position));
        Glide.with(context).load(bgList.get(position)).into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int pos = holder.getLayoutPosition();
                    listener.onItemClick(holder.imageView, pos, "");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return typeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView type;
        RelativeLayout root;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_bg);
            type = itemView.findViewById(R.id.item_name);
            root = itemView.findViewById(R.id.item_root);
        }
    }
}
