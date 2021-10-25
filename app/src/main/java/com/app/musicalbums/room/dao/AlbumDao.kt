package com.app.musicalbums.room.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.musicalbums.enums.ImageSize
import com.app.musicalbums.models.Album
import com.app.musicalbums.models.AlbumImage
import com.app.musicalbums.models.Artist
import com.app.musicalbums.models.Track
import com.app.musicalbums.room.entities.AlbumWithTracks

@Dao
abstract class AlbumDao {

    @Query("SELECT * FROM album where name=:name")
    abstract suspend fun getAlbumDetails(name: String): List<AlbumWithTracks>

    @Query("SELECT * FROM album")
    abstract fun getAllAlbums(): PagingSource<Int,AlbumWithTracks>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertArtist(artist: Artist): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAlbum(artist: Album): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAlbumImage(albumImage: AlbumImage): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertTracks(tracks: List<Track>):List<Long>

    suspend fun insertAlbumsWithTracks(artist: Artist, album: Album, tracks: List<Track>){
        insertArtist(artist)
        album.mbid = artist.mbid
        insertAlbum(album)
        val mediumImage = album.images?.find { it.size ==ImageSize.medium.name }
        val largeImage = album.images?.find { it.size == ImageSize.large.name }
        mediumImage?.text?.let {
            val albumImageMedium = AlbumImage(albumName = album.name, text = it, size = mediumImage.size)
            insertAlbumImage(albumImageMedium)
        }

        largeImage?.text?.let {
            val albumImageLarge = AlbumImage(albumName = album.name, text = it, size = largeImage.size)
            insertAlbumImage(albumImageLarge)
        }
        val filteredTracks = tracks.filter { it.name.isNotBlank() }
        filteredTracks .forEach {
            it.albumName = album.name
        }

        insertTracks(filteredTracks)
    }

    @Query("DELETE FROM album where name=:name")
    abstract suspend fun deleteAlbum(name: String)

//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    suspend fun insert(word: Word)
//

}