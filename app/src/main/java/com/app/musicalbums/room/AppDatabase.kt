package com.app.musicalbums.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.musicalbums.models.Album
import com.app.musicalbums.models.AlbumImage
import com.app.musicalbums.models.Artist
import com.app.musicalbums.models.Track
import com.app.musicalbums.room.dao.AlbumDao

@Database(
    entities = [Artist::class,Album::class,Track::class, AlbumImage::class],
    version = 1
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun getAlbumDao(): AlbumDao
}