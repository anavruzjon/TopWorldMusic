package com.nakhmadov.topworldmusic.data.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nakhmadov.topworldmusic.data.db.models.Album
import com.nakhmadov.topworldmusic.data.db.models.ChartItem
import com.nakhmadov.topworldmusic.data.db.models.EditorialItem
import com.nakhmadov.topworldmusic.data.db.models.Track

@Dao
interface AlbumDao {

    @Query("delete from chart_item where itemType = 'album'")
    fun deleteChartAlbums()

    @Query("delete from editorial_item where itemType = 'album_release'")
    fun deleteEditorialReleaseAlbums()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertChartAlbum(chartItem: ChartItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEditorialAlbum(editorialItem: EditorialItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListOfChartItems(list: List<ChartItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListOfEditorialItems(list: List<EditorialItem>)

    @Query("select * from chart_item join album on albumId = itemId where itemType = 'album'")
    fun chartAlbums(): List<Album>

    @Query("select * from editorial_item join album on albumId = itemId where itemType = 'album_release'")
    fun editorialReleaseAlbums(): List<Album>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAlbum(album: Album)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListOfAlbums(list: List<Album>)

    @Query("select * from album where albumId = :albumId")
    fun getAlbumById(albumId: Long): Album

    @Query("select * from genre_item join album on itemId = albumId where itemType = 'album' and genreId = :genreId")
    fun genreAlbums(genreId: Long): List<Album>


}