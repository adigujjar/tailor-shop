package com.example.taylorshop

import android.annotation.SuppressLint
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taylorshop.Adapter.CustomAdapter
import com.example.taylorshop.Fragment.AddNewCustomer
import com.example.taylorshop.Models.Customer
import com.example.taylorshop.data.AppDatabase
import com.example.taylorshop.interfaces.PopupCallback
import com.example.taylorshop.utilities.CustomerVM
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import com.orhanobut.hawk.Hawk

class MainActivity : AppCompatActivity(), PopupCallback {
    lateinit var databaseReference: DatabaseReference
    var arrayList = ArrayList<Customer?>()
    private lateinit var floatingActionButton: FloatingActionButton
    private lateinit var recyclerView: RecyclerView
    private var mAdapter: CustomAdapter? = null
    private var popupCallback: PopupCallback? = null
    private lateinit var customerVM: CustomerVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Hawk.init(this).build()
        popupCallback = this
        floatingActionButton = findViewById(R.id.fab)
        customerVM = ViewModelProvider(this)[CustomerVM::class.java]
        recyclerView = findViewById(R.id.recycler_view)
        floatingActionButton.setOnClickListener(View.OnClickListener { addCustomer() })

        recyclerView.layoutManager = LinearLayoutManager(this)
        databaseReference = FirebaseDatabase.getInstance().reference.child("Contact")
        if (!Hawk.contains("Fetched_from_firebase")) {
            databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    arrayList.clear()
                    for (postsnapshot in dataSnapshot.children) {
                        Hawk.put("Fetched_from_firebase", true)
                        val customer = postsnapshot.getValue(Customer::class.java)
                        customer!!.key = postsnapshot.key
                        arrayList.add(customer)
                        Hawk.put("userID", customer.id)
                        AppDatabase.getInstance(applicationContext).customerDao().insertCustomer(customer)
                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {}
            })
        }

        if (isOnline) customerVM.deleteAfterOnline(databaseReference)

            customerVM.customerList.observe(this) {
                if (!it.isNullOrEmpty()) {
                    arrayList.clear()
                    customerVM.checkInternetConnectivity(isOnline,this, databaseReference, it)
                    arrayList.addAll(it as ArrayList<Customer>)
                    mAdapter = CustomAdapter(arrayList, applicationContext, popupCallback)
                    recyclerView.adapter = mAdapter
                } else {
                    customerVM.checkInternetConnectivity(isOnline,this, databaseReference, it)
                }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val menuItem = menu.findItem(R.id.action_search)
        val searchView = menuItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                filter(s)
                return true
            }
        })
        return true
    }

    fun filter(text: String) {
        var text = text
        val srchList = ArrayList<Customer?>()
        if (text.isEmpty()) {
            srchList.addAll(arrayList)
            Log.e("Customer", arrayList.size.toString())
        } else {
            text = text.toLowerCase()
            for (item in arrayList) {
                if (item!!.name!!.toLowerCase().contains(text) || item.phone_number!!.toLowerCase()
                        .contains(text)
                ) {
                    srchList.add(item)
                }
            }
        }
        mAdapter = null
        mAdapter = CustomAdapter(srchList, applicationContext)
        recyclerView.adapter = mAdapter
    }

    private fun addCustomer() {
        val fragment: Fragment = AddNewCustomer()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame, fragment, "MyFragment")
        fragmentTransaction.addToBackStack("Customer")
        fragmentTransaction.commit()
    }

    private val isOnline: Boolean
        get() {
            val connMgr = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            @SuppressLint("MissingPermission") val networkInfo = connMgr.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
           }

    override fun onClick(position: Int) {
         AppDatabase.getInstance(this).customerDao().deleteCustomer(arrayList[position]!!)
        if(isOnline) {
            val sQuery = FirebaseDatabase.getInstance().reference.child("Contact").orderByChild("phone_number")
                    .equalTo(arrayList[position]?.phone_number)
            sQuery.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (snapshot in dataSnapshot.children) {
                        snapshot.ref.removeValue()
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })
        } else {
            if (Hawk.contains("delete_customer")) {
                val data = Hawk.get<HashMap<String, Customer>>("delete_customer")
                data[arrayList[position]?.phone_number.toString()] = arrayList[position]!!
                Hawk.put("delete_customer", data)
            } else {
                val deleteUsers = HashMap<String, Customer>()
                deleteUsers[arrayList[position]?.phone_number.toString()] = arrayList[position]!!
                Hawk.put("delete_customer" , deleteUsers)
            }
        }
        if (arrayList.size == 1 && position == 0) arrayList.clear() else arrayList.removeAt(position)
        mAdapter?.notifyItemRemoved(position)
    }
}