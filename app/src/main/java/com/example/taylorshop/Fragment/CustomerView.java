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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taylorshop.MainActivity;
import com.example.taylorshop.Models.Customer;
import com.example.taylorshop.R;
import com.orhanobut.hawk.Hawk;

import java.util.HashMap;
import java.util.Objects;

public class CustomerView extends Fragment implements View.OnClickListener {
    TextView txtser, txtname, txtmble;
    EditText suitLength, suitArms, suitShoulders, suitModa,
            suitPocket, suitChest, suitBack, suitChestLoose,
            suitBackLoose, trouserLength, trouserPhncha, suitExtraNotes,
            suitNeck, suitCuff, suitdaman;

    Button btnSaveInfo;
    Customer customer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_customer_view, container, false);

        txtser = view.findViewById(R.id.customer_view_serial);
        txtname = view.findViewById(R.id.customer_view_name);
        txtmble = view.findViewById(R.id.customer_view_Mobile);
        suitLength = view.findViewById(R.id.suitLengthValue);
        suitArms = view.findViewById(R.id.armsValue);
        suitShoulders = view.findViewById(R.id.shoulderValue);
        suitModa = view.findViewById(R.id.modavalue);
        suitPocket = view.findViewById(R.id.pocketValue);
        suitChest = view.findViewById(R.id.chestValue);
        suitBack = view.findViewById(R.id.backValue);
        suitChestLoose = view.findViewById(R.id.chestLooseValue);
        suitBackLoose = view.findViewById(R.id.backLooseValue);
        trouserLength = view.findViewById(R.id.trouserLengthValue);
        trouserPhncha = view.findViewById(R.id.trouserPhnchaValue);
        suitExtraNotes = view.findViewById(R.id.notesValue);
        suitNeck = view.findViewById(R.id.neckValue);
        suitdaman = view.findViewById(R.id.frontValue);
        suitCuff = view.findViewById(R.id.cuffDesign);
        btnSaveInfo = view.findViewById(R.id.save);


        customer = Hawk.get("customer");
//        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("customer_view", Context.MODE_PRIVATE);
        txtser.setText(customer.getSerial_number());
        txtname.setText(customer.getName());
        txtmble.setText(customer.getPhone_number());
        suitLength.setText(customer.getLengthSuit());
        suitArms.setText(customer.getArmsSuit());
        suitShoulders.setText(customer.getShoulderSuit());
        suitModa.setText(customer.getSuitModa());
        suitPocket.setText(customer.getSuitPocket());
        suitChest.setText(customer.getChestSuit());
        suitBack.setText(customer.getBackSuit());
        suitChestLoose.setText(customer.getChestLoose());
        suitBackLoose.setText(customer.getBackLoose());
        trouserLength.setText(customer.getTrouserLength());
        trouserPhncha.setText(customer.getTrouserEdge());
        suitExtraNotes.setText(customer.getSuitExtraNotes());
        suitNeck.setText(customer.getNeckSuit());
        suitdaman.setText(customer.getSuitFront());

        btnSaveInfo.setOnClickListener(this);


        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Taylor Shop");
        getActivity().findViewById(R.id.action_search).setVisibility(View.VISIBLE);
        getActivity().findViewById(R.id.fab).setVisibility(View.VISIBLE);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Customer Info");
        getActivity().findViewById(R.id.action_search).setVisibility(View.GONE);
        getActivity().findViewById(R.id.fab).setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        if (v == btnSaveInfo) {
            customer = new Customer(
                    txtser.getText().toString(), txtname.getText().toString(), txtmble.getText().toString(), suitLength.getText().toString(), suitShoulders.getText().toString(), suitArms.getText().toString(),
                    suitChest.getText().toString(), suitBack.getText().toString(), suitNeck.getText().toString(), suitChestLoose.getText().toString(), suitBackLoose.getText().toString(), suitdaman.getText().toString(),
                    trouserLength.getText().toString(), trouserPhncha.getText().toString(), suitPocket.getText().toString(), suitModa.getText().toString(), suitExtraNotes.getText().toString(), customer.getKey()
            );


            HashMap<String, Object> childUpdates = new HashMap<String, Object>();
            childUpdates.put(customer.getKey(), customer);
            ((MainActivity) requireActivity()).databaseReference.updateChildren(childUpdates);
        }
    }
}
