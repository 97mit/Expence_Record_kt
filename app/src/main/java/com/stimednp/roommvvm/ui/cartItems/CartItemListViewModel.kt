package com.stimednp.roommvvm.ui.cartItems

import androidx.lifecycle.ViewModel
import com.stimednp.roommvvm.data.db.entity.Meals
import com.stimednp.roommvvm.data.db.entity.CartItems
import com.stimednp.roommvvm.data.repository.CartItemsRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartItemListViewModel @Inject constructor(private val repository: CartItemsRepositoryImpl) : ViewModel(){
    fun getAllCartItems() = repository.getAllCartItems()

    suspend fun insertCartItems(menuItems: CartItems) = repository.insertCartItems(menuItems)
    suspend fun updateCartItems(menuItems: CartItems) = repository.updateCartItems(menuItems)

    suspend fun deleteCartItems(menuItems: CartItems) = repository.deleteCartItems(menuItems)

    suspend fun deleteCartItemsById(id: Int) = repository.deleteCartItemsById(id)

    suspend fun clearCartItems() = repository.clearCartItems()
}