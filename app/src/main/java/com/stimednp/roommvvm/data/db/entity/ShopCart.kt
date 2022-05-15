package com.stimednp.roommvvm.data.db.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.stimednp.roommvvm.R
import kotlinx.parcelize.Parcelize
import java.lang.Math.random

@Entity(tableName = "shop_cart_table")
@Parcelize
data class ShopCart(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val title: String?,
    val price: Int,
    var itemCount: Int?,
    var itemUnit:String?,
    var cartId: Int,
    var cartItemId:Int?,
    @Ignore
    var addedDate: String?,
    var icon: String?,
) : Parcelable {
    constructor(
        id: Int?,
        title: String?,
        price: Int,
        itemCount: Int?,
        itemUnit: String?,
        cartId: Int,
        cartItemId: Int,
        icon: String?
    ) : this(id, title, price, itemCount,itemUnit, cartId,cartItemId, "yyyy-dd-mm", icon)
}