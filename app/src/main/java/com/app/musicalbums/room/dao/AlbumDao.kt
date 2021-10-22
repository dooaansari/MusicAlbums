package com.app.musicalbums.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.musicalbums.models.Album
import com.app.musicalbums.models.Artist
import com.app.musicalbums.models.Track
import com.app.musicalbums.room.entities.AlbumEntity
import com.app.musicalbums.room.entities.AlbumWithTracks
import com.app.musicalbums.room.entities.ArtistEntity
import com.app.musicalbums.room.entities.TrackEntity

@Dao
abstract class AlbumDao {

    @Query("SELECT * FROM album where id=:id")
    abstract suspend fun getAlbumDetails(id: Int): List<AlbumWithTracks>

    @Query("SELECT * FROM album")
    abstract suspend fun getAllAlbums(): List<Album>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertArtist(artist: Artist): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAlbum(artist: Album): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertTracks(tracks: List<Track>):List<Long>

    suspend fun insertAlbumsWithTracks(artist: Artist, album: Album, tracks: List<Track>){
        val artistId = insertArtist(artist)
        album.artistId= artistId

        val albumId = insertAlbum(album)

        tracks.forEach {
            it.albumId = albumId
        }

        insertTracks(tracks)
    }



//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    suspend fun insert(word: Word)
//
//    @Query("DELETE FROM word_table")
//    suspend fun deleteAll()
}