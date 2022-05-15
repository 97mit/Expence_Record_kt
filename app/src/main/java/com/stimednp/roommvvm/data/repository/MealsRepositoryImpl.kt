package com.stimednp.roommvvm.data.repository

import com.stimednp.roommvvm.data.db.Dao.MealsDao
import com.stimednp.roommvvm.data.db.entity.Meals
import com.stimednp.roommvvm.data.db.entity.MenuItems
import javax.inject.Inject

class MealsRepositoryImpl @Inject constructor(private val mealsDao: MealsDao) :MealRepository{
    override fun getAllMeals() = mealsDao.getAllMealDate()

    override suspend fun insertMeal(meal: Meals) = mealsDao.insert(meal)

    override suspend fun updateMeal(meal: Meals) = mealsDao.update(meal)

    override suspend fun deleteMeal(meal: Meals) = mealsDao.delete(meal)

    override fun getAllMealsOfMonth(month: String,year: String) = mealsDao.getAllMealOfMonth(month,year)
    override fun getLiveMealOfTheDay(mealDate: String) = mealsDao.getAllLiveMealsOfTheDay(mealDate)
    override suspend fun getMealOfTheDay(mealDate: String) = mealsDao.getAllMealsOfTheDay(mealDate)
    override suspend fun getAllYears() = mealsDao.getAllYears()
    override suspend fun getAllMonth(default_year:String) = mealsDao.getAllMonth(default_year)
    override suspend fun getAllMealOfMonthData(month:String,year:String) = mealsDao.getAllMealOfMonthData(month,year)


}