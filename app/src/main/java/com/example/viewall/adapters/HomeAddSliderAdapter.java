package com.example.viewall.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.viewall.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

public class HomeAddSliderAdapter extends SliderViewAdapter<HomeAddSliderAdapter.HomeAddAdapterVh> {

    int imageArray[];
    Context context;

    public HomeAddSliderAdapter(int[] imageArray, Context context) {
        this.imageArray = imageArray;
        this.context = context;
    }

    @Override
    public HomeAddAdapterVh onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
        return new HomeAddAdapterVh(view);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(HomeAddAdapterVh viewHolder, int position) {
        Glide.with(viewHolder.itemView)
                .load(imageArray[position])
                .into(viewHolder.homeAddSliderId);
    }

    @Override
    public int getCount() {
        return imageArray.length;
    }

    public class HomeAddAdapterVh extends ViewHolder {

        View itemView;
        ImageView homeAddSliderId;

        public HomeAddAdapterVh(View itemView) {
            super(itemView);
            homeAddSliderId = itemView.findViewById(R.id.homeAddSliderId);
            this.itemView = itemView;
        }
    }
}
