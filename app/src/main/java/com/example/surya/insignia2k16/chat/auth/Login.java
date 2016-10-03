package com.example.surya.insignia2k16.chat.auth;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.surya.insignia2k16.Constants;
import com.example.surya.insignia2k16.R;
import com.example.surya.insignia2k16.events_main.MainActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class Login extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,View.OnClickListener{

    private static final String TAG = "Login Activity";
    private static final int RC_SIGN_IN = 9001;
    EditText mEmailField,mPasswordField;
    Button mSign_in_btn,mGoogle_btn;
    String mEmail,mPassword;
    TextView sign_up_label;
    FirebaseAuth mAuth;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView login_view = (TextView)findViewById(R.id.login_txt);
        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/bell_mt.ttf");
        login_view.setTypeface(typeface);

        mEmailField = (EditText)findViewById(R.id.login_email);
        mPasswordField = (EditText)findViewById(R.id.login_password);
        mSign_in_btn = (Button)findViewById(R.id.sign_in_btn);
        mGoogle_btn = (Button)findViewById(R.id.google_btn);
        sign_up_label = (TextView)findViewById(R.id.sign_up_text);

        mAuth = FirebaseAuth.getInstance();
        mSign_in_btn.setOnClickListener(this);
        mGoogle_btn.setOnClickListener(this);
        sign_up_label.setOnClickListener(this);

    }

    private void emailLogin() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.authentication_dialog);
        dialog.setTitle("Loading...");
        dialog.show();

        mEmail = mEmailField.getText().toString();
        mPassword = mPasswordField.getText().toString();

        boolean isValidEmail = checkEmail(mEmail);
        boolean isValidPass = checkPassword(mPassword);

        if (!isValidEmail || !isValidPass){
            Toast.makeText(Login.this, "Check the credentials", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
            return;
        }

        mAuth.signInWithEmailAndPassword(mEmail, mPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        dialog.dismiss();
                        Log.e(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail", task.getException());
                            Toast.makeText(Login.this, "Authentication failed " + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(Login.this, "Successful login", Toast.LENGTH_LONG).show();
                            navigateToMain();
                        }
                    }
                });

    }

    private boolean checkPassword(String password) {

        if (password.length()<8){
            mPasswordField.setError("password must be 8 characters");
            return false;
        }else {
            return true;
        }

    }

    private boolean checkEmail(String email) {

        if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return true;
        } else {
            mEmailField.setError("Check Email");
            return false;
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.sign_in_btn:
                emailLogin();
                break;
            case R.id.sign_up_text:
                Intent intent = new Intent(Login.this,SignUp.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            case R.id.google_btn:
                googleLogin();
                break;
        }

    }

    private void googleLogin() {
        // Configure Google Sign In
        Constants.FLAG = true;
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                Toast.makeText(Login.this, "Error in Signing In with Google", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.authentication_dialog);
        dialog.setTitle("Loading...");
        dialog.show();

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());
                        dialog.dismiss();
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(Login.this, "Success Login" + Constants.USERNAME, Toast.LENGTH_SHORT).show();
                            navigateToMain();
                        }
                    }
                });
    }

    private void navigateToMain() {
        SharedPreferences prefs = getSharedPreferences("UserPrefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("USERNAME",mEmail);
        editor.apply();

        Intent intent = new Intent(Login.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
