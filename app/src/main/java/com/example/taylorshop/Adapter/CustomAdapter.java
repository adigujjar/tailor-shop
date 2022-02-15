package com.example.taylorshop.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taylorshop.Fragment.CustomerView;
import com.example.taylorshop.MainActivity;
import com.example.taylorshop.Models.Customer;
import com.example.taylorshop.R;
import com.example.taylorshop.interfaces.PopupCallback;
import com.example.taylorshop.ui.PopUp;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    public Context context;
    @NonNull
   ArrayList<Customer> customerArrayList = new ArrayList<Customer>();
    private PopupCallback popupCallback;


    public CustomAdapter(@NonNull ArrayList<Customer> customerArrayList, Context context,  PopupCallback popupCallback) {
        this.customerArrayList = customerArrayList;
        this.context = context;
        this.popupCallback = popupCallback;
    }

    public CustomAdapter(ArrayList<Customer> srchList, Context applicationContext) {
        this.customerArrayList = srchList;
        this.context = applicationContext;
    }

    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_row, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder,  int position) {

        holder.Name_customer.setText(customerArrayList.get(position).getName());
        holder.serial_num_customer.setText(customerArrayList.get(position).getSerial_number());
        holder.mobile_customer.setText(customerArrayList.get(position).getPhone_number());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Hawk.put("customer", customerArrayList.get(position));
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment fragment = new CustomerView();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment).addToBackStack("customerview").commit();
                Log.e("check", customerArrayList.get(position).getName());
            }
        });
        holder.layout.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                PopUp.getInstance().myDialog(v.getContext(), popupCallback, "Delete entry", "Are you sure you want to delete this entry?", position);
                return false;
            }
        });
    }
    @Override
    public int getItemCount() {
        return customerArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView Name_customer, mobile_customer, serial_num_customer;
        ConstraintLayout layout;
        public ViewHolder(View view)
        {
            super(view);
            Name_customer = view.findViewById(R.id.customer_name);
            mobile_customer = view.findViewById(R.id.customer_mobile);
            serial_num_customer = view.findViewById(R.id.serial_number);
            layout = view.findViewById(R.id.parentlay);
        }
    }

    public void setData(ArrayList<Customer> user){
        customerArrayList =user;
        notifyDataSetChanged();

    }

}

