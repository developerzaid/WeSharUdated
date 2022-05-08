package com.hazyaz.weshare.users.areaincharge;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hazyaz.weshare.R;

import static android.content.ContentValues.TAG;

public class AILogin extends AppCompatActivity {

    EditText ai_username, ai_password, ai_email;
    Button loginBtn;
    FirebaseAuth mAuth;
    Button aiRegisterButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ai_login);


        ai_username = findViewById(R.id.ai_username);
        ai_password = findViewById(R.id.ai_password);
        ai_email = findViewById(R.id.ai_email);
        loginBtn = findViewById(R.id.ai_loginBtn);
        mAuth = FirebaseAuth.getInstance();
        aiRegisterButton = findViewById(R.id.ai_register);


        aiRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AILogin.this, AIRegister.class));
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //handle login
                //admin info
                String email, username, password;
                email = ai_email.getText().toString().trim();
                password = ai_password.getText().toString().trim();
                username = ai_username.getText().toString().trim();
                if (username.equals("")) {
                    ai_username.setError("Empty");
                    ai_username.setFocusable(true);
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    ai_email.setError("Invalided Email");
                    ai_email.setFocusable(true);
                } else if (password.length() < 6) {
                    ai_password.setError("Password length at least 6 characters");
                    ai_password.setFocusable(true);
                } else {

                    SharedPreferences.Editor editor;
                    editor = PreferenceManager.getDefaultSharedPreferences(AILogin.this).edit();
                    editor.putString("admin_username", email.trim());
                    editor.putString("admin_password", password.trim());
                    editor.apply();
                    LoginAreaIncharge(username, email, password);

                }
            }
        });
    }

    void LoginAreaIncharge(String username, String email, String password) {
        ProgressDialog progressDialog
                = new ProgressDialog(this);
        progressDialog.setTitle("Loggin User in");
        progressDialog.setMessage("Please wait, loggin in progress");
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            new Intent(AILogin.this, AIHome.class);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("hashdashdashdahsd", "signInWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }


}
