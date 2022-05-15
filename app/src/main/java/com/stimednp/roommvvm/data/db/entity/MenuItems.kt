package com.stimednp.roommvvm.data.db.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "menu_table")
@Parcelize
data class MenuItems(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val title: String?,
    val price: Int,
    var itemCount: Int? = 1,
    @Ignore var addedDate: String?,
    ):Parcelable{
        constructor(id: Int?,
                    title: String?,
                    price: Int,
                    itemCount: Int? = 1
        ):this(id, title, price, itemCount, "yyyy-dd-mm")
}