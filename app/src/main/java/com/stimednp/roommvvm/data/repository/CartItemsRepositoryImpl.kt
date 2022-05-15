package com.stimednp.roommvvm.data.repository

import com.stimednp.roommvvm.data.db.Dao.CartItemsDao
import com.stimednp.roommvvm.data.db.entity.Meals
import com.stimednp.roommvvm.data.db.entity.CartItems
import javax.inject.Inject

class CartItemsRepositoryImpl @Inject constructor(private val cartItemsDao: CartItemsDao) : CartItemsRepository {
    override fun getAllCartItems() = cartItemsDao.getAllCartItems()

    override suspend fun insertCartItems(cartItems: CartItems) = cartItemsDao.insert(cartItems)


    override suspend fun updateCartItems(cartItems: CartItems) = cartItemsDao.update(cartItems)

    override suspend fun deleteCartItems(cartItems: CartItems) = cartItemsDao.delete(cartItems)

    override suspend fun deleteCartItemsById(id: Int) = cartItemsDao.deleteCartItemsById(id)

    override suspend fun clearCartItems() = cartItemsDao.deleteAllCartItems()
}