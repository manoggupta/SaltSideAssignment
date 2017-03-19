package com.android.saltside.module;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.saltside.R;
import com.android.saltside.SaltSideApp;
import com.android.saltside.model.ItemData;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

/**
 * Created by manoj on 3/18/2017.
 */

public class ListItemAdapter extends RecyclerView.Adapter<ListItemAdapter.ItemViewHolder> {

    private ArrayList<ItemData> dataArrayList;

    ListItemAdapter(ArrayList<ItemData> dataArrayList){
        this.dataArrayList = dataArrayList;
    }

    @Override
    public int getItemCount() {
        return dataArrayList != null ? dataArrayList.size() : 0;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {
        ItemData itemData = dataArrayList.get(position);
        String url = itemData.getImageUrl();
        if(url != null && url.startsWith("http")){
            url = url.replace("http", "https");
        }
        holder.imageView.setImageUrl(url, SaltSideApp.getInstance().getImageLoader());
        holder.titleTv.setText(itemData.getTitle());
        holder.descTv.setText(itemData.getDescription());
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView titleTv;
        private TextView descTv;
        private NetworkImageView imageView;

        ItemViewHolder(View itemView) {
            super(itemView);
            titleTv = (TextView) itemView.findViewById(R.id.titleTv);
            descTv = (TextView) itemView.findViewById(R.id.descTv);
            imageView = (NetworkImageView) itemView.findViewById(R.id.imageView);
        }
    }
}
