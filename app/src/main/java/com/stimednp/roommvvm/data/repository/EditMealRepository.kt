package com.stimednp.roommvvm.data.repository

import androidx.lifecycle.LiveData
import com.stimednp.roommvvm.data.db.entity.Meals

interface EditMealRepository {
    suspend fun insertMeal(meal: Meals)
    suspend fun updateMeal(meal: Meals)
    suspend fun deleteMeal(meal: Meals)
    fun getAllLiveMealOfTheDay(mealDate: String): LiveData<String>
    suspend fun getAllMealOfTheDay(mealDate: String): String
}