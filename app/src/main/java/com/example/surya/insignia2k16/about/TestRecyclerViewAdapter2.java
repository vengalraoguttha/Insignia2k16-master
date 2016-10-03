package com.example.surya.insignia2k16.about;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.surya.insignia2k16.R;

import java.util.List;

/**
 * Created by Havi Havish on 23-06-16.
 */
public class TestRecyclerViewAdapter2 extends RecyclerView.Adapter<TestRecyclerViewAdapter2.PersonViewHolder> {

    List<Object> contents;

    static final int TYPE_HEADER = 0;
    static final int TYPE_CELL = 1;
    int lastPosition=-1;
    Context context1;
    TextView personName;
    TextView personAge;
    ImageView personPhoto;

    public TestRecyclerViewAdapter2(List<Object> contents) {
        this.contents = contents;
    }

    /*public TestRecyclerViewAdapter22(List<Object> contents) {
        this.contents = contents;
    }*/

  /*  public TestRecyclerViewAdapter22(List<Object> contents) {this.contents = contents;
    }*/

    public static class PersonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView cv;
        TextView personName;
        TextView personAge;
        ImageView personPhoto;
        Context context;
        FrameLayout container;

        PersonViewHolder(View itemView) {
            super(itemView);

            context = itemView.getContext();
            cv = (CardView) itemView.findViewById(R.id.card_view);
            personName = (TextView) itemView.findViewById(R.id.person_name);
            personAge = (TextView) itemView.findViewById(R.id.person_contact);
            personPhoto = (ImageView) itemView.findViewById(R.id.person_photo);
            container=(FrameLayout)itemView.findViewById(R.id.frame_layout);
            cv.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            // RecyclerView.ViewHolder holder = (RecyclerView.ViewHolder) v.getTag();
            int position = /*holder*/getAdapterPosition();
            switch (position) {
                case 0:

                    i.setData(Uri.parse("http://www.google.com"));

                    break;

                case 1:
                    i.setData(Uri.parse("http://www.youtube.com"));
                    break;

                default:
                    i.setData(Uri.parse("http://www.facebook.com"));
                    break;
            }
            context.startActivity(i);
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

        context1=parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card2, parent, false);
        return new PersonViewHolder(view) {
        };

    }


    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {

        personViewHolder.personName.setText(contents.get(i).name);
        personViewHolder.personAge.setText(contents.get(i).age);
        personViewHolder.personPhoto.setImageResource(contents.get(i).photoId);
        setAnimation(personViewHolder.container,i);
    }
    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context1, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
                animation.setDuration(1000);
            }
            lastPosition = position;
        }
    }
}