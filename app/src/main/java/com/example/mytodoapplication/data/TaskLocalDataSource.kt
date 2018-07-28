package com.example.mytodoapplication.data

import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TaskLocalDataSource(private val mTasksDao: TaskDao) : TaskDataSource {

    /**
     * 未完了タスクの取得
     */
    override fun getUncompletedTasks(callback: TaskDataSource.LoadTasksCallback) {
        checkNotNull(callback)
        mTasksDao.getAllUncompletedTask().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { result ->
                    callback.onTasksLoaded(result)
                }
    }

    /**
     * タスクの追加
     */
    override fun saveTask(task: Task, callback: TaskDataSource.AddTaskCallback) {
        checkNotNull(task)
        Completable.fromAction { mTasksDao.insert(task) }
                .subscribeOn(Schedulers.io())
                .subscribe {
                    callback.onTaskAdded()
                }
    }

    /**
     * タスクの完了
     */
    override fun completeTask(task: Task) {
        checkNotNull(task)
        Completable.fromAction { mTasksDao.insert(task) }
                .subscribeOn(Schedulers.io())
                .subscribe()
    }

}