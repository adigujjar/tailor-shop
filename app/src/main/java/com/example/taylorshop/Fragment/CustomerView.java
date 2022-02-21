package com.example.taylorshop.Fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.taylorshop.MainActivity;
import com.example.taylorshop.Models.Customer;
import com.example.taylorshop.R;
import com.example.taylorshop.data.AppDatabase;
import com.example.taylorshop.interfaces.PopupCallback;
import com.example.taylorshop.ui.PopUp;
import com.orhanobut.hawk.Hawk;

import java.util.HashMap;

import dagger.hilt.android.AndroidEntryPoint;


public class CustomerView extends Fragment implements View.OnClickListener, PopupCallback {
    TextView txtser;
    EditText suitLength, suitArms, suitShoulders, suitModa, suitChest, suitBack, suitChestLoose, txtname,
            suitBackLoose, trouserLength, trouserPhncha, suitExtraNotes, suitNeck, suitCuff, suitdaman, txtmble;

    Button btnSaveInfo;
    Customer customer;
    CheckBox pocketOne,pocketTwo,pocketThree,pocketFour, colorCheck, banCheck, golKeraCheck, seedhaKeraCheck, cbFP, cbSLP, cbSRP, cbTP;

    HorizontalScrollView pocketsView;
    PopupCallback popupCallback;
    private boolean fp;
    private boolean tp;
    private boolean rp;
    private boolean lp;

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

        pocketOne = view.findViewById(R.id.checkbox1);
        pocketTwo = view.findViewById(R.id.checkbox2);
        pocketThree = view.findViewById(R.id.checkbox3);
        pocketFour = view.findViewById(R.id.checkbox4);

        pocketsView = view.findViewById(R.id.allPockets);
        cbFP = view.findViewById(R.id.cBFPocket);
        cbSLP = view.findViewById(R.id.cbSLP);
        cbSRP = view.findViewById(R.id.cbSRP);
        cbTP = view.findViewById(R.id.cbTP);

        colorCheck = view.findViewById(R.id.checkboxColor);
        banCheck = view.findViewById(R.id.checkboxBan);
        golKeraCheck = view.findViewById(R.id.checkboxCircular);
        seedhaKeraCheck = view.findViewById(R.id.checkboxStraight);

        customer = Hawk.get("customer");
        txtser.setText(customer.getSerial_number());
        txtname.setText(customer.getName());
        txtmble.setText(customer.getPhone_number());
        suitLength.setText(customer.getLengthSuit());
        suitArms.setText(customer.getArmsSuit());
        suitShoulders.setText(customer.getShoulderSuit());
        suitModa.setText(customer.getSuitModa());
        if (customer.getSuitPocket().equals("1")) {
            pocketOne.setChecked(true);
        } else if (customer.getSuitPocket().equals("2")) {
            pocketTwo.setChecked(true);
        } else if (customer.getSuitPocket().equals("3")) {
            pocketThree.setChecked(true);
        } else if(customer.getSuitPocket().equals("4")){
            pocketFour.setChecked(true);
        }

        if (customer.getColorOrBanDesign().equals("1")) {
            colorCheck.setChecked(true);
        } else {
            banCheck.setChecked(true);
        }

        if (pocketOne.isChecked() || pocketTwo.isChecked() || pocketThree.isChecked() || pocketFour.isChecked()) {
            pocketsView.setVisibility(View.VISIBLE);
        }

        if (customer.getKeraDesign().equals("1")) {
            seedhaKeraCheck.setChecked(true);
        } else {
            golKeraCheck.setChecked(true);
        }

        suitChest.setText(customer.getChestSuit());
        suitBack.setText(customer.getBackSuit());
        suitChestLoose.setText(customer.getChestLoose());
        suitBackLoose.setText(customer.getBackLoose());
        trouserLength.setText(customer.getTrouserLength());
        trouserPhncha.setText(customer.getTrouserEdge());
        suitExtraNotes.setText(customer.getSuitExtraNotes());
        suitNeck.setText(customer.getNeckSuit());
        suitdaman.setText(customer.getSuitFront());
        suitCuff.setText(customer.getSuitCuff());

        cbFP.setChecked(customer.getPkF());
        cbSRP.setChecked(customer.getPkR());
        cbSLP.setChecked(customer.getPkL());
        cbTP.setChecked(customer.getPkT());
        fp = customer.getPkF();
        rp = customer.getPkR();
        lp = customer.getPkL();
        tp = customer.getPkT();

        btnSaveInfo.setOnClickListener(this);
        pocketOne.setOnClickListener(this);
        pocketTwo.setOnClickListener(this);
        pocketThree.setOnClickListener(this);
        pocketFour.setOnClickListener(this);
        colorCheck.setOnClickListener(this);
        banCheck.setOnClickListener(this);
        golKeraCheck.setOnClickListener(this);
        seedhaKeraCheck.setOnClickListener(this);
        cbSLP.setOnClickListener(this);
        cbFP.setOnClickListener(this);
        cbSRP.setOnClickListener(this);
        cbTP.setOnClickListener(this);

