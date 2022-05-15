package com.stimednp.roommvvm.ui.EditMeals

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.stimednp.roommvvm.data.db.entity.Meals
import com.stimednp.roommvvm.data.repository.EditMealRepositoryImpl
import com.stimednp.roommvvm.data.repository.MealsRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditMealViewModel @Inject constructor(private val repository: EditMealRepositoryImpl): ViewModel() {
    suspend fun insert(meal: Meals) = repository.insertMeal(meal)

    suspend fun update(meal: Meals) = repository.updateMeal(meal)

    suspend fun delete(meal: Meals) = repository.deleteMeal(meal)
    fun getAllLiveMealOfTheDay(mealDate: String) = repository.getAllLiveMealOfTheDay(mealDate)
    suspend fun getAllMealOfTheDay(mealDate: String) = repository.getAllMealOfTheDay(mealDate)
}