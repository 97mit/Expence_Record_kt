package com.stimednp.roommvvm.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.stimednp.roommvvm.data.db.Dao.MenuItemsDao
import com.stimednp.roommvvm.data.db.MessDatabase
import com.stimednp.roommvvm.data.db.ShoppingDB
import com.stimednp.roommvvm.data.db.entity.MenuItems
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by rivaldy on 07,February,2021
 * Find me on my lol Github :D -> https://github.com/im-o
 */

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun providesNoteDatabase(@ApplicationContext context: Context): MessDatabase {
        return Room.databaseBuilder(context, MessDatabase::class.java, MessDatabase.DB_NAME).build()
    }

    @Singleton
    @Provides
    fun providesMessDao(messDatabase: MessDatabase) = messDatabase.getMessDao()

    @Singleton
    @Provides
    fun providesMenuItemDao(messDatabase: MessDatabase) = messDatabase.getMenuItemsDao()

    @Singleton
    @Provides
    fun providesMealsDao(messDatabase: MessDatabase) = messDatabase.getMealsDao()

    /*=============================================================================================*/
    @Singleton
    @Provides
    fun providesCartDatabase(@ApplicationContext context: Context): ShoppingDB {
        return Room.databaseBuilder(context, ShoppingDB::class.java, ShoppingDB.SHOPPING_CART_DB_NAME).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun providesCartItemsDao(shoppingDB: ShoppingDB) = shoppingDB.getCartItemDao()

    @Singleton
    @Provides
    fun provideCartDao(shoppingDB: ShoppingDB) = shoppingDB.getCartDao()

    @Singleton
    @Provides
    fun provideShopCartDao(shoppingDB: ShoppingDB) = shoppingDB.getShopCartDao()
}
