package com.nakhmadov.topworldmusic.data.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nakhmadov.topworldmusic.data.db.models.Artist
import com.nakhmadov.topworldmusic.data.db.models.ChartItem
import com.nakhmadov.topworldmusic.data.db.models.EditorialItem

@Dao
interface ArtistDao {

    @Query("delete from chart_item where itemType = 'artist'")
    fun deleteChartArtists()

    @Query("delete from editorial_item where itemType = 'artist'")
    fun deleteEditorialArtists()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertChartArtist(chartItem: ChartItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEditorialArtist(editorialItem: EditorialItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListOfChartItems(list: List<ChartItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListOfEditorialItems(list: List<EditorialItem>)

    @Query("select * from chart_item join artist on artistId = itemId where itemType = 'artist'")
    fun chartArtists(): List<Artist>

    @Query("select * from editorial_item join artist on artistId = itemId where itemType = 'artist'")
    fun editorialArtists(): List<Artist>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArtist(artist: Artist)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListOfArtists(list: List<Artist>)


}