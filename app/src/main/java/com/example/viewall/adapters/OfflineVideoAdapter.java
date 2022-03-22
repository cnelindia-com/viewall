package com.example.viewall.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.viewall.R;
import com.example.viewall.models.databasemodels.VideoModel;

import java.util.List;

public class OfflineVideoAdapter extends RecyclerView.Adapter<OfflineVideoAdapter.OfflineViewHolder> {

    Context context;
    List<VideoModel> list;

    public OfflineVideoAdapter(Context context, List<VideoModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public OfflineVideoAdapter.OfflineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.offline_list_item, parent, false);
        return new OfflineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OfflineVideoAdapter.OfflineViewHolder holder, int position) {
        VideoModel videoModel = list.get(position);

        holder.videoNameId.setText(videoModel.getName());
        holder.videoUrlId.setText(videoModel.getVideoUrl());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class OfflineViewHolder extends RecyclerView.ViewHolder {

        LinearLayout rootLayoutId;
        TextView videoNameId, videoUrlId;

        public OfflineViewHolder(@NonNull View itemView) {
            super(itemView);
            rootLayoutId = itemView.findViewById(R.id.rootLayoutId);
            videoNameId = itemView.findViewById(R.id.videoNameId);
            videoUrlId = itemView.findViewById(R.id.videoUrlId);
        }
    }
}
