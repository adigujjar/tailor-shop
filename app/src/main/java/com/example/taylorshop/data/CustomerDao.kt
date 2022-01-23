package com.example.taylorshop.data

import androidx.room.*
import com.example.taylorshop.Models.Customer
import kotlinx.coroutines.flow.Flow

/**
 * The Data Access Object for the [CustomerDao] class.
 */
@Dao
interface CustomerDao {
    @Query("SELECT * FROM Customer")
    fun getCustomerList(): Flow<List<Customer>>

    @Insert
    suspend fun insertCustomer(customer: Customer): Long

    @Insert
    suspend fun updateCustomer(customer: Customer): Long

    @Delete
    suspend fun deleteCustomer(customer: Customer)
}
