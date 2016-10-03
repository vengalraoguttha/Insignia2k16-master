package com.example.surya.insignia2k16.about;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.surya.insignia2k16.R;

import java.util.List;

/**
 * Created by hp on 02-07-2016.
 */
public class TestRecyclerViewAdapter4 extends RecyclerView.Adapter<TestRecyclerViewAdapter4.PersonViewHolder> {
    List<Object> contents;

    static final int TYPE_HEADER = 0;
    static final int TYPE_CELL = 1;

    public TestRecyclerViewAdapter4(List<Object> contents) {
        this.contents = contents;
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder  {

        CardView cv;
        TextView personName;
        TextView personAge;

        TextView personName1;
        TextView personAge1;
        ImageView personPhoto;
        Context context;
        FrameLayout container;

        PersonViewHolder(View itemView) {
            super(itemView);

            context = itemView.getContext();
            cv = (CardView) itemView.findViewById(R.id.cv);
            personName = (TextView) itemView.findViewById(R.id.person_name);
            personAge = (TextView) itemView.findViewById(R.id.person_contact);

            personName1 = (TextView) itemView.findViewById(R.id.person_name1);
            personAge1 = (TextView) itemView.findViewById(R.id.person_contact1);
            personPhoto = (ImageView) itemView.findViewById(R.id.person_photo);
            container=(FrameLayout)itemView.findViewById(R.id.frame_layout);
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return TYPE_HEADER;
            default:
                return TYPE_CELL;
        }
    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card4, parent, false);
        return new PersonViewHolder(view) {
        };

    }


    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {

        personViewHolder.personName.setText(contents.get(i).name);
        personViewHolder.personAge.setText(contents.get(i).age);

        personViewHolder.personName1.setText(contents.get(i).name1);
        personViewHolder.personAge1.setText(contents.get(i).age1);
        personViewHolder.personPhoto.setImageResource(contents.get(i).photoId);

    }

}
