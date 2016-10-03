package com.example.surya.insignia2k16.chat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.surya.insignia2k16.Constants;
import com.example.surya.insignia2k16.R;
import com.example.surya.insignia2k16.chat.auth.Login;
import com.example.surya.insignia2k16.chat.model.Messages;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class GlobalChat extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {


    EditText mEditText;
    RecyclerView mRecyclerView;
    FirebaseAuth mFirebaseAuth;
    DatabaseReference mReference;
    FirebaseUser mFirebaseUser;
    LinearLayoutManager mLayoutManager;
    RelativeLayout message_layout;
    String photoUrl = "a";
    FirebaseRecyclerAdapter<Messages,MessageViewHolder> mFirebaseAdapter;
    GoogleApiClient mGoogleApiClient;
    SharedPreferences prefs;
    CircleImageView mSend_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global_chat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mEditText = (EditText)findViewById(R.id.message_text);
        mSend_btn = (CircleImageView) findViewById(R.id.send_button);
        mRecyclerView = (RecyclerView)findViewById(R.id.chat_recyclerView);
        message_layout = (RelativeLayout)findViewById(R.id.message_view);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mReference = FirebaseDatabase.getInstance().getReference();

        prefs = getSharedPreferences("UserPrefs",MODE_PRIVATE);

        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        if (Constants.FLAG){
            //get username from google
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(this,this)
                    .addApi(Auth.GOOGLE_SIGN_IN_API)
                    .build();
            Constants.USERNAME = mFirebaseUser.getDisplayName();
            if(String.valueOf(mFirebaseUser.getPhotoUrl()) != null) {
                photoUrl = String.valueOf(mFirebaseUser.getPhotoUrl());

                Toast.makeText(GlobalChat.this, mFirebaseUser.getPhotoUrl() + Constants.USERNAME, Toast.LENGTH_SHORT).show();
            }

            SharedPreferences user_prefs = getSharedPreferences("UserPrefs",MODE_PRIVATE);
            SharedPreferences.Editor editor = user_prefs.edit();
            editor.putString("USERNAME",Constants.USERNAME);
            editor.putString("Profile",photoUrl);
            editor.apply();
        }
        Constants.USERNAME = prefs.getString("USERNAME","Insignia_user");

        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setStackFromEnd(true);

        mFirebaseAdapter = new FirebaseRecyclerAdapter<Messages, MessageViewHolder>(Messages.class,
                R.layout.message_list_item,
                MessageViewHolder.class,
                mReference.child(Constants.MESSAGES)) {
            @Override
            protected void populateViewHolder(MessageViewHolder viewHolder, Messages model, int position) {

                SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");

//                if (!model.getPhotoUrl().equals("a")){
//                    Picasso.with(GlobalChat.this).load(model.getPhotoUrl()).fit().into(viewHolder.mImageView_out);
//                    Picasso.with(GlobalChat.this).load(model.getPhotoUrl()).fit().into(viewHolder.mImageView_in);
//                }else{
//                    viewHolder.mImageView_in.setBackgroundResource(R.drawable.ic_person_black_36dp);
//                    viewHolder.mImageView_out.setBackgroundResource(R.drawable.ic_person_black_36dp);
//                }

                if (Constants.USERNAME.equals(model.getUser())) {
                    viewHolder.mLinearLayout_out.setVisibility(View.VISIBLE);
                    viewHolder.mLinearLayout_in.setVisibility(View.INVISIBLE);
                    viewHolder.mImageView_in.setVisibility(View.INVISIBLE);
                    viewHolder.mImageView_out.setVisibility(View.VISIBLE);
                    viewHolder.user.setText(model.getUser());
                    viewHolder.mUser_message.setText(model.getMessage());
                    viewHolder.mTimestamp.setText(sdf.format(new Date(model.getTimestampSentLong())));
                    if (!model.getPhotoUrl().equals("a"))
                        Picasso.with(GlobalChat.this).load(model.getPhotoUrl()).fit().into(viewHolder.mImageView_out);
                    else
                        Picasso.with(GlobalChat.this).load(R.drawable.ic_person_black_36dp)
                                .fit().into(viewHolder.mImageView_out);

                }else {
                    viewHolder.mLinearLayout_in.setVisibility(View.VISIBLE);
                    viewHolder.mLinearLayout_out.setVisibility(View.INVISIBLE);
                    viewHolder.mImageView_out.setVisibility(View.INVISIBLE);
                    viewHolder.mImageView_in.setVisibility(View.VISIBLE);
                    viewHolder.user_in.setText(model.getUser());
                    viewHolder.mUser_message_in.setText(model.getMessage());
                    viewHolder.mTimestamp_in.setText(sdf.format(new Date(model.getTimestampSentLong())));
                    if (!model.getPhotoUrl().equals("a"))
                        Picasso.with(GlobalChat.this).load(model.getPhotoUrl()).fit().into(viewHolder.mImageView_in);
                    else
                        Picasso.with(GlobalChat.this).load(R.drawable.ic_person_black_36dp)
                                .fit().into(viewHolder.mImageView_in);

                }

            }
        };


        mFirebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int friendlyMessageCount = mFirebaseAdapter.getItemCount();
                int lastVisiblePosition = mLayoutManager.findLastCompletelyVisibleItemPosition();
                // If the recycler view is initially being loaded or the user is at the bottom of the list, scroll
                // to the bottom of the list to show the newly added message.
                if (lastVisiblePosition == -1 ||
                        (positionStart >= (friendlyMessageCount - 1) && lastVisiblePosition == (positionStart - 1))) {
                    mRecyclerView.scrollToPosition(positionStart);
                }
            }
        });

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mFirebaseAdapter);

        mSend_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            mFirebaseAuth.signOut();
            if (Constants.FLAG){
                Auth.GoogleSignInApi.signOut(mGoogleApiClient);
            }
            Intent intent = new Intent(GlobalChat.this,Login.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            photoUrl = null;
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void sendMessage() {

        String message = mEditText.getText().toString().replaceAll("\\\\n","");

        if (!message.trim().equals("")){
            mEditText.setText("");
            Messages messages = new Messages(Constants.USERNAME,message.replace("\n","").replace("\r",""),photoUrl);
            Toast.makeText(GlobalChat.this, messages.getMessage(), Toast.LENGTH_SHORT).show();
            mReference.child(Constants.MESSAGES).push().setValue(messages);
        }else {
            mEditText.setError("Empty message");
        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private static class MessageViewHolder extends RecyclerView.ViewHolder {

        public TextView user,user_in;
        public TextView mUser_message,mUser_message_in;
        public TextView mTimestamp,mTimestamp_in;
        public LinearLayout mLinearLayout_out,mLinearLayout_in;
        public CircleImageView mImageView_in,mImageView_out;

        public MessageViewHolder(View itemView) {
            super(itemView);
            user = (TextView) itemView.findViewById(R.id.sent_user_out);
            mUser_message = (TextView) itemView.findViewById(R.id.users_message_out);
            mTimestamp = (TextView) itemView.findViewById(R.id.message_timestamp_out);
            mLinearLayout_out = (LinearLayout) itemView.findViewById(R.id.chat_layout_out);
            mImageView_out = (CircleImageView)itemView.findViewById(R.id.profile_image_out);
            user_in = (TextView) itemView.findViewById(R.id.sent_user_incoming);
            mUser_message_in = (TextView) itemView.findViewById(R.id.users_message_incoming);
            mTimestamp_in = (TextView) itemView.findViewById(R.id.message_timestamp_incoming);
            mLinearLayout_in = (LinearLayout) itemView.findViewById(R.id.chat_layout_incoming);
            mImageView_in = (CircleImageView)itemView.findViewById(R.id.profile_image_in);
        }
    }

}
