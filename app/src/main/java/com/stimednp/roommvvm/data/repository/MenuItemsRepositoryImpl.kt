package com.stimednp.roommvvm.data.repository

import androidx.lifecycle.LiveData
import com.stimednp.roommvvm.data.db.Dao.MenuItemsDao
import com.stimednp.roommvvm.data.db.entity.Meals
import com.stimednp.roommvvm.data.db.entity.MenuItems
import com.stimednp.roommvvm.data.db.entity.MessInfo
import javax.inject.Inject

class MenuItemsRepositoryImpl @Inject constructor(private val menuItemsDao: MenuItemsDao) : MenuItemsRepository {
        override fun getAllMenuItems() = menuItemsDao.getAllMenuItems()

        override suspend fun insertMenuItems(menuItems: MenuItems) = menuItemsDao.insert(menuItems)

        override suspend fun insertMeals(meals: Meals) = menuItemsDao.insertMeals(meals)

        override suspend fun updateMenuItems(menuItems: MenuItems) = menuItemsDao.update(menuItems)

        override suspend fun deleteMenuItems(menuItems: MenuItems) = menuItemsDao.delete(menuItems)

        override suspend fun deleteMenuItemsById(id: Int) = menuItemsDao.deleteMenuItemsById(id)

        override suspend fun clearMenuItems() = menuItemsDao.deleteAllMenuItems()
}