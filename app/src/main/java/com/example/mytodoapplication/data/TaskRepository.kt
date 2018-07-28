package com.example.mytodoapplication.data

class TaskRepository(private val taskLocalDataSource: TaskDataSource) : TaskDataSource {
    /**
     * 未完了タスクの一覧取得
     */
    override fun getUncompletedTasks(callback: TaskDataSource.LoadTasksCallback) {
        checkNotNull(callback)
        taskLocalDataSource.getUncompletedTasks(object : TaskDataSource.LoadTasksCallback {
            override fun onTasksLoaded(tasks: List<Task>) {
                callback.onTasksLoaded(tasks)
            }
        })
    }

    /**
     * タスクの追加
     */
    override fun saveTask(task: Task, callback: TaskDataSource.AddTaskCallback) {
        taskLocalDataSource.saveTask(task, object : TaskDataSource.AddTaskCallback {
            override fun onTaskAdded() {
                callback.onTaskAdded()
            }
        })
    }

    /**
     * タスクの完了
     */
    override fun completeTask(task: Task) {
        taskLocalDataSource.completeTask(task)
    }

}