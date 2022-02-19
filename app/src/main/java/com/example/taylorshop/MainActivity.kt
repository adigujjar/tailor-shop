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
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*
import com.orhanobut.hawk.Hawk

class MainActivity : AppCompatActivity(), PopupCallback {
    lateinit var databaseReference: DatabaseReference
    var arrayList = ArrayList<Customer?>()
    private lateinit var floatingActionButton: FloatingActionButton
    private lateinit var customAdapter: CustomAdapter
    private lateinit var recyclerView: RecyclerView
    private var mAdapter: RecyclerView.Adapter<*>? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var popupCallback: PopupCallback? = null
    private val position = -1
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

        //fetch data offline
//        if (FirebaseApp.getApps(this).isNotEmpty() && !isOnline) if (this::databaseReference.isInitialized) FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        databaseReference = FirebaseDatabase.getInstance().reference.child("Contact")
//        databaseReference.keepSynced(true)
//            customerVM.customerList.observe(this) {
//            }
//
        if (!Hawk.contains("Fetched_from_firebase")) {
            databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    arrayList.clear()
                    for (postsnapshot in dataSnapshot.children) {
                        Hawk.put("Fetched_from_firebase", true)
                        val customer = postsnapshot.getValue(Customer::class.java)
                        customer!!.key = postsnapshot.key
                        customer.name
                        customer.serial_number
                        customer.phone_number
                        arrayList.add(customer)
                        AppDatabase.getInstance(applicationContext).customerDao().insertCustomer(customer)
                    }
//                    mAdapter = CustomAdapter(arrayList, applicationContext, popupCallback)
//                    layoutManager = LinearLayoutManager(this@MainActivity)
//                    recyclerView.setLayoutManager(layoutManager)
//                    recyclerView.setAdapter(mAdapter)
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })
        }

            customerVM.customerList.observe(this) {
                if (!it.isNullOrEmpty()) {
                    customerVM.checkInternetConnectivity(isOnline,this, databaseReference, it)
                    mAdapter = CustomAdapter(arrayList, applicationContext, popupCallback)
                    val recyclerView = recyclerView
                    arrayList.addAll(it as ArrayList<Customer>)
                    (mAdapter as CustomAdapter).setData(it as ArrayList<Customer>)
                    recyclerView.adapter = mAdapter
                    recyclerView.layoutManager = LinearLayoutManager(this)
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
        recyclerView!!.adapter = mAdapter
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    fun addCustomer() {
        val fragment: Fragment = AddNewCustomer()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame, fragment, "MyFragment")
        fragmentTransaction.addToBackStack("Customer")
        fragmentTransaction.commit()
    }

    val isOnline: Boolean
        get() {
            val connMgr = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            @SuppressLint("MissingPermission") val networkInfo = connMgr.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }

    override fun onClick(position: Int) {
         AppDatabase.getInstance(this).customerDao().deleteCustomer(arrayList[position]!!)
        if (Hawk.contains("delete_customer")) {
            val data = Hawk.get<HashMap<String, Customer>>("delete_customer")
            data[arrayList[position]?.phone_number.toString()] = arrayList[position]!!
            Hawk.put("delete_customer", data)
        } else {
            val deleteUsers = HashMap<String, Customer>()
            deleteUsers[arrayList[position]?.phone_number.toString()] = arrayList[position]!!
            Hawk.put("delete_customer" , deleteUsers)
        }
        arrayList.removeAt(position)
        if (mAdapter != null) mAdapter!!.notifyDataSetChanged()
    }

}




