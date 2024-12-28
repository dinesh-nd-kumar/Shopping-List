package com.example.shoppinglist.repo

import com.example.shoppinglist.model.Product
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ProductApis {
    @GET("server/native_Db_V13.php?axn=get/taskproducts&divisionCode=258")
     fun getProducts(): Call<MutableList<Product>>

    @POST("server/native_Db_V13.php?axn=save/taskproddets&divisionCode=258")
     fun saveProducts(@Body data: MutableList<Product>): Call<ResponseBody>


}