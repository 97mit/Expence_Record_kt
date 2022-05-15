package com.stimednp.roommvvm.data.db.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.stimednp.roommvvm.data.db.entity.MessInfo

/**
 * Created by rivaldy on Oct/18/2020.
 * Find me on my lol Github :D -> https://github.com/im-o
 */

@Dao
interface MessInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE) //if some data is same/conflict, it'll be replace with new data.
    suspend fun insertMess(messInfo: MessInfo)

    @Update
    suspend fun updateMess(messInfo: MessInfo)

    @Delete
    suspend fun deleteMess(messInfo: MessInfo)

    @Query("SELECT * FROM mess_table ORDER BY title DESC")
    fun getAllMesses(): LiveData<List<MessInfo>>
    // why not use suspend ? because Room does not support LiveData with suspended functions.
    // LiveData already works on a background thread and should be used directly without using coroutines

    @Query("DELETE FROM mess_table")
    suspend fun clearMess()

    @Query("DELETE FROM mess_table WHERE id = :id") //you can use this too, for delete note by id.
    suspend fun deleteMessById(id: Int)
}