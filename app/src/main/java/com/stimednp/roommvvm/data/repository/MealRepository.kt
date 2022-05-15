package com.stimednp.roommvvm.data.repository

import androidx.lifecycle.LiveData
import com.stimednp.roommvvm.data.db.entity.Meals

interface MealRepository {
    fun getAllMeals() : LiveData<List<Meals>>
    suspend fun insertMeal(meal: Meals)
    suspend fun updateMeal(meal: Meals)
    suspend fun deleteMeal(meal: Meals)
    fun getAllMealsOfMonth(month: String,year: String):LiveData<List<Meals>>
    fun getLiveMealOfTheDay(mealDate: String):LiveData<String>
    suspend fun getMealOfTheDay(mealDate: String):String
    suspend fun getAllYears(): Array<String>
    suspend fun getAllMonth(default_year:String): List<String>
    suspend fun getAllMealOfMonthData(month:String,year:String): List<Meals>
}