package com.stimednp.roommvvm.data.db.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.stimednp.roommvvm.data.db.entity.Meals
import com.stimednp.roommvvm.data.db.entity.MenuItems

@Dao
interface MenuItemsDao {
    @Insert
    suspend fun insert(note: MenuItems)
    @Insert(onConflict = REPLACE)
    suspend fun insertMeals(meals: Meals)

    @Update
    suspend fun update(note: MenuItems)

    @Delete
    suspend fun delete(note: MenuItems)

    @Query("DELETE FROM menu_table")
    suspend fun deleteAllMenuItems()

    @Query("DELETE FROM menu_table WHERE id=:id")
    suspend fun deleteMenuItemsById(id:Int)

    @Query("SELECT * FROM menu_table WHERE id=:itemId")
    suspend fun getItem(itemId: Int): MenuItems

    @Query("SELECT * FROM menu_table ORDER BY price DESC")
    fun getAllMenuItems(): LiveData<List<MenuItems>>
}