package com.stimednp.roommvvm.data.db.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.stimednp.roommvvm.data.db.entity.CartItems
import com.stimednp.roommvvm.data.db.entity.ShopCart

@Dao
interface ShopCartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(shopCart: ShopCart?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(shopCartList: List<ShopCart>?)

    @Update
    suspend fun update(shopCart: ShopCart?)

    @Query("SELECT * FROM shop_cart_table WHERE cartItemId=:itemId AND cartId=:shopCartId ")
    suspend fun getSingleCartItem(itemId:Int, shopCartId:Int): ShopCart

    @Delete
    suspend fun delete(shopCart: ShopCart?)

    @Query("DELETE FROM shop_cart_table")
    suspend fun deleteAllShopCart()

    @Query("SELECT *  FROM shop_cart_table WHERE cartId=:cartId ORDER BY id DESC")
    fun getAllLiveItemsOfCart(cartId:Int): LiveData<List<ShopCart>?>

    @Query("SELECT *  FROM shop_cart_table WHERE cartId=:cartId ORDER BY id DESC")
    suspend fun getAllItemsOfCart(cartId:Int): List<ShopCart>?

    @Query("SELECT SUM(price) FROM cart_items_table WHERE id IN (:cart_item_ids)")
    fun getSumOfTheCart(cart_item_ids: IntArray): Int

}