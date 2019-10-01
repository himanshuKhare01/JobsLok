package com.jobslok.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.jobslok.Fragment.BrowseFragment;
import com.jobslok.Fragment.MessageFragment;
import com.jobslok.Fragment.MoreFragment;
import com.jobslok.Fragment.MyTaskFragment;
import com.jobslok.Fragment.PostTaskFragment;
import com.jobslok.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       //FirebaseSignOut
       //signout();
        loadFragment(new PostTaskFragment());



        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.menu_post:
                        fragment = new PostTaskFragment();
                        loadFragment(fragment);
                        break;
                    case R.id.menu_task:
                        fragment = new MyTaskFragment();
                        loadFragment(fragment);
                        break;
                    case R.id.menu_browse:
                        fragment = new BrowseFragment();
                        loadFragment(fragment);
                        break;
                    case R.id.menu_messages:
                        fragment = new MessageFragment();
                        loadFragment(fragment);
                        break;
                    case R.id.menu_more:
                        fragment = new MoreFragment();
                        loadFragment(fragment);
                        break;
                }
                return true;
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.flContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void signout() {
        FirebaseAuth.getInstance().signOut();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        // Google sign out
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }

}
