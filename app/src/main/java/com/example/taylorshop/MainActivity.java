package com.example.taylorshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.example.taylorshop.Adapter.CustomAdapter;
import com.example.taylorshop.Fragment.AddNewCustomer;
import com.example.taylorshop.Models.Customer;
import com.example.taylorshop.interfaces.PopupCallback;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.hawk.Hawk;


import java.util.ArrayList;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class MainActivity extends AppCompatActivity implements PopupCallback {

    public ArrayList<Customer> arrayList = new ArrayList<Customer>();
    FloatingActionButton floatingActionButton;
    public DatabaseReference databaseReference;
    CustomAdapter customAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private PopupCallback popupCallback;
    private int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Hawk.init(this).build();
        popupCallback = this;
        floatingActionButton = findViewById(R.id.fab);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCustomer();
            }
        });

        //fetch data offline
        if (!FirebaseApp.getApps(this).isEmpty() && !isOnline())
            if (databaseReference != null)
                FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Contact");
        databaseReference.keepSynced(true);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot postsnapshot : dataSnapshot.getChildren()) {
                    Customer customer = postsnapshot.getValue(Customer.class);
                    customer.setKey(postsnapshot.getKey());
                    customer.getName();
                    customer.getSerial_number();
                    customer.getPhone_number();
                    arrayList.add(customer);
                }

                mAdapter = new CustomAdapter(arrayList, getApplicationContext(), popupCallback);
                layoutManager = new LinearLayoutManager(MainActivity.this);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filter(s);
                return true;
            }
        });

        return true;
    }

    public void filter(String text) {
        ArrayList<Customer> srchList = new ArrayList<Customer>();
        if (text.isEmpty()) {
            srchList.addAll(arrayList);
            Log.e("Customer", String.valueOf(arrayList.size()));
        } else {
            text = text.toLowerCase();
            for (Customer item : arrayList) {
                if (item.getName().toLowerCase().contains(text) || item.getPhone_number().toLowerCase().contains(text)) {
                    srchList.add(item);
                }
            }
        }
        mAdapter = null;
        mAdapter = new CustomAdapter(srchList, getApplicationContext());
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void addCustomer() {
        Fragment fragment = new AddNewCustomer();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment, "MyFragment");
        fragmentTransaction.addToBackStack("Customer");
        fragmentTransaction.commit();
    }

    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    @Override
    public void onClick(int position) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query sQuery = ref.child("Contact").orderByChild("phone_number").equalTo(arrayList.get(position).getPhone_number());
        sQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    snapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        arrayList.remove(position);
        arrayList.clear();
        if (customAdapter != null) customAdapter.notifyDataSetChanged();
    }
}
