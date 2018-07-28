package com.example.mytodoapplication.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
        @PrimaryKey(autoGenerate = true)
        var id: Int = 0,
        var name: String,
        var completed: Boolean = false
)