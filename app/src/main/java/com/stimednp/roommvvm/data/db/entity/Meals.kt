package com.stimednp.roommvvm.data.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "meal_table")
@Parcelize
data class Meals (
    @ColumnInfo(name = "order_details")
    var order_details: String ,

    @ColumnInfo(name = "item_count", defaultValue = "1")
    var itemCount :Int?= 0,

    @ColumnInfo(name = "item_sum")
    var sumOfMeals: Int?=0,

    @ColumnInfo(name = "mess_id")
    var messId: Int?=0,

    @PrimaryKey
    @ColumnInfo(name = "meal_date")
    var mealDate:String = "yyyy-mm-dd"
):Parcelable {
    constructor() : this("",1,0,-1,"yyyy-mm-dd")
}