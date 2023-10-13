package com.example.playlistmakerag.mediateka.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.playlistmakerag.mediateka.data.db.entity.PlaylistEntity

@Dao
interface PlaylistDao {
    @Insert(entity = PlaylistEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylistEntity(track: PlaylistEntity)

    @Query("SELECT * FROM track_table")
    suspend fun getPlaylists(): List<PlaylistEntity>

    @Delete(entity = PlaylistEntity::class)
    suspend fun deletePlaylistEntity(track: PlaylistEntity)

}