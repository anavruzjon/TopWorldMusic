package com.nakhmadov.topworldmusic.data.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.*
import androidx.room.Query
import com.nakhmadov.topworldmusic.data.db.models.Genre
import com.nakhmadov.topworldmusic.data.db.models.GenreItem

@Dao
abstract class GenreDao {

    @Query("select count(genreId) from genre")
    abstract fun count(): Int

    @Insert(onConflict = REPLACE)
    abstract fun insertListOfGenres(list: List<Genre>)

    @Query("select * from genre")
    abstract fun genres(): List<Genre>

    @Query("select * from genre where genreId = :genreId")
    abstract fun genreById(genreId: Long): Genre?

    fun genresToInsert(list: List<Genre>) {
        val insertGenresList = mutableListOf<Genre>()
        list.forEach {
            val id = it.genreId
            val genre = genreById(id)
            if (genre == null)
                insertGenresList.add(it)
            else {
                if (genre.pictureBigUrl.isNullOrEmpty())
                    insertGenresList.add(it)
            }
        }
        insertListOfGenres(insertGenresList)
    }

    @Insert(onConflict = REPLACE)
    abstract fun insertListOfGenreItems(list: List<GenreItem>)

}