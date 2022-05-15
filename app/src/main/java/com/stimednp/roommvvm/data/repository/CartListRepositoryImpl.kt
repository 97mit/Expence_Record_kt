package com.stimednp.roommvvm.data.repository

import com.stimednp.roommvvm.data.db.Dao.CartListDao
import com.stimednp.roommvvm.data.db.entity.CartList
import javax.inject.Inject


class CartListRepositoryImpl @Inject constructor(private val cartListDao: CartListDao) : CartListRepository {

    override fun getAllCarts() = cartListDao.getAllCarts()

    override suspend fun insertCart(cartList: CartList) = cartListDao.insertCart(cartList)

    override suspend fun updateCart(cartList: CartList) = cartListDao.updateCart(cartList)

    override suspend fun deleteCart(cartList: CartList) = cartListDao.deleteCart(cartList)

    override suspend fun deleteCartById(id: Int) = cartListDao.deleteCartById(id)

    override suspend fun clearCart() = cartListDao.clearCart()
}