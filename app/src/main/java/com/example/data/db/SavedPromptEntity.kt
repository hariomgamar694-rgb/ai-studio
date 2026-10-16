package com.example.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_prompts")
data class SavedPromptEntity(
    @PrimaryKey
    val promptId: String,
    val savedAtTimestamp: Long = System.currentTimeMillis()
)
