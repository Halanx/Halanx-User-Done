package com.halanx.tript.userapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.halanx.tript.userapp.Activities.MapsActivity;
import com.halanx.tript.userapp.R;

public class PaymentFragments extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_payment_fragments, container, false);
    }

    public void onPaymentOptionClick(View view) {
        startActivity(new Intent(getActivity(), MapsActivity.class));
    }

}
