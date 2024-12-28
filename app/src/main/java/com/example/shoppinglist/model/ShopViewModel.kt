package com.example.shoppinglist.model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglist.repo.ShopRepo
import kotlinx.coroutines.launch

class ShopViewModel : ViewModel() {

    private val repo = ShopRepo()


    fun loadData(context: Context) {
        viewModelScope.launch{
            repo.loadData(context)
        }
    }
    fun getProductLiveData() : LiveData<MutableList<Product>> {
        return repo.getData()
    }
    fun removeItem(pos :Int)  {
         repo.removeitem(pos)
    }

    fun saveAll(){
        viewModelScope.launch {
            repo.saveAll()
        }
    }

}