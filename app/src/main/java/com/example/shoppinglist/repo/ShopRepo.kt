package com.example.shoppinglist.repo

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.shoppinglist.model.Product
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShopRepo {

    private var productLiveData = MutableLiveData<MutableList<Product>>()
    var context: Context? = null

     suspend fun loadData(context: Context) {
        this.context = context
//        productLiveData.postValue( getProductFromRoom())
        fetchProducts()
    }
    fun getData(): MutableLiveData<MutableList<Product>> {
        return productLiveData
    }

    private suspend fun fetchProducts() {
        val call = Retrofit.api.getProducts()
        call.enqueue(object : Callback<MutableList<Product>> {
            override fun onResponse(call: Call<MutableList<Product>>, response: Response<MutableList<Product>>) {
                if (response.isSuccessful) {
                    // Handle the successful response
                    val products = response.body()
                    Log.d("ShopRepo", "Products: $products")

                    productLiveData.postValue( response.body())
                    GlobalScope.launch{
//                        saveProductInRoom(response.body())
                    }

                    products?.forEach { p ->
                        Log.d("Product", "${p.name}: ${p.name}, id: ${p.id}")
                    }

                } else {
                    Log.e("ShopRepo", "Failed: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<MutableList<Product>>, t: Throwable) {
                Log.e("MainActivity", "Error: ${t.message}")
            }
        })
    }

    fun removeitem(pos: Int) {

        val currentList = productLiveData.value ?: emptyList<Product>().toMutableList()

        currentList.removeAt(pos)

        productLiveData.value = currentList
    }

    suspend fun saveAll() {

        val call = Retrofit.api.saveProducts(productLiveData.value!!)
        call.enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Toast.makeText(context,response.body().toString(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

            }


        })

    }
}