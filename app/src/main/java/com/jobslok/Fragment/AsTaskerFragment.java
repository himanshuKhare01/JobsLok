package com.jobslok.Fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jobslok.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AsTaskerFragment extends Fragment {


    public AsTaskerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_astasker, container, false);
    }

}
