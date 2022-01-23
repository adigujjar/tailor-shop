package com.example.taylorshop.utilities

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.taylorshop.Models.Customer
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.concurrent.Flow
import javax.inject.Inject

@HiltViewModel
class CustomerVM @Inject constructor(
val customerRepository: CustomerRepository
): ViewModel() {

    val customerList: kotlinx.coroutines.flow.Flow<List<Customer>> = customerRepository.getCustomerList()
}