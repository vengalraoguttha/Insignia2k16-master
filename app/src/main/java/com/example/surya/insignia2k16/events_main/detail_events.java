package com.example.surya.insignia2k16.events_main;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.surya.insignia2k16.Constants;
import com.example.surya.insignia2k16.R;
import com.example.surya.insignia2k16.registration.Registration;

public class detail_events extends AppCompatActivity {

    Button mRegister_button;
    Button agree_btn,disagree_btn;
    TextView mEventName,mDetail_description;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_events);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout)
                findViewById(R.id.toolbar_layout);
        toolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
        toolbarLayout.setExpandedTitleTextAppearance(R.style.expandedappbar);

        //fonts

        mDetail_description = (TextView)findViewById(R.id.details_description_text);
        mRegister_button = (Button)findViewById(R.id.detail_register_button);
        mImageView = (ImageView)findViewById(R.id.collapsed_image);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Winners Receive Certification Of Appreciation By ALPHAZ", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        final int position = getIntent().getIntExtra("p",0);
        toolbarLayout.setTitle(Constants.mEvents_names[position]);


        mRegister_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registration(position);
            }
        });
        mImageView.setImageResource(Constants.mEvents_posters[position]);
        mDetail_description.setText(getString(Constants.mEvents_description[position]));

//
    }

    public void registration(int position){

        final Dialog dialog = new Dialog(detail_events.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.requirementsdialog);
        agree_btn = (Button)dialog.findViewById(R.id.button_agree);
        disagree_btn = (Button)dialog.findViewById(R.id.button_disAgree);
        mEventName = (TextView)dialog.findViewById(R.id.dialog_event_title);
        mEventName.setText(Constants.mEvents_names[position]);
        //navigate to registration activity
        if (agree_btn!=null)
            agree_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(detail_events.this,Registration.class);
                    startActivity(intent);
                    dialog.dismiss();
                }
            });
        disagree_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

}
