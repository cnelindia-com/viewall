package com.example.viewall.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.viewall.R;
import com.example.viewall.activities.SportsActivity;
import com.example.viewall.models.homecategorylist.DataItem;
import com.example.viewall.utils.SharePrefrancClass;

import java.util.ArrayList;

public class HomeCategoryAdapter extends RecyclerView.Adapter<HomeCategoryAdapter.HomeViewHolder>{

    Context context;
    ArrayList<DataItem> list;

    public HomeCategoryAdapter(Context context, ArrayList<DataItem> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public HomeCategoryAdapter.HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_category_item, parent, false);
        return new HomeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeCategoryAdapter.HomeViewHolder holder, int position) {
        DataItem dataItem = list.get(position);

        holder.txtCatId.setText(dataItem.getName());

        holder.rootLayoutId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Toast.makeText(context, dataItem.getId(), Toast.LENGTH_SHORT).show();*/
                SharePrefrancClass.getInstance(context).savePref("catIdFromHome", dataItem.getId());
                SharePrefrancClass.getInstance(context).savePref("catNameFromHome", dataItem.getName());
                Intent putDataIntent = new Intent(context, SportsActivity.class);
                putDataIntent.putExtra("catId", dataItem.getId());
                putDataIntent.putExtra("catName", dataItem.getName());
                context.startActivity(putDataIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {

        LinearLayout rootLayoutId;
        TextView txtCatId;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            rootLayoutId = itemView.findViewById(R.id.rootLayoutId);
            txtCatId = itemView.findViewById(R.id.txtCatId);
        }
    }
}
