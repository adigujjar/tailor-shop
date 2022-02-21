package com.example.taylorshop.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Customer")
data class Customer(
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0,
    @ColumnInfo(name = "serial_number")
    var serial_number: String? = null,

    @ColumnInfo(name = "name")
    var name: String? = null,

    @ColumnInfo(name = "pkF")
    var pkF: Boolean = false,

    @ColumnInfo(name = "pkR")
    var pkR: Boolean = false,

    @ColumnInfo(name = "pkL")
    var pkL: Boolean = false,

    @ColumnInfo(name = "pkT")
    var pkT: Boolean = false,

    @ColumnInfo(name = "phone_number")
    var phone_number: String? = null,

    @ColumnInfo(name = "lengthSuit")
    var lengthSuit: String? = null,

    @ColumnInfo(name = "shoulderSuit")
    var shoulderSuit: String? = null,

    @ColumnInfo(name = "armsSuit")
    var armsSuit: String? = null,

    @ColumnInfo(name = "chestSuit")
    var chestSuit: String? = null,

    @ColumnInfo(name = "backSuit")
    var backSuit: String? = null,

    @ColumnInfo(name = "neckSuit")
    var neckSuit: String? = null,

    @ColumnInfo(name = "chestLoose")
    var chestLoose: String? = null,

    @ColumnInfo(name = "backLoose")
    var backLoose: String? = null,

    @ColumnInfo(name = "suitFront")
    var suitFront: String? = null,

    @ColumnInfo(name = "trouserLength")
    var trouserLength: String? = null,

    @ColumnInfo(name = "trouserEdge")
    var trouserEdge: String? = null,

    @ColumnInfo(name = "suitPocket")
    var suitPocket: String? = null,

    @ColumnInfo(name = "suitModa")
    var suitModa: String? = null,

    @ColumnInfo(name = "suitExtraNotes")
    var suitExtraNotes: String? = null,

    @ColumnInfo(name = "suitCuff")
    var suitCuff: String? = null,

    @ColumnInfo(name = "key")
    var key: String? = null,

    @ColumnInfo(name = "colorOrBanDesign")
    var colorOrBanDesign: String? = null,

    @ColumnInfo(name = "keraDesign")
    var keraDesign: String? = null,
)