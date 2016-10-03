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

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Havi Havish on 23-06-16.
 */
public class TestRecyclerViewAdapter extends RecyclerView.Adapter<TestRecyclerViewAdapter.PersonViewHolder> {

   static List<Object> contents;

    static final int TYPE_HEADER = 0;
    static final int TYPE_CELL = 1;
    int lastPosition=-1;
Context context1;
    TextView personName;
    TextView personAge;
    ImageView personPhoto;

    public TestRecyclerViewAdapter(List<Object> contents) {
        this.contents=contents;



    }

    /*public TestRecyclerViewAdapter(List<Object> contents) {
        this.contents = contents;
    }*/

  /*  public TestRecyclerViewAdapter(List<Object> contents) {this.contents = contents;
    }*/

    public static class PersonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView cv;
        TextView personName;
        TextView personAge;
        TextView personAge1;
        ImageView personPhoto;
        Context context;
        FrameLayout container;
        PersonViewHolder(View itemView)
        {
            super(itemView);

            context = itemView.getContext();
            cv = (CardView)itemView.findViewById(R.id.card_view);
            personName = (TextView)itemView.findViewById(R.id.person_name);
            personAge = (TextView)itemView.findViewById(R.id.person_contact);
            personAge1 = (TextView)itemView.findViewById(R.id.person_contact1);
            personPhoto = (CircleImageView)itemView.findViewById(R.id.person_photo);
            container=(FrameLayout)itemView.findViewById(R.id.frame_layout);


            personAge.setOnClickListener(this);
            personAge1.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            final Intent intent,intent1;
            // RecyclerView.ViewHolder holder = (RecyclerView.ViewHolder) v.getTag();
            int position = /*holder*/getAdapterPosition();
            if (v == personAge) {


                switch (position) {
                    case 0:
                        intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "9560468502"));
                        break;

                    case 1:
                        intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "9989178319"));
                        break;

                    default:
                        intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "8456971230"));
                        break;
                }
                context.startActivity(intent);
            }
            if(v==personAge1)
            {

                switch (position) {
                    case 0:
                        intent1 = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                                "mailto","abc@gmail.com", null));
                        break;

                    case 1:
                        intent1 = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                                "mailto","abc@gmail.com", null));
                        break;

                    default:
                        intent1 = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                                "mailto","abc@gmail.com", null));
                        break;
                }
                context.startActivity(intent1);
            }
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
      //  for (int i=0;i<4;i++)
        //Bitmap icon = BitmapFactory.decodeResourceStream(context1.getResources(),contents.get(i).photoId);
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.card1, parent, false);
                return new PersonViewHolder(view) {
                };

        }


    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {


        personViewHolder.personName.setText(contents.get(i).name);
        personViewHolder.personAge.setText(contents.get(i).age);
        personViewHolder.personAge1.setText(contents.get(i).mail);
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
                animation.setDuration(500);
            }
            lastPosition = position;
        }
    }


}
