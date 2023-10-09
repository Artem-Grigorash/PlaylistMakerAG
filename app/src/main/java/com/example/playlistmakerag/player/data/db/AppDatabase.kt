package com.example.playlistmakerag.player.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.playlistmakerag.player.data.db.dao.TrackDao
import com.example.playlistmakerag.player.data.db.entity.TrackEntity

@Database(version = 1, entities = [TrackEntity::class])
abstract class AppDatabase : RoomDatabase(){

    abstract fun trackDao(): TrackDao
}