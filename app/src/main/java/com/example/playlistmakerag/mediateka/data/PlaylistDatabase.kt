package com.example.playlistmakerag.mediateka.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.playlistmakerag.mediateka.data.db.entity.PlaylistEntity
import com.example.playlistmakerag.player.domain.PlayerInterface

@Database(version = 1, entities = [PlaylistEntity::class])
abstract class PlaylistDatabase : RoomDatabase(){

    abstract fun playlistDao(): PlayerInterface
}