package com.example.taylorshop.utilities

import androidx.lifecycle.ViewModel
import com.example.taylorshop.Models.Customer
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CustomerVM @Inject constructor(
 customerRepository: CustomerRepository
): ViewModel() {
    val customerList: kotlinx.coroutines.flow.Flow<List<Customer>> = customerRepository.getCustomerList()
}