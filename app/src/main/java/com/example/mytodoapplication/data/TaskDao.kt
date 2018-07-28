package com.example.mytodoapplication.data

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import io.reactivex.Maybe

@Dao
interface TaskDao {

    /**
     * 全取得
     */
    @Query("SELECT * FROM tasks")
    fun getAll(): List<Task>

    /**
     * 未完了のタスクのみ取得
     */
    @Query("SELECT * FROM tasks where completed = 0")
    fun getAllUncompletedTask(): Maybe<List<Task>>

    /**
     * 完了済みのタスクのみ取得
     */
    @Query("SELECT * FROM tasks where completed = 1")
    fun getAllCompletedTask(): List<Task>

    /**
     * タスクの追加
     */
    @Insert(onConflict = REPLACE)
    fun insert(task: Task)

    /**
     * タスクの完了状態の変更
     */
    @Update
    fun completed(task: Task)

    /**
     * タスクの削除
     */
    @Delete
    fun delete(task: Task)

}