package com.example.taylorshop.utilities

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taylorshop.Adapter.CustomAdapter
import com.example.taylorshop.MainActivity
import com.example.taylorshop.Models.Customer
import com.example.taylorshop.data.AppDatabase
import com.google.firebase.database.*
import com.orhanobut.hawk.Hawk
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class CustomerVM(
        application: Application
) : AndroidViewModel(application) {
    var customerList: LiveData<List<Customer>>
    var repository: CustomerRepository

    init {
        val customerDao = AppDatabase.getInstance(application).customerDao()
        repository = CustomerRepository(customerDao)

        customerList = repository.getCustomerList()
    }

    fun checkInternetConnectivity(
            isOnline: Boolean,
            mainActivity: MainActivity,
            databaseReference: DatabaseReference,
            list: List<Customer>
    ) {
        if (isOnline) {
            viewModelScope.launch(Dispatchers.IO) {
                if (!list.isNullOrEmpty()) {
                    list.forEach { customer ->
                        if (Hawk.contains(customer.phone_number)) {
                            Hawk.delete(customer.phone_number)
                                databaseReference.push().setValue(customer).addOnCompleteListener { task ->
                                    if (task.isComplete && task.isSuccessful) {
                                    }
                                }

                        }

                        if (Hawk.contains("delete_customer")) {
                            val data = Hawk.get<HashMap<String, Customer>>("delete_customer")
                            if (data.containsKey(customer.phone_number)) {


                                val ref = FirebaseDatabase.getInstance().reference
                                val sQuery = ref.child("Contact").orderByChild("phone_number").equalTo(customer.phone_number)
                                sQuery.addListenerForSingleValueEvent(object : ValueEventListener {
                                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                                        for (snapshot in dataSnapshot.children) {
                                            snapshot.ref.removeValue()
                                        }
                                    }

                                    override fun onCancelled(databaseError: DatabaseError) {}
                                })


                            }
                        }
                    }
                }
//                customerList.observe(mainActivity) {
//                    if (!it.isNullOrEmpty()) {
//                        it.forEach { customer ->
//                            databaseReference.push().setValue(customer).addOnCompleteListener { task ->
//                                if (task.isComplete && task.isSuccessful) {
//                                    AppDatabase.getInstance(mainActivity.applicationContext).customerDao().deleteCustomer(customer)
//                                }
//                            }
//                        }
//                    }
//                }
            }
        }
    }
}