package com.hazyaz.weshare.users.deliveryperson;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hazyaz.weshare.GetLocation;
import com.hazyaz.weshare.MainActivity;
import com.hazyaz.weshare.R;
import com.hazyaz.weshare.users.areaincharge.AIHome;
import com.hazyaz.weshare.users.areaincharge.AILogin;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class DPHome extends AppCompatActivity {
    FirebaseAuth mAuth;
    private Button mLocationReq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dp_home);



        mAuth = FirebaseAuth.getInstance();
        mLocationReq = findViewById(R.id.reqLocation);



    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser==null){
            startActivity(new Intent(DPHome.this, DPLogin.class));
        }
    }







}