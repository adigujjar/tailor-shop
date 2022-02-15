package com.example.taylorshop.data

import android.provider.SyncStateContract.Helpers.insert
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.taylorshop.Models.Customer

/**
 * The Data Access Object for the [CustomerDao] class.
 */
@Dao
interface CustomerDao {
    @Query("SELECT * FROM Customer")
    fun getCustomerList(): LiveData<List<Customer>>

    @Insert
    fun insertCustomer(customer: Customer): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateCustomer(customer: Customer)

    @Delete
    fun deleteCustomer(customer: Customer)

    @Query("SELECT * from Customer WHERE phone_number= :phone")
    fun getItemById(phone: String): List<Customer?>?

}
