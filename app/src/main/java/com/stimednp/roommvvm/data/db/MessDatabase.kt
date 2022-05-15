package com.stimednp.roommvvm.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.stimednp.roommvvm.data.db.Dao.MealsDao
import com.stimednp.roommvvm.data.db.Dao.MenuItemsDao
import com.stimednp.roommvvm.data.db.Dao.MessInfoDao
import com.stimednp.roommvvm.data.db.entity.Meals
import com.stimednp.roommvvm.data.db.entity.MenuItems
import com.stimednp.roommvvm.data.db.entity.MessInfo

/**
 * Created by rivaldy on Oct/18/2020.
 * Find me on my lol Github :D -> https://github.com/im-o
 */

@Database(
    entities = [MessInfo::class,MenuItems::class,Meals::class],
    version = 1,
    exportSchema = false
)
abstract class MessDatabase : RoomDatabase() {
    abstract fun getMessDao(): MessInfoDao
    abstract fun getMenuItemsDao(): MenuItemsDao
    abstract fun getMealsDao(): MealsDao

    companion object {
        const val DB_NAME = "mess_database.db"
    }
}