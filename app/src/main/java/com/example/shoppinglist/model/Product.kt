package com.example.shoppinglist.model

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("product_code")
    val id: String,

    @SerializedName("product_unit")
    val productUnit: String,

    @SerializedName("product_name")
    val name: String,

    @SerializedName("convQty")
    var quantity: String,

    @SerializedName("Rate")
    var rate: Int = 20
    ){

}
