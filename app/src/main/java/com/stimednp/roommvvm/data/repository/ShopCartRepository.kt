package com.stimednp.roommvvm.data.repository

import androidx.lifecycle.LiveData
import com.stimednp.roommvvm.data.db.entity.CartItems
import com.stimednp.roommvvm.data.db.entity.ShopCart

interface ShopCartRepository {
    fun getAllLiveShopCartItems(cartId:Int) : LiveData<List<ShopCart>?>
    suspend fun getAllShopCartItems(cartId:Int) : List<ShopCart>?
    suspend fun getSingleCartItem(itemId:Int ,shopCartId:Int) : ShopCart
    suspend fun insertCart(shopCart: ShopCart?)
    suspend fun insertCart(shopCart: List<ShopCart>?)
    suspend fun updateCart(shopCart: ShopCart?)
    suspend fun deleteCart(shopCart: ShopCart?)
    suspend fun getSumOfTheCart(cartItemIds: IntArray): Int
}