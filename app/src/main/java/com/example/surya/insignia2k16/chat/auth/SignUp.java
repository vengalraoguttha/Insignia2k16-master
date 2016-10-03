package com.example.surya.insignia2k16.chat.auth;

import android.app.Dialog;
import android.content.Intent;
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

import com.example.surya.insignia2k16.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {

    private static final String TAG = "Sign up Activity" ;
    EditText mUsernameField,mEmailField,mPasswordField;
    String mUsername,mPassword,mEmail;
    Button sign_up_btn;
    TextView sign_in_label;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mUsernameField = (EditText)findViewById(R.id.sign_up_username);
        mEmailField = (EditText)findViewById(R.id.sign_up_email);
        mPasswordField = (EditText)findViewById(R.id.sign_up_password);
        sign_up_btn = (Button)findViewById(R.id.sign_up_btn);
        sign_in_label = (TextView)findViewById(R.id.sign_in_text);

        mAuth = FirebaseAuth.getInstance();

        sign_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });
        sign_in_label.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToLogin();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void navigateToLogin() {
        Intent intent = new Intent(SignUp.this,Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void createUser() {

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.authentication_dialog);
        dialog.setTitle("Loading...");
        dialog.show();

        mUsername = mUsernameField.getText().toString();
        mEmail = mEmailField.getText().toString();
        mPassword = mPasswordField.getText().toString();

        boolean isValidEmail = checkEmail(mEmail);
        boolean isValidUsername = checkUsername(mUsername);
        boolean isValidPass = checkPassword(mPassword);

        if (!isValidEmail || !isValidPass || !isValidUsername){
            Toast.makeText(SignUp.this, "Check the credentials", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
            return;
        }

        mAuth.createUserWithEmailAndPassword(mEmail, mPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.e(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                        dialog.dismiss();
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(SignUp.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }else {
//                            Constants.USERNAME = mUsername;
//                            Constants.EMAIL = mEmail;
//                            DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
//                            mRef.child(Constants.USERS).push().setValue(new Users(Constants.USERNAME,mEmail));
                            navigateToLogin();
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

    private boolean checkUsername(String username) {

        if (username == null){
            mUsernameField.setError("null");
            return false;
        }else {
            if (username.length()<5)
                mUsernameField.setError("Atleast 5 characters of length");
            else
                return true;
        }
        return false;
    }

    private boolean checkEmail(String email) {

        if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return true;
        } else {
            mEmailField.setError("Check Email");
            return false;
        }
    }
}