package com.stimednp.roommvvm.data.repository

import androidx.lifecycle.LiveData
import com.stimednp.roommvvm.data.db.entity.MessInfo

/**
 * Created by rivaldy on Oct/18/2020.
 * Find me on my lol Github :D -> https://github.com/im-o
 */

interface MessRepository {
    fun getAllMesses(): LiveData<List<MessInfo>>
    suspend fun insertMess(messInfo: MessInfo)
    suspend fun updateMess(messInfo: MessInfo)
    suspend fun deleteMess(messInfo: MessInfo)
    suspend fun deleteMessById(id: Int)
    suspend fun clearMess()
}