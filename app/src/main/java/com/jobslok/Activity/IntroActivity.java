package com.jobslok.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.*;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.jobslok.R;

import java.lang.*;

public class IntroActivity extends AppCompatActivity {
    ImageView logo;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        logo = findViewById(R.id.logo);
        animation = AnimationUtils.loadAnimation(this, R.anim.main_animation);
        logo.startAnimation(animation);
        logo.setVisibility(View.VISIBLE);
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                        startActivity(new Intent(IntroActivity.this, RegistrationActivity.class));
                        finish();
                    }else{
                        startActivity(new Intent(IntroActivity.this, MainActivity.class));
                        finish();
                    }
                }
            }
        };
        thread.start();
    }
}
