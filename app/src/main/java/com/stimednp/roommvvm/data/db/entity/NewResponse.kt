package com.stimednp.roommvvm.data.db.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
): Parcelable