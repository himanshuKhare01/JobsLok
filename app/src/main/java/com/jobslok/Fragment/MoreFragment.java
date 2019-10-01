package com.jobslok.Fragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.jobslok.Activity.DashboardActivity;
import com.jobslok.Activity.HelpActivity;
import com.jobslok.Activity.NotificationActivity;
import com.jobslok.Activity.PaymentHistoryActivity;
import com.jobslok.Activity.PaymentMethodActivity;
import com.jobslok.Activity.ProfileActivity;
import com.jobslok.Activity.RegistrationActivity;
import com.jobslok.Activity.ReviewsActivity;
import com.jobslok.Activity.SettingsActivity;
import com.jobslok.Activity.SupportCenterActivity;
import com.jobslok.Activity.TaskAlertsActivity;
import com.jobslok.R;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoreFragment extends Fragment {


    public MoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_more, container, false);
        TextView dashboard=v.findViewById(R.id.dashboard);
        dashboard.setOnClickListener(view -> startActivity(DashboardActivity.class));

        TextView profile=v.findViewById(R.id.profile);
        profile.setOnClickListener(view -> startActivity(ProfileActivity.class));

        TextView paymentHistory=v.findViewById(R.id.payment_history);
        paymentHistory.setOnClickListener(view -> startActivity(PaymentHistoryActivity.class));

        TextView paymentMethod=v.findViewById(R.id.payment_method);
        paymentMethod.setOnClickListener(view -> startActivity(PaymentMethodActivity.class));

        TextView reviews=v.findViewById(R.id.reviews);
        reviews.setOnClickListener(view -> startActivity(ReviewsActivity.class));

        TextView noifications=v.findViewById(R.id.notification);
        noifications.setOnClickListener(view -> startActivity(NotificationActivity.class));

//        TextView taskalerts=v.findViewById(R.id.task_alerts);
//        taskalerts.setOnClickListener(view -> startActivity(TaskAlertsActivity.class));

//        TextView settings=v.findViewById(R.id.settings);
//        settings.setOnClickListener(view -> startActivity(SettingsActivity.class));

        TextView help=v.findViewById(R.id.help);
        help.setOnClickListener(view -> startActivity(SupportCenterActivity.class));

        TextView logout=v.findViewById(R.id.logout);
        logout.setOnClickListener(view -> {
            AlertDialog.Builder alertDialog=new AlertDialog.Builder(getActivity());
            alertDialog.setMessage("Want to logout from the device?");
            alertDialog.setPositiveButton("Yes", (dialogInterface, i) -> {
                FirebaseAuth.getInstance().signOut();
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .build();
                GoogleSignIn.getClient(Objects.requireNonNull(getActivity()), gso).signOut().addOnCompleteListener(task -> {
                    startActivity(new Intent(getActivity(), RegistrationActivity.class));
                    getActivity().finish();
                });
            }).setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss()).create().show();
        });
        return v;
    }
    private void startActivity(Class activity){
        startActivity(new Intent(getActivity(),activity));
    }

}
