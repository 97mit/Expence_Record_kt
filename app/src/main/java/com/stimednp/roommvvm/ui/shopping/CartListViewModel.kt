package com.stimednp.roommvvm.ui.shopping

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stimednp.roommvvm.data.db.entity.CartList
import com.stimednp.roommvvm.data.repository.CartListRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartListViewModel @Inject constructor(private val repository: CartListRepositoryImpl) : ViewModel() {

    suspend fun insertCart(cartInfo: CartList) = repository.insertCart(cartInfo)

    suspend fun updateCart(cartInfo: CartList) = repository.updateCart(cartInfo)

    suspend fun deleteCart(cartInfo: CartList) = repository.deleteCart(cartInfo)

    suspend fun deleteCartById(id: Int) = repository.deleteCartById(id)

    suspend fun clearCart() = repository.clearCart()

    fun getAllCarts() = repository.getAllCarts()
}