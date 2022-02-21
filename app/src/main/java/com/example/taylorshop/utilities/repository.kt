package com.example.taylorshop.utilities

import com.example.taylorshop.Models.Customer
import com.example.taylorshop.data.CustomerDao
import javax.inject.Inject
import javax.inject.Singleton

class CustomerRepository (private val customerDao: CustomerDao) {

    fun getCustomerList() = customerDao.getCustomerList()
}