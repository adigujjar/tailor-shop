package com.example.taylorshop.utilities

import com.example.taylorshop.Models.Customer
import com.example.taylorshop.data.CustomerDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CustomerRepository @Inject constructor(private val customerDao: CustomerDao) {

   suspend fun createCustomerLocally(customer: Customer) {
       customerDao.insertCustomer(customer)
   }

    suspend fun updateCustomerLocally(customer: Customer) {
       customerDao.updateCustomer(customer)
   }

    suspend fun deleteCustomerLocally(customer: Customer) {
       customerDao.deleteCustomer(customer)
   }

    fun getCustomerList() = customerDao.getCustomerList()
}