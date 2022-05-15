package com.stimednp.roommvvm.data.repository

import com.stimednp.roommvvm.data.db.Dao.MealsDao
import com.stimednp.roommvvm.data.db.entity.Meals
import javax.inject.Inject


class EditMealRepositoryImpl @Inject constructor(private val mealsDao: MealsDao) :EditMealRepository{

    override suspend fun insertMeal(meal: Meals) = mealsDao.insert(meal)

    override suspend fun updateMeal(meal: Meals) = mealsDao.update(meal)

    override suspend fun deleteMeal(meal: Meals) = mealsDao.delete(meal)

    override fun getAllLiveMealOfTheDay(mealDate: String) = mealsDao.getAllLiveMealsOfTheDay(mealDate)
    override suspend fun getAllMealOfTheDay(mealDate: String) = mealsDao.getAllMealsOfTheDay(mealDate)


}