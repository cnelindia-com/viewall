package com.example.viewall.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.viewall.R;
import com.example.viewall.activities.HomeActivity;
import com.example.viewall.activities.VideoShowActivity;

public class PopularVideoHomeAdapter extends RecyclerView.Adapter<PopularVideoHomeAdapter.PopularViewHolder> {

    Context context;
    int popularList[];

    public PopularVideoHomeAdapter(Context context, int[] popularList) {
        this.context = context;
        this.popularList = popularList;
    }

    @NonNull
    @Override
    public PopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.popularvideo_item, parent, false);
        return new PopularViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularViewHolder holder, int position) {
        holder.imgVid.setImageResource(popularList[position]);

        holder.imgVid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, VideoShowActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return popularList.length;
    }

    public class PopularViewHolder extends RecyclerView.ViewHolder {

        ImageView imgVid;

        public PopularViewHolder(@NonNull View itemView) {
            super(itemView);

            imgVid = itemView.findViewById(R.id.imgVid);
        }
    }
}
