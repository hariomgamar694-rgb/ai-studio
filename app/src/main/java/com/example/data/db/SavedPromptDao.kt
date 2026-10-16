package com.example.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedPromptDao {
    @Query("SELECT * FROM saved_prompts ORDER BY savedAtTimestamp DESC")
    fun getAllSaved(): Flow<List<SavedPromptEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM saved_prompts WHERE promptId = :promptId)")
    fun isSaved(promptId: String): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePrompt(prompt: SavedPromptEntity)

    @Query("DELETE FROM saved_prompts WHERE promptId = :promptId")
    suspend fun removePrompt(promptId: String)
}
