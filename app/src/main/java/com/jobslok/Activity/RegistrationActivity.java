package com.jobslok.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.service.autofill.RegexValidator;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jobslok.R;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class RegistrationActivity extends AppCompatActivity {
    VideoView videoView;
    String TAG = "RegisterActivity";
    FirebaseAuth mAuth;
    private static final int RC_SIGN_IN = 9001;
    private CollectionReference userCollectionReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registration);
        if (!Places.isInitialized()) {
            Places.initialize(this, "AIzaSyCYQQC1px4F3XJG0N_Me5mImgqG8iSnCaU");
        }
        videoView = findViewById(R.id.videoview);
        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.google_signin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googleSignin();
            }
        });
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.introduction_video);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        android.widget.RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) videoView.getLayoutParams();
        params.width = metrics.widthPixels;
        params.height = metrics.heightPixels;
        params.leftMargin = 0;
        videoView.setVideoURI(uri);
        videoView.setLayoutParams(params);

        videoView.start();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
            }
        });
    }

    private void googleSignin() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                assert account != null;
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(RegistrationActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(RegistrationActivity.this, permission)) {
                ActivityCompat.requestPermissions(RegistrationActivity.this, new String[]{permission}, requestCode);
            } else {
                ActivityCompat.requestPermissions(RegistrationActivity.this, new String[]{permission}, requestCode);
            }
        } else {
            Toast.makeText(this, "" + permission + " is already granted.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ActivityCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED) {
            PlacesClient placesClient = Places.createClient(RegistrationActivity.this);
            List<Place.Field> placeFields = Collections.singletonList(Place.Field.ADDRESS);
            FindCurrentPlaceRequest request = FindCurrentPlaceRequest.newInstance(placeFields);
            if (ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Task<FindCurrentPlaceResponse> placeResponse = placesClient.findCurrentPlace(request);
                placeResponse.addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        FindCurrentPlaceResponse response = task.getResult();
                         String add= response.getPlaceLikelihoods().get(1).getPlace().getAddress();
                        Toast.makeText(RegistrationActivity.this, "address "+add, Toast.LENGTH_SHORT).show();
                        ProgressDialog dialog= new ProgressDialog(RegistrationActivity.this);
                        dialog.setMessage("Please Wait...");
                        dialog.create();
                        dialog.show();
                        Log.i("Register", "address" + add);
                        storeUserInfo(add);
                    } else {
                        Exception exception = task.getException();
                        Toast.makeText(this, "Exception occur", Toast.LENGTH_SHORT).show();
                        if (exception instanceof ApiException) {
                            ApiException apiException = (ApiException) exception;
                            Log.e(TAG, "Place not found: " + apiException.getStatusCode());
                        }
                    }
                });
            } else {
                Toast.makeText(this, "Not permitted", Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @SuppressLint("NewApi")
                    @Override
                    public void onComplete(@NonNull final Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if (Objects.requireNonNull(task.getResult()).getAdditionalUserInfo().isNewUser()) {
                                askForPermission(ACCESS_FINE_LOCATION,1);
                            } else {
                                startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                                finish();
                            }
                        } else {
                            Toast.makeText(RegistrationActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                        }
                    }
                });
    }

    private void storeUserInfo(String address) {
        mAuth=FirebaseAuth.getInstance();
        userCollectionReference = FirebaseFirestore.getInstance().collection("users");
        Map<String, Object> user = new HashMap<>();
        user.put("name", Objects.requireNonNull(mAuth.getCurrentUser()).getDisplayName());
        user.put("email", mAuth.getCurrentUser().getEmail());
        user.put("phone", "" + mAuth.getCurrentUser().getPhoneNumber());
        user.put("image", Objects.requireNonNull(mAuth.getCurrentUser().getPhotoUrl()).toString());
        user.put("address", address);
        user.put("rating","0");
        user.put("taskPosted","0");
        user.put("taskCompleted","0");
        user.put("ratedBy","0");
        userCollectionReference.document(mAuth.getCurrentUser().getUid()).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                 startActivity(new Intent(RegistrationActivity.this, GuideActivity.class));
                  finish();
               }else
                    Toast.makeText(RegistrationActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegistrationActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
