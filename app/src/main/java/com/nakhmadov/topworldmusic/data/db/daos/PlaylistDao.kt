package com.nakhmadov.topworldmusic.data.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.nakhmadov.topworldmusic.data.db.models.ChartItem
import com.nakhmadov.topworldmusic.data.db.models.EditorialItem
import com.nakhmadov.topworldmusic.data.db.models.Playlist
import com.nakhmadov.topworldmusic.data.db.models.PlaylistItem

@Dao
interface PlaylistDao {

    @Query("delete from chart_item where itemType = 'playlist'")
    fun deleteChartPlaylists()

    @Query("delete from editorial_item where itemType = 'playlist'")
    fun deleteEditorialPlaylists()

    @Insert(onConflict = REPLACE)
    fun insertChartPlaylist(chartItem: ChartItem)

    @Insert(onConflict = REPLACE)
    fun insertEditorialPlaylist(editorialItem: EditorialItem)

    @Insert(onConflict = REPLACE)
    fun insertListOfChartItems(list: List<ChartItem>)

    @Insert(onConflict = REPLACE)
    fun insertListOfEditorialItems(list: List<EditorialItem>)

    @Insert(onConflict = REPLACE)
    fun insertPlaylist(playlist: Playlist)

    @Insert(onConflict = REPLACE)
    fun insertListOfPlaylists(list: List<Playlist>)

    @Query("select * from chart_item join playlist on playlistId = itemId where itemType = 'playlist'")
    fun chartPlaylists(): List<Playlist>

    @Query("select * from editorial_item join playlist on playlistId = itemId where itemType = 'playlist'")
    fun editorialPlaylists(): List<Playlist>

    @Insert(onConflict = REPLACE)
    fun insertPlaylistItems(list: List<PlaylistItem>)

    @Query("select * from playlist where playlistId = :playlistId")
    fun getPlaylistById(playlistId: Long): Playlist

}