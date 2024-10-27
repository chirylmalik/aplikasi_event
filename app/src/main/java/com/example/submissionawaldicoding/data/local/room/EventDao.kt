package com.example.submissionawaldicoding.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.submissionawaldicoding.data.local.entity.EventEntity

@Dao
interface EventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(event: EventEntity)

    @Query("DELETE FROM event_entity WHERE id = :eventId")
    suspend fun deleteFavorite(eventId: Int)

    @Query("SELECT * FROM event_entity")
    suspend fun getAllFavorites(): List<EventEntity>
}