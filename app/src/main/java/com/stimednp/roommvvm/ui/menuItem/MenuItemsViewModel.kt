package com.stimednp.roommvvm.ui.menuItem

import androidx.lifecycle.ViewModel
import com.stimednp.roommvvm.data.db.entity.Meals
import com.stimednp.roommvvm.data.db.entity.MenuItems
import com.stimednp.roommvvm.data.repository.MenuItemsRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class MenuItemsViewModel @Inject constructor(private val repository: MenuItemsRepositoryImpl) : ViewModel(){
    fun getAllMenuItems() = repository.getAllMenuItems()

    suspend fun insertMenuItems(menuItems: MenuItems) = repository.insertMenuItems(menuItems)

    suspend fun insertMeal(meals: Meals) = repository.insertMeals(meals)

    suspend fun updateMenuItems(menuItems: MenuItems) = repository.updateMenuItems(menuItems)

    suspend fun deleteMenuItems(menuItems: MenuItems) = repository.deleteMenuItems(menuItems)

    suspend fun deleteMenuItemsById(id: Int) = repository.deleteMenuItemsById(id)

    suspend fun clearMenuItems() = repository.clearMenuItems()
}