package com.example.playlistmakerag.player.data.db.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.playlistmakerag.player.data.db.entity.TrackEntity

interface TrackDao {
    @Insert(entity = TrackEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertTrackEntity(track: TrackEntity)

    @Query("SELECT * FROM track_table")
    suspend fun getTracks(): List<TrackEntity>

    @Delete(entity = TrackEntity::class)
    fun deleteTrackEntity(track: TrackEntity)

    @Query("SELECT trackId FROM track_table")
    suspend fun getId(): List<String>
}