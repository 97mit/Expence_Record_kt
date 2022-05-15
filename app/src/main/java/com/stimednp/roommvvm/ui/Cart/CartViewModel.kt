package com.stimednp.roommvvm.ui.Cart

import androidx.lifecycle.ViewModel
import com.stimednp.roommvvm.data.db.entity.CartItems
import com.stimednp.roommvvm.data.db.entity.ShopCart
import com.stimednp.roommvvm.data.repository.ShopCartRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class CartViewModel @Inject constructor(private val repository: ShopCartRepositoryImpl): ViewModel() {

    fun getAllLiveShopCartItems(cartId:Int) = repository.getAllLiveShopCartItems(cartId)
    suspend fun getAllShopCartItems(cartId:Int) = repository.getAllShopCartItems(cartId)
    suspend fun getSingleCartItem(itemId:Int,shopCartId:Int) = repository.getSingleCartItem(itemId,shopCartId)

    suspend fun insertShopCart(shopCart: ShopCart?) = repository.insertCart(shopCart)
    suspend fun insertShopCart(shopCart: List<ShopCart>?) = repository.insertCart(shopCart)

    suspend fun updateShopCart(shopCart: ShopCart?) = repository.updateCart(shopCart)

    suspend fun deleteShopCart(shopCart: ShopCart?) = repository.deleteCart(shopCart)


    suspend fun getSumOfTheCart(cartItemIds: IntArray) = repository.getSumOfTheCart(cartItemIds)

}