        cbTP.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                tp = b;
            }
        });
        cbSLP.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                lp = b;
            }
        });

        cbSRP.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                rp = b;
            }
        });

        cbFP.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                fp = b;
            }
        });

        return view;
    }

    private void resetPockets() {
        pocketsView.setVisibility(View.VISIBLE);
        pocketOne.setChecked(false);
        pocketTwo.setChecked(false);
        pocketThree.setChecked(false);
        pocketFour.setChecked(false);
    }

    private void resetBanOrColorDesign() {
        banCheck.setChecked(false);
        colorCheck.setChecked(false);
    }

    private void resetFrontKeraDesign() {
        seedhaKeraCheck.setChecked(false);
        golKeraCheck.setChecked(false);
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
        popupCallback = this;
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Customer Info");
        getActivity().findViewById(R.id.action_search).setVisibility(View.GONE);
        getActivity().findViewById(R.id.fab).setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        if (v == pocketOne) {
            resetPockets();
            pocketOne.setChecked(true);
        } else if (v == pocketTwo) {
            resetPockets();
            pocketTwo.setChecked(true);
        } else if (v == pocketThree) {
            resetPockets();
            pocketThree.setChecked(true);
        } else if (v == pocketFour){
            resetPockets();
            pocketFour.setChecked(true);
        }

        if (v == cbTP) {
            if (cbTP.isChecked()) cbTP.setChecked(true); else cbTP.setChecked(false);
        } else if (v == cbSLP) {
            if (cbSLP.isChecked()) cbSLP.setChecked(true); else cbSLP.setChecked(false);
        } else if(v == cbSRP) {
            if (cbSRP.isChecked())cbSRP.setChecked(true); else cbSRP.setChecked(false);
        } else if (v == cbFP){
            if (cbFP.isChecked())cbFP.setChecked(true); else cbFP.setChecked(false);
        }

        if (v == colorCheck) {
            resetBanOrColorDesign();
            colorCheck.setChecked(true);
        } else if (v == banCheck){
            resetBanOrColorDesign();
            banCheck.setChecked(true);
        }

        if (v == seedhaKeraCheck) {
            resetFrontKeraDesign();
            seedhaKeraCheck.setChecked(true);
        } else if (v == golKeraCheck) {
            resetFrontKeraDesign();
            golKeraCheck.setChecked(true);
        }

        String colorOrBanDesign = "";
        if (colorCheck.isChecked()) {
            colorOrBanDesign = "1";
        } else if (banCheck.isChecked()){
            colorOrBanDesign = "2";
        }

        String keraDesign = "";
        if (seedhaKeraCheck.isChecked()) {
            keraDesign = "1";
        } else if (golKeraCheck.isChecked()){
            keraDesign = "2";
        }

        if (v == btnSaveInfo) {
            String pockets = "";
            if (pocketOne.isChecked()) {
                pockets = "1";

            } else if (pocketTwo.isChecked()) {
                pockets = "2";
            } else if (pocketThree.isChecked()) {
                pockets = "3";
            } else {
                pockets = "4";
            }

            customer = new Customer(customer.getId(),
                    txtser.getText().toString(), txtname.getText().toString(),fp,rp,lp,tp,
                    txtmble.getText().toString(), suitLength.getText().toString(), suitShoulders.getText().toString(), suitArms.getText().toString(),
                    suitChest.getText().toString(), suitBack.getText().toString(), suitNeck.getText().toString(), suitChestLoose.getText().toString(), suitBackLoose.getText().toString(), suitdaman.getText().toString(),
                    trouserLength.getText().toString(), trouserPhncha.getText().toString(), pockets, suitModa.getText().toString(), suitExtraNotes.getText().toString(), suitCuff.getText().toString() ,customer.getKey(),
                    colorOrBanDesign, keraDesign);

            PopUp.getInstance().myDialog(requireContext(), popupCallback, "Are you sure you want to update..", "This customer data will update by Anmol Tailor", -1);
        }
    }

    @Override
    public void onClick(int position) {
        if (isOnline()) {
            HashMap<String, Object> childUpdates = new HashMap<String, Object>();
            childUpdates.put(String.valueOf(customer.getId()), customer);
            ((MainActivity) requireActivity()).databaseReference.updateChildren(childUpdates);
            AppDatabase.Companion.getInstance(requireContext()).customerDao().updateCustomer(
                    customer.getBackLoose(),
                    customer.getArmsSuit(),
                    customer.getChestLoose(),
                    customer.getBackSuit(),
                    customer.getColorOrBanDesign(),
                    customer.getKeraDesign(),
                    customer.getLengthSuit(),
                    customer.getName(),
                    customer.getNeckSuit(),
                    customer.getPhone_number(),
                    customer.getSerial_number(),
                    customer.getShoulderSuit(),
                    customer.getSuitCuff(),
                    customer.getSuitFront(),
                    customer.getSuitModa(),
                    customer.getTrouserEdge(),
                    customer.getTrouserLength(),
                    customer.getSuitPocket(),
                    customer.getPkF(),
                    customer.getPkL(),
                    customer.getPkR(),
                    customer.getPkT()
            );
        } else {
            if (Hawk.contains("update_customer")) {
                HashMap<String, Customer> data = Hawk.get("update_customer");
                data.put(customer.getPhone_number(), customer);
                        Hawk.put("update_customer", data);
            } else {
                HashMap<String, Customer> updateUsers = new HashMap<String, Customer>();
                updateUsers.put(customer.getPhone_number(), customer);
                Hawk.put("update_customer", updateUsers);
            }
            AppDatabase.Companion.getInstance(requireContext()).customerDao().updateCustomer(
                    customer.getBackLoose(),
                    customer.getArmsSuit(),
                    customer.getChestLoose(),
                    customer.getBackSuit(),
                    customer.getColorOrBanDesign(),
                    customer.getKeraDesign(),
                    customer.getLengthSuit(),
                    customer.getName(),
                    customer.getNeckSuit(),
                    customer.getPhone_number(),
                    customer.getSerial_number(),
                    customer.getShoulderSuit(),
                    customer.getSuitCuff(),
                    customer.getSuitFront(),
                    customer.getSuitModa(),
                    customer.getTrouserEdge(),
                    customer.getTrouserLength(),
                    customer.getSuitPocket(),
                    customer.getPkF(),
                    customer.getPkL(),
                    customer.getPkR(),
                    customer.getPkT()
            );
        }
    }

    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)getActivity().
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}
