package com.example.surya.insignia2k16.events_main;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.surya.insignia2k16.Constants;
import com.example.surya.insignia2k16.R;

/**
 * Created by surya on 23-08-2016.
 */

public class Events_Adapter extends RecyclerView.Adapter<Events_Adapter.MyViewHolder> {


    Typeface custom_font;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mList_item_text;
        public TextView mList_item_venue;
        public ImageView mList_image_view;

        public MyViewHolder(View view) {
            super(view);
            mList_item_text = (TextView)view.findViewById(R.id.event_name_textview);
            mList_image_view = (ImageView)view.findViewById(R.id.event_image_view);
            mList_item_venue = (TextView)view.findViewById(R.id.venue_name);
        }
    }

    public Events_Adapter() {

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.events_recyclerview, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mList_item_text.setText(Constants.mEvents_names[position]);
        holder.mList_image_view.setImageResource(Constants.mEvents_posters[position]);
        holder.mList_item_venue.setText(Constants.event_name);
    }

    @Override
    public int getItemCount() {
        return Constants.mEvents_names.length;
    }
}