package com.stimednp.roommvvm.data.db.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.stimednp.roommvvm.data.db.entity.Meals

@Dao
interface MealsDao {
    @Insert(onConflict = REPLACE)
    suspend fun insert(meals: Meals)

    @Update
    suspend fun update(meals: Meals)

    @Delete
    suspend fun delete(meals: Meals)

    @Query("DELETE FROM meal_table")
    suspend fun deleteAllMeals()

    @Query("SELECT *  FROM meal_table ORDER BY meal_date DESC")
    fun getAllMealDate(): LiveData<List<Meals>>

    @Query("SELECT *  FROM meal_table WHERE strftime('%m' ,meal_date ) =:month AND strftime('%Y' ,meal_date ) =:year ORDER BY meal_date DESC")
    fun getAllMealOfMonth(month: String, year: String): LiveData<List<Meals>>

    @Query("SELECT *  FROM meal_table WHERE strftime('%m' ,meal_date ) =:month AND strftime('%Y' ,meal_date ) =:year ORDER BY meal_date DESC")
    suspend fun getAllMealOfMonthData(month: String, year: String): List<Meals>

    @Query("SELECT DISTINCT strftime('%m' ,meal_date ) FROM meal_table WHERE strftime('%Y' ,meal_date ) =:year ORDER BY meal_date DESC")
    suspend fun getAllMonth(year: String?): List<String>

    @Query("SELECT DISTINCT strftime('%Y' ,meal_date ) FROM meal_table ORDER BY meal_date DESC")
    suspend fun getAllYears(): Array<String>

    /*TODO*/
    @Query("SELECT order_details FROM meal_table WHERE meal_date =:mealDate")
    suspend fun getAllMealsOfTheDay(mealDate: String): String

    @Query("SELECT order_details FROM meal_table WHERE meal_date =:mealDate")
    fun getAllLiveMealsOfTheDay(mealDate: String): LiveData<String>

    @Query("SELECT SUM(price) FROM menu_table WHERE id IN (:mealDate)")
    fun getSumOfTheDay(mealDate: IntArray): Int

    @Query("UPDATE meal_table SET item_sum =:amount WHERE meal_date =:meal_date")
    suspend fun updateTotalSum(amount: Int, meal_date: String)
}