package com.stimednp.roommvvm.data.db.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.stimednp.roommvvm.R
import kotlinx.parcelize.Parcelize

@Entity(tableName = "cart_items_table")
@Parcelize
data class CartItems(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val title: String?,
    val price: Int,
    var itemCount: Int? = 1,
    var itemUnit: String?,
    @Ignore var cartId:Int,
    @Ignore var addedDate: String?,
    var icon: String?,
):Parcelable{
    constructor(id: Int?,
                title: String?,
                price: Int,
                itemCount: Int? = 1,
                itemUnit: String? = "",
                icon: String? = "",
    ):this(id, title, price, itemCount,itemUnit,-1, "yyyy-dd-mm", icon)
}