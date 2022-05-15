package com.stimednp.roommvvm.data.db.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.stimednp.roommvvm.data.db.entity.CartItems

@Dao
interface CartItemsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cartItems: CartItems)
    @Update
    suspend fun update(cartItems: CartItems)

    @Delete
    suspend fun delete(cartItems: CartItems)

    @Query("DELETE FROM cart_items_table")
    suspend fun deleteAllCartItems()

    @Query("DELETE FROM cart_items_table WHERE id=:id")
    suspend fun deleteCartItemsById(id:Int)

    @Query("SELECT * FROM cart_items_table WHERE id=:itemId")
    suspend fun getItem(itemId: Int): CartItems

    @Query("SELECT * FROM cart_items_table ORDER BY price DESC")
    fun getAllCartItems(): LiveData<List<CartItems>>
}