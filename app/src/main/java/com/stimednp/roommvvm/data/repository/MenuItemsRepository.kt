package com.stimednp.roommvvm.data.repository

import androidx.lifecycle.LiveData
import com.stimednp.roommvvm.data.db.entity.Meals
import com.stimednp.roommvvm.data.db.entity.MenuItems

interface MenuItemsRepository {
    fun getAllMenuItems(): LiveData<List<MenuItems>>
    suspend fun insertMenuItems(menuItems: MenuItems)
    suspend fun insertMeals(meals: Meals)
    suspend fun updateMenuItems(menuItems: MenuItems)
    suspend fun deleteMenuItems(menuItems: MenuItems)
    suspend fun deleteMenuItemsById(id: Int)
    suspend fun clearMenuItems()
}