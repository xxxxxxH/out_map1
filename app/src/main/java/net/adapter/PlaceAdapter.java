package net.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import net.basicmodel.R;
import net.entiy.BigPlaceEntity;
import net.entiy.SmallPlaceEntity;
import net.utils.OnItemClickListener;
import net.utils.ScreenUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolder> {

    ArrayList<BigPlaceEntity> data;
    ArrayList<SmallPlaceEntity> data1;
    Context context;
    Activity activity;
    OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public PlaceAdapter(ArrayList<BigPlaceEntity> data, ArrayList<SmallPlaceEntity> data1, Context context, Activity activity) {
        this.data = data;
        this.data1 = data1;
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_interactive, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        if (data != null) {
            ViewGroup.LayoutParams params = holder.bg.getLayoutParams();
            params.height = ScreenUtils.getScreenSize(activity)[0] / 3;
            holder.bg.setLayoutParams(params);
            Glide.with(context).load(data.get(position).getImageUrl()).into(holder.bg);
            holder.title.setText(data.get(position).getTitle());
            holder.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int pos = holder.getLayoutPosition();
                        listener.onItemClick(holder.root, pos, "big");
                    }
                }
            });
        } else if (data1 != null) {
            ViewGroup.LayoutParams params = holder.bg.getLayoutParams();
            params.height = ScreenUtils.getScreenSize(activity)[0] / 4;
            holder.bg.setLayoutParams(params);
            Glide.with(context).load(data1.get(position).getImageUrl()).into(holder.bg);
            holder.title.setText(data1.get(position).getTitle());
            holder.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int pos = holder.getLayoutPosition();
                        listener.onItemClick(holder.root, pos, "small");
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (data != null) {
            return data.size();
        } else {
            return data1.size();
        }

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView bg;
        TextView title;
        LinearLayout root;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            bg = itemView.findViewById(R.id.img);
            title = itemView.findViewById(R.id.img_name);
            root = itemView.findViewById(R.id.root);
        }
    }
}
