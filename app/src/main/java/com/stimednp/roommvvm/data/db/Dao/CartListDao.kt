package com.stimednp.roommvvm.data.db.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.stimednp.roommvvm.data.db.entity.CartList
import com.stimednp.roommvvm.data.db.entity.MessInfo


@Dao
interface CartListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCart(cartList: CartList)

    @Update
    suspend fun updateCart(cartList: CartList)

    @Delete
    suspend fun deleteCart(cartList: CartList)

    @Query("SELECT * FROM cart_list_table ORDER BY title DESC")
    fun getAllCarts(): LiveData<List<CartList>>
    // why not use suspend ? because Room does not support LiveData with suspended functions.
    // LiveData already works on a background thread and should be used directly without using coroutines

    @Query("DELETE FROM cart_list_table")
    suspend fun clearCart()

    @Query("DELETE FROM cart_list_table WHERE id = :id") //you can use this too, for delete note by id.
    suspend fun deleteCartById(id: Int)
}