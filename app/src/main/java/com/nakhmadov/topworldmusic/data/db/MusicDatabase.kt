package com.nakhmadov.topworldmusic.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nakhmadov.topworldmusic.data.db.daos.*
import com.nakhmadov.topworldmusic.data.db.models.*

@Database(
    entities = [AuthToken::class, Album::class, Artist::class, Genre::class, GenreItem::class,
        Track::class, Playlist::class, ChartItem::class, EditorialItem::class, PlaylistItem::class],
    version = 1
)
abstract class MusicDatabase : RoomDatabase() {
    abstract val authTokenDao: AuthTokenDao
    abstract val albumDao: AlbumDao
    abstract val artistDao: ArtistDao
    abstract fun genreDao(): GenreDao
    //abstract val genreDao: GenreDao
    abstract val playlistDao: PlaylistDao
    abstract val trackDao: TrackDao
}

private lateinit var INSTANCE: MusicDatabase

fun getDatabase(context: Context): MusicDatabase {

    synchronized(MusicDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                MusicDatabase::class.java,
                "MusicDatabase"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
        return INSTANCE
    }
}