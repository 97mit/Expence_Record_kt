package com.stimednp.roommvvm.ui.mess_home

import androidx.lifecycle.ViewModel
import com.stimednp.roommvvm.data.db.entity.Meals
import com.stimednp.roommvvm.data.repository.MealsRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MessHomeViewModel @Inject constructor(private val repository: MealsRepositoryImpl): ViewModel() {

     fun getAllMeals() = repository.getAllMeals()

     suspend fun insertMeal(meal: Meals) = repository.insertMeal(meal)

     suspend fun updateMeal(meal: Meals) = repository.updateMeal(meal)

     suspend fun deleteMeal(meal: Meals) = repository.deleteMeal(meal)

     fun getAllMealsOfMonth(month: String,year: String) = repository.getAllMealsOfMonth(month,year)
     fun getLiveMealOfTheDay(mealDate: String) = repository.getLiveMealOfTheDay(mealDate)
     suspend fun getMealOfTheDay(mealDate: String) = repository.getMealOfTheDay(mealDate)
     suspend fun getAllYears() = repository.getAllYears()
     suspend fun getAllMonth(default_year:String) = repository.getAllMonth(default_year)
     suspend fun getAllMealOfMonthData(month:String,year:String) = repository.getAllMealOfMonthData(month,year)
}