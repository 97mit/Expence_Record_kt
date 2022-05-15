package com.stimednp.roommvvm.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.stimednp.roommvvm.data.db.Dao.*
import com.stimednp.roommvvm.data.db.entity.CartList
import com.stimednp.roommvvm.data.db.entity.CartItems
import com.stimednp.roommvvm.data.db.entity.ShopCart

@Database(
    entities = [CartItems::class,CartList::class,ShopCart::class],
    version = 13,
    exportSchema = false
)
abstract class ShoppingDB : RoomDatabase() {
    abstract fun getCartItemDao(): CartItemsDao
    abstract fun getCartDao(): CartListDao
    abstract fun getShopCartDao(): ShopCartDao

    companion object {
        const val SHOPPING_CART_DB_NAME = "shopping_cart.db"
    }
}