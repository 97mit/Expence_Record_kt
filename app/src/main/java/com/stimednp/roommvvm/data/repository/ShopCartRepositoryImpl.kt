package com.stimednp.roommvvm.data.repository

import com.stimednp.roommvvm.data.db.Dao.ShopCartDao
import com.stimednp.roommvvm.data.db.entity.CartItems
import com.stimednp.roommvvm.data.db.entity.ShopCart
import javax.inject.Inject

class ShopCartRepositoryImpl @Inject constructor(private val shopCartDao: ShopCartDao) :ShopCartRepository{
    override fun getAllLiveShopCartItems(cartId:Int) = shopCartDao.getAllLiveItemsOfCart(cartId)
    override suspend fun getAllShopCartItems(cartId:Int) = shopCartDao.getAllItemsOfCart(cartId)
    override suspend fun getSingleCartItem(itemId:Int,shopCartId:Int) = shopCartDao.getSingleCartItem(itemId,shopCartId)

    override suspend fun insertCart(shopCart: ShopCart?) = shopCartDao.insert(shopCart)
    override suspend fun insertCart(shopCart: List<ShopCart>?) = shopCartDao.insert(shopCart)

    override suspend fun updateCart(shopCart: ShopCart?) = shopCartDao.update(shopCart)

    override suspend fun deleteCart(shopCart: ShopCart?) = shopCartDao.delete(shopCart)
    override suspend fun getSumOfTheCart(cartItemIds: IntArray) = shopCartDao.getSumOfTheCart(cartItemIds)

}