package com.example.surya.insignia2k16.events_main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.surya.insignia2k16.Constants;
import com.example.surya.insignia2k16.R;
import com.example.surya.insignia2k16.about.AboutInsignia;
import com.example.surya.insignia2k16.chat.GlobalChat;
import com.example.surya.insignia2k16.chat.auth.Login;
import com.example.surya.insignia2k16.instafeed.Instafeed;
import com.example.surya.insignia2k16.locate.Locate;
import com.google.android.gms.appinvite.AppInvite;
import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.android.gms.appinvite.AppInviteInvitationResult;
import com.google.android.gms.appinvite.AppInviteReferral;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,GoogleApiClient.OnConnectionFailedListener {

    public static final int REQUEST_INVITE=0;
    FirebaseAuth mFirebaseAuth;
    DatabaseReference mReference;
    FirebaseUser mFirebaseUser;
    FirebaseRemoteConfig mRemoteConfig;
    RecyclerView mRecyclerView;
    GoogleApiClient mGoogleApiClient;
    ImageView mImageView;
    private TextView mTextView;
    String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Initialize Firebase Remote Config.
        mRemoteConfig = FirebaseRemoteConfig.getInstance();

        // Define Firebase Remote Config Settings.
        FirebaseRemoteConfigSettings firebaseRemoteConfigSettings =
                            new FirebaseRemoteConfigSettings.Builder()
                                .setDeveloperModeEnabled(true)
                                .build();

        // Define default config values. Defaults are used when fetched config values are not
        // available. Eg: if an error occurred fetching values from the server.
        Map<String, Object> defaultConfigMap = new HashMap<>();
        defaultConfigMap.put("event_venue", "To be declared");

        // Apply config settings and default values.
        mRemoteConfig.setConfigSettings(firebaseRemoteConfigSettings);
        mRemoteConfig.setDefaults(defaultConfigMap);

        // Fetch remote config.
        fetchConfig();
        str="https://scontent-hkg3-1.xx.fbcdn.net/v/t1.0-9/12046939_1532014153757303_5061038059984759315_n.jpg?oh=90b7e15cf917331b7c5ae621adbe5840&oe=58AB8789";
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(AppInvite.API)
                .enableAutoManage(this, this)
                .build();

        // Check for App Invite invitations and launch deep-link activity if possible.
        // Requires that an Activity is registered in AndroidManifest.xml to handle
        // deep-link URLs.
        boolean autoLaunchDeepLink = true;
        AppInvite.AppInviteApi.getInvitation(mGoogleApiClient, this, autoLaunchDeepLink)
                .setResultCallback(
                        new ResultCallback<AppInviteInvitationResult>() {
                            @Override
                            public void onResult(AppInviteInvitationResult result) {
                                Log.d("vengal", "getInvitation:onResult:" + result.getStatus());
                                if (result.getStatus().isSuccess()) {
                                    // Extract information from the intent
                                    Intent intent = result.getInvitationIntent();
                                    String deepLink = AppInviteReferral.getDeepLink(intent);
                                    String invitationId = AppInviteReferral.getInvitationId(intent);

                                    // Because autoLaunchDeepLink = true we don't have to do anything
                                    // here, but we could set that to false and manually choose
                                    // an Activity to launch to handle the deep link here.
                                    // ...
                                }
                            }
                        });


        //recycler view
        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(layoutManager);

        Events_Adapter myAdapter = new Events_Adapter();

        mRecyclerView.setAdapter(myAdapter);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mReference = FirebaseDatabase.getInstance().getReference();

        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                        Toast.makeText(MainActivity.this, Constants.mEvents_names[position] + position, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this,detail_events.class);
                        intent.putExtra("p",position);
                        startActivity(intent);

                    }
                })
        );


        /*end of newly added*/


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header=navigationView.getHeaderView(0);
        mTextView = (TextView)header.findViewById(R.id.email_main);
        mImageView = (ImageView) header.findViewById(R.id.profile_image_main);
    }

    private void fetchConfig() {

             // Fetch the config to determine the allowed length of messages.
            long cacheExpiration = 3600; // 1 hour in seconds
            // If developer mode is enabled reduce cacheExpiration to 0 so that
            // each fetch goes to the server. This should not be used in release
            // builds.
            if (mRemoteConfig.getInfo().getConfigSettings()
                    .isDeveloperModeEnabled()) {
                cacheExpiration = 0;
            }
            mRemoteConfig.fetch(cacheExpiration)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Make the fetched config available via
                            // FirebaseRemoteConfig get<type> calls.
                            mRemoteConfig.activateFetched();
                            applyRetrievedLengthLimit();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // There has been an error fetching the config
                            Log.w("hi", "Error fetching config: " +
                                    e.getMessage());
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            applyRetrievedLengthLimit();
                        }
                    });

    }

    private void applyRetrievedLengthLimit() {

         Constants.event_name = mRemoteConfig.getString("event_venue");


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            mFirebaseAuth.signOut();
            startActivity(new Intent(MainActivity.this,Login.class));
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_chat) {

            startActivity(new Intent(MainActivity.this,GlobalChat.class));

        } else if (id == R.id.nav_Instafeed) {

            startActivity(new Intent(MainActivity.this, Instafeed.class));

        } else if (id == R.id.nav_about) {

            startActivity(new Intent(MainActivity.this, AboutInsignia.class));

        } else if (id == R.id.nav_locate) {

            startActivity(new Intent(MainActivity.this, Locate.class));

        } else if (id == R.id.nav_share) {
                onInviteClicked();
        } else if (id == R.id.nav_send) {

            Intent Email = new Intent(Intent.ACTION_SEND);
            Email.setType("text/email");
            Email.putExtra(Intent.EXTRA_EMAIL, new String[] { "insignia.alphaz@gmail.com" });
            Email.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
            Email.putExtra(Intent.EXTRA_TEXT, "Dear ...," + "");
            startActivity(Intent.createChooser(Email, "Send Feedback:"));
            return true;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("vengal", "onActivityResult: requestCode=" + requestCode + ", resultCode=" + resultCode);

        if (requestCode == REQUEST_INVITE) {
            if (resultCode == RESULT_OK) {
                // Get the invitation IDs of all sent messages
                String[] ids = AppInviteInvitation.getInvitationIds(resultCode, data);
                for (String id : ids) {
                    Log.d("vengal", "onActivityResult: sent invitation " + id);
                }
            } else {
                // Sending failed or it was canceled, show failure message to the user
                // ...
            }
        }
    }

    private void onInviteClicked() {
        Intent intent = new AppInviteInvitation.IntentBuilder(getString(R.string.invitation_title))
                .setMessage(getString(R.string.invitation_message))
                //.setDeepLink(Uri.parse(getString(R.string.invitation_deep_link)))
                .setCustomImage(Uri.parse(getString(R.string.invitation_custom_image)))
                .setCallToActionText(getString(R.string.invitation_cta))
                .build();
        startActivityForResult(intent,REQUEST_INVITE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mFirebaseUser == null){
            Toast.makeText(MainActivity.this, "null", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this,Login.class));
            finish();
            return;
        }
        if (mFirebaseUser.getDisplayName() != null)
        mTextView.setText(mFirebaseUser.getDisplayName());
        else
        mTextView.setText(mFirebaseUser.getEmail());
        if (mFirebaseUser.getPhotoUrl() != null)
        Picasso.with(this).load(mFirebaseUser.getPhotoUrl()).into(mImageView);
    }



    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
