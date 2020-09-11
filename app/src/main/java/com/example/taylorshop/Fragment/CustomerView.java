package com.example.taylorshop.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.taylorshop.MainActivity;
import com.example.taylorshop.R;

public class CustomerView extends Fragment {
    TextView txtser, txtname, txtmble;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_customer_view, container, false);

        txtser = view.findViewById(R.id.customer_view_serial);
        txtname = view.findViewById(R.id.customer_view_name);
        txtmble = view.findViewById(R.id.customer_view_Mobile);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("customer_view", Context.MODE_PRIVATE);
        txtser.setText(sharedPreferences.getString("serial", null));
        txtname.setText(sharedPreferences.getString("name", null));
        txtmble.setText(sharedPreferences.getString("mobile", null));
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Taylor Shop");
        getActivity().findViewById(R.id.action_search).setVisibility(View.VISIBLE);
        getActivity().findViewById(R.id.fab).setVisibility(View.VISIBLE);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Customer Info");
        getActivity().findViewById(R.id.action_search).setVisibility(View.GONE);
        getActivity().findViewById(R.id.fab).setVisibility(View.GONE);
    }
}
