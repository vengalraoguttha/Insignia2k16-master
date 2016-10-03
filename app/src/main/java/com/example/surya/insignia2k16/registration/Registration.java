package com.example.surya.insignia2k16.registration;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.surya.insignia2k16.Constants;
import com.example.surya.insignia2k16.R;

public class Registration extends AppCompatActivity {


    Spinner mSpinner;
    ArrayAdapter<String> mArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSpinner = (Spinner)findViewById(R.id.events_spinner);

        mArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, Constants.mEvents_names);

        mSpinner.setAdapter(mArrayAdapter);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


}
