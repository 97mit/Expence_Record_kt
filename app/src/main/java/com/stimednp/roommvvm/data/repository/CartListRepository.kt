package com.stimednp.roommvvm.data.repository

import androidx.lifecycle.LiveData
import com.stimednp.roommvvm.data.db.entity.CartList

interface CartListRepository {
    fun getAllCarts(): LiveData<List<CartList>>
    suspend fun insertCart(cartList: CartList)
    suspend fun updateCart(cartList: CartList)
    suspend fun deleteCart(cartList: CartList)
    suspend fun deleteCartById(id: Int)
    suspend fun clearCart()
}