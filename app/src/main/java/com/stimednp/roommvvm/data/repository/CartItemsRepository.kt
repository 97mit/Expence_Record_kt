package com.stimednp.roommvvm.data.repository

import androidx.lifecycle.LiveData
import com.stimednp.roommvvm.data.db.entity.CartItems

interface CartItemsRepository {
    fun getAllCartItems(): LiveData<List<CartItems>>
    suspend fun insertCartItems(cartItems: CartItems)
    suspend fun updateCartItems(cartItems: CartItems)
    suspend fun deleteCartItems(cartItems: CartItems)
    suspend fun deleteCartItemsById(id: Int)
    suspend fun clearCartItems()
}