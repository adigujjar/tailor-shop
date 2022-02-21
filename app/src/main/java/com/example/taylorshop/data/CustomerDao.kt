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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCustomer(customer: Customer): Long


    @Delete
    fun deleteCustomer(customer: Customer)

    @Query("UPDATE Customer SET serial_number= :Serial_number, name= :Name, lengthSuit= :LengthSuit, backLoose=:BackLoose,armsSuit=:ArmsSuit,chestLoose=:ChestLoose,backSuit=:BackSuit,colorOrBanDesign=:ColorOrBanDesign,keraDesign=:KeraDesign, lengthSuit=:LengthSuit,neckSuit=:NeckSuit, phone_number=:Phone_number, shoulderSuit=:ShoulderSuit,suitCuff=:SuitCuff, suitFront=:SuitFront, suitModa=:SuitModa,suitPocket=:SuitPocket, trouserEdge=:TrouserEdge,trouserLength=:TrouserLength,pkF =:fp,pkL = :lp, pkR = :rp, pkT= :tp WHERE phone_number= :Phone_number")
    fun updateCustomer(BackLoose: String, ArmsSuit: String,
                       ChestLoose: String, BackSuit: String,
                       ColorOrBanDesign: String, KeraDesign: String,
                       LengthSuit: String,
                       Name: String, NeckSuit: String,
                       Phone_number: String,
                       Serial_number: String, ShoulderSuit: String, SuitCuff: String,
                       SuitFront: String,
                       SuitModa: String, TrouserEdge: String,
                       TrouserLength: String, SuitPocket: String, fp: Boolean, lp: Boolean, rp: Boolean, tp: Boolean)

}
