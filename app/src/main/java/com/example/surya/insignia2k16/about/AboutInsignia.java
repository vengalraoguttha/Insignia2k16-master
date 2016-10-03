package com.example.surya.insignia2k16.about;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.surya.insignia2k16.R;

public class AboutInsignia extends AppCompatActivity {

    ViewPager mViewPager;
    Toolbar mActionBarToolbar;
    changer c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("xxxxxxxx","xxxxxxx");
        setContentView(R.layout.activity_about_insignia);
        c = new changer(getSupportFragmentManager());

        mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setTitle("About Insignia");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(c);

        //Add a tab bar navigation
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }
}

class changer extends FragmentPagerAdapter
{

    public changer(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Log.i("xxxxxxxx",String.valueOf(position));
        Fragment fragment=null;
        switch (position)
        {
            case 0: return new f1();
            case 1: return  new f2();
            case 2: return   new f3();
            case 3:   return new f4();


        }
        return new f2();
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position)
        {
            case 0:  return "Contacts";
            case 1:  return "Sponsors";
            case 2:  return "FAQ";
            case 3:  return "About";

        }
        return null;
    }
}