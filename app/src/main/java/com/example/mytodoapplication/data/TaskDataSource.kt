package com.example.mytodoapplication.data

import android.support.annotation.NonNull

interface TaskDataSource {

    interface LoadTasksCallback {
        fun onTasksLoaded(tasks: List<Task>)
    }

    interface AddTaskCallback {
        fun onTaskAdded()
    }

    /**
     * 未完了タスク一覧の取得
     */
    fun getUncompletedTasks(@NonNull callback: LoadTasksCallback)

    /**
     * タスクの追加
     */
    fun saveTask(task: Task, callback: AddTaskCallback)

    /**
     * タスクの完了
     */
    fun completeTask(task: Task)
}