package com.nakhmadov.topworldmusic.data.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.nakhmadov.topworldmusic.data.db.models.AuthToken

@Dao
interface AuthTokenDao {

    @Query("select * from auth_token limit 1")
    fun token(): AuthToken?

    @Insert(onConflict = REPLACE)
    fun insertToken(token: AuthToken)

    @Query("delete from auth_token")
    fun clear()

}