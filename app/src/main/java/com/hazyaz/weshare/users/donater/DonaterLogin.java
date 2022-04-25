package com.hazyaz.weshare.users.donater;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hazyaz.weshare.R;
import com.hazyaz.weshare.users.admin.AdminHome;
import com.hazyaz.weshare.users.areaincharge.AIRegister;

import static android.content.ContentValues.TAG;

public class DonaterLogin extends AppCompatActivity {

    EditText username, email , pass;
    Button donaterlogin_button;
    Button donater_reg_button;

    @Nullable
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donater_login);

        username=findViewById(R.id.donater_username);
        pass=findViewById(R.id.donater_password);
        email=findViewById(R.id.donater_email);
        donaterlogin_button=findViewById(R.id.donater_loginBtn);
        donater_reg_button = findViewById(R.id.donater_register);

        donaterlogin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String iEmail, iUsername, iPassword;

                iEmail=username.getText().toString().trim();
                iPassword=pass.getText().toString().trim();
                iUsername=email.getText().toString().trim();

                if(iUsername.equals("")){
                    username.setError("Empty");
                    username.setFocusable(true);
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(iEmail).matches()){
                    email.setError("Invalided Email");
                    email.setFocusable(true);
                }
                else if(iPassword.length()<6){
                    pass.setError("Password length at least 6 characters");
                    pass.setFocusable(true);
                }
                DonaterLoginCred(iUsername,iEmail,iPassword);
            }
        });

donater_reg_button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
       startActivity(new Intent(getApplicationContext(),DonaterRegister.class));
    }
});



    }

   void DonaterLoginCred(String name, String email, String pass){

       ProgressDialog progressDialog
               = new ProgressDialog(this);
       progressDialog.setTitle("Logging In");
       progressDialog.setMessage("Please wait, Loggin in progress");
       progressDialog.show();

       FirebaseAuth mAuth = FirebaseAuth.getInstance();
       mAuth.createUserWithEmailAndPassword(email, pass)
               .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       if (task.isSuccessful()) {
                           progressDialog.dismiss();
                           // Sign in success, update UI with the signed-in user's information
                           Log.d(TAG, "signInWithEmail:success");
                           FirebaseUser user = mAuth.getCurrentUser();
                           startActivity(new Intent(getApplicationContext(), DonaterHome.class));
                       } else {
                           // If sign in fails, display a message to the user.
                           Log.w(TAG, "signInWithEmail:failure", task.getException());
                           Toast.makeText(getApplicationContext(),"Authentication failed."+task.getException(),
                                   Toast.LENGTH_SHORT).show();

                       }
                   }
               });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }



}
