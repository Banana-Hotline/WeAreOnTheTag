package com.example.android.weareonthetag.Fragments;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.weareonthetag.R;

/**
     * A placeholder fragment containing a simple view.
     */
    public class ConnectTaggerFragment extends Fragment {

        public ConnectTaggerFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_connect_to_tagger, container, false);
            TextView outputTxtView = (TextView)rootView.findViewById(R.id.outputTxtView);
            Button connectDevicesBtn = (Button) rootView.findViewById(R.id.connectDevicesBtn);

            return rootView;
        }
    }