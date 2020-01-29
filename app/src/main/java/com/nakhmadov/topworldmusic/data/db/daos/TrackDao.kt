package com.nakhmadov.topworldmusic.data.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.nakhmadov.topworldmusic.data.db.models.ChartItem
import com.nakhmadov.topworldmusic.data.db.models.Track

@Dao
interface TrackDao {

    @Query("delete from chart_item where itemType = 'track'")
    fun deleteChartTracks()

    @Insert(onConflict = REPLACE)
    fun insertChartTrack(chartItem: ChartItem)

    @Insert(onConflict = REPLACE)
    fun insertListOfChartItems(list: List<ChartItem>)

    @Insert(onConflict = REPLACE)
    fun insertTrack(track: Track)

    @Insert(onConflict = REPLACE)
    fun insertListOfTracks(list: List<Track>)

    @Query("select * from chart_item join track on trackId = itemId where itemType = 'track'")
    fun chartTracks(): List<Track>

    @Query("select * from track where albumId = :albumId")
    fun albumTracks(albumId: Long): List<Track>

    @Query("select track.trackId, title, shortTitle, linkToDeezer, durationSeconds, previewMp3, albumId, artistId, releaseDate, albumImage, artistName  from playlist_item join track on track.trackId = playlist_item.trackId where playlist_item.playlistId = :playlistId")
    fun playlistTracks(playlistId: Long): List<Track>

}