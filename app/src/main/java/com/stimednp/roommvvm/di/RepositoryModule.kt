package com.stimednp.roommvvm.di

import com.stimednp.roommvvm.data.db.Dao.*
import com.stimednp.roommvvm.data.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

/**
 * Created by rivaldy on 28,January,2021
 * Find me on my lol Github :D -> https://github.com/im-o
 */

@InstallIn(ActivityRetainedComponent::class)
@Module
object RepositoryModule {

    @Provides
    fun providesNoteRepository(messInfoDao: MessInfoDao) = MessRepositoryImpl(messInfoDao)

    @Provides
    fun providesMenuItemsRepository(menuItemsDao: MenuItemsDao) = MenuItemsRepositoryImpl(menuItemsDao)

    @Provides
    fun providesMealsRepository(mealsDao: MealsDao) = MealsRepositoryImpl(mealsDao)

    /*==============================================================================*/

    @Provides
    fun providesCartRepository(cartListDao: CartListDao) = CartListRepositoryImpl(cartListDao)
    @Provides
    fun providesCartItemRepository(cartItemDao: CartItemsDao) = CartItemsRepositoryImpl(cartItemDao)
    @Provides
    fun providesShopCartRepository(shopCartDao: ShopCartDao) = ShopCartRepositoryImpl(shopCartDao)
}