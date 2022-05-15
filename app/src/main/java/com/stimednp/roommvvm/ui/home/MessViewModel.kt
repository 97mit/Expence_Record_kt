package com.stimednp.roommvvm.ui.home
import androidx.lifecycle.ViewModel
import com.stimednp.roommvvm.data.db.entity.MessInfo
import com.stimednp.roommvvm.data.repository.MessRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by rivaldy on Oct/18/2020.
 * Find me on my lol Github :D -> https://github.com/im-o
 */
@HiltViewModel
class MessViewModel @Inject constructor(private val repository: MessRepositoryImpl) : ViewModel() {

    suspend fun insertMess(messInfo: MessInfo) = repository.insertMess(messInfo)

    suspend fun updateMess(messInfo: MessInfo) = repository.updateMess(messInfo)

    suspend fun deleteMess(messInfo: MessInfo) = repository.deleteMess(messInfo)

    suspend fun deleteMessById(id: Int) = repository.deleteMessById(id)

    suspend fun clearMess() = repository.clearMess()

    fun getAllMesses() = repository.getAllMesses()
}