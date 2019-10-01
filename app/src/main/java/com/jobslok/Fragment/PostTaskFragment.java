package com.jobslok.Fragment;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jobslok.Activity.MainActivity;
import com.jobslok.Activity.PostTaskActivity;
import com.jobslok.R;
public class PostTaskFragment extends Fragment {

    CardView task_cleaning;

    public PostTaskFragment() {
        // Required empty public constructor
    }
/**/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=  inflater.inflate(R.layout.fragment_post_task, container, false);
        task_cleaning=view.findViewById(R.id.cleaning);

        task_cleaning.setOnClickListener(view1 -> startActivity(new Intent(getActivity(), PostTaskActivity.class).putExtra("type",getResources().getStringArray(R.array.task_type)[0])));
       return view;
    }

}
