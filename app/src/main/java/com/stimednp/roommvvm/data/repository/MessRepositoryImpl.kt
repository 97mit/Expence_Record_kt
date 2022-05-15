package com.stimednp.roommvvm.data.repository

import com.stimednp.roommvvm.data.db.Dao.MessInfoDao
import com.stimednp.roommvvm.data.db.entity.MessInfo
import javax.inject.Inject

/**
 * Created by rivaldy on 07,February,2021
 * Find me on my lol Github :D -> https://github.com/im-o
 */

class MessRepositoryImpl @Inject constructor(private val messInfoDao: MessInfoDao) : MessRepository {

    override fun getAllMesses() = messInfoDao.getAllMesses()

    override suspend fun insertMess(messInfo: MessInfo) = messInfoDao.insertMess(messInfo)

    override suspend fun updateMess(messInfo: MessInfo) = messInfoDao.updateMess(messInfo)

    override suspend fun deleteMess(messInfo: MessInfo) = messInfoDao.deleteMess(messInfo)

    override suspend fun deleteMessById(id: Int) = messInfoDao.deleteMessById(id)

    override suspend fun clearMess() = messInfoDao.clearMess()
}