package com.example.taylorshop.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.taylorshop.MainActivity;
import com.example.taylorshop.Models.Customer;
import com.example.taylorshop.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddNewCustomer extends Fragment {

    EditText name_customer_text, serial_num_customer_text, mobile_customer_text;
    DatabaseReference databaseReference;
    Button add_new;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_new_customer, container, false);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Contact");

        name_customer_text = view.findViewById(R.id.Name_of_customer);
        serial_num_customer_text = view.findViewById(R.id.Serial_num_of_customer);
        mobile_customer_text = view.findViewById(R.id.mobile_of_customer);


        add_new = view.findViewById(R.id.Add_customer_data);

            add_new.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!isOnline()){
                        Toast.makeText(getContext(),"No Internet Connection!", Toast.LENGTH_LONG).show();
                    } else if (TextUtils.isEmpty(name_customer_text.getText().toString())){
                        name_customer_text.setError("Required");
                    }else if (TextUtils.isEmpty(serial_num_customer_text.getText().toString()) || serial_num_customer_text.getText().toString().length() > 5 ||
                            serial_num_customer_text.getText().toString().matches(".*[a-zA-Z]+.*")) {
                        serial_num_customer_text.setError("Required and must be less then 5 digit and only use numbers");
                    }else if(TextUtils.isEmpty(mobile_customer_text.getText().toString()) || mobile_customer_text.getText().toString().length() <= 8 ||
                            mobile_customer_text.getText().toString().length() >= 15 || mobile_customer_text.getText().toString().matches(".*[a-zA-Z]+.*") ){
                        mobile_customer_text.setError("Required must be only numbers greater than 8 and less then 15");
                    }
                    else {
                        writeNewUser(name_customer_text.getText().toString(), serial_num_customer_text.getText().toString(), mobile_customer_text.getText().toString());
                    }
                }
            });
        return view;
    }
    public void writeNewUser(String name, String serial, String mobile)
    {
        progressDialog = new ProgressDialog(getContext(),R.style.Custom);
        //progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        Customer customer = new Customer(serial, name, mobile, "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0","");
        databaseReference.push().setValue(customer).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    progressDialog.dismiss();
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if(imm.isAcceptingText()) { // verify if the soft keyboard is open
                        imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                    }
//                    FragmentManager fm = getActivity().getSupportFragmentManager();
//                    if(fm.getBackStackEntryCount()>0) {
//                        fm.popBackStack();
//                    }
                    getActivity().finish();
                    startActivity(new Intent(getContext(), MainActivity.class));
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)getActivity().
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Customer");
        getActivity().findViewById(R.id.action_search).setVisibility(View.GONE);
        getActivity().findViewById(R.id.fab).setVisibility(View.GONE);


    }

    @Override
    public void onDetach() {
        super.onDetach();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Taylor Shop");
        getActivity().findViewById(R.id.action_search).setVisibility(View.VISIBLE);
        getActivity().findViewById(R.id.fab).setVisibility(View.VISIBLE);


    }
}
