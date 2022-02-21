package com.example.taylorshop.utilities

import android.app.Application
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.taylorshop.MainActivity
import com.example.taylorshop.Models.Customer
import com.example.taylorshop.data.AppDatabase
import com.google.firebase.database.*
import com.orhanobut.hawk.Hawk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CustomerVM(
        application: Application
) : AndroidViewModel(application) {
    var customerList: LiveData<List<Customer>>
    var repository: CustomerRepository
    var databaseRef = FirebaseDatabase.getInstance().reference
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
                        databaseRef = FirebaseDatabase.getInstance().reference.child("Contact").child(customer.id.toString())
                        if (Hawk.contains(customer.phone_number)) {
                            Hawk.delete(customer.phone_number)
                                databaseRef.setValue(customer).addOnCompleteListener { task -> if (task.isComplete && task.isSuccessful) { } }
                        }

                        if (Hawk.contains("update_customer")) {
                            val data = Hawk.get<HashMap<String, Customer>>("update_customer")
                            if (data.containsKey(customer.phone_number)) {
                                val childUpdates = java.util.HashMap<String, Any>()
                                val updateUser = data[customer.phone_number]
                                childUpdates[updateUser?.id.toString()] = updateUser!!
                                databaseReference.updateChildren(childUpdates)
                                data.remove(customer.phone_number)
                                Hawk.put("update_customer", data)
                            }
                        }
                    }
                }
            }
        }
    }

    fun deleteAfterOnline(databaseReference: DatabaseReference) {
        if (Hawk.contains("delete_customer")) {
            val data = Hawk.get<HashMap<String, Customer>>("delete_customer")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                data.forEach { (_, customer) ->
                    Hawk.delete("delete_customer")
                    databaseReference.child(customer.id.toString()).ref.removeValue()
                }
            }
        }
    }
}