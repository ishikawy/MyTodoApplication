package com.example.mytodoapplication.ui.main

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableArrayList
import android.databinding.ObservableField
import android.databinding.ObservableList
import com.example.mytodoapplication.data.Task
import com.example.mytodoapplication.data.TaskDataSource
import com.example.mytodoapplication.data.TaskRepository

open class MainViewModel(private val taskRepository: TaskRepository) : ViewModel() {

    val items: ObservableList<Task> = ObservableArrayList()

    val inputName = ObservableField<String>()

    /**
     * タスク一覧表示（未完了のみ）
     */
    fun loadTasks() {
        taskRepository.getUncompletedTasks(object : TaskDataSource.LoadTasksCallback {
            override fun onTasksLoaded(tasks: List<Task>) {
                items.clear()
                items.addAll(tasks)
            }
        })
    }

    /**
     * 新規タスクの追加
     */
    fun addNewTask() {
        var newTaskName = inputName.get()
        if (newTaskName == null || newTaskName.isEmpty()) {
            // Emptyなので保存しない
        } else {
            val newTask = Task(name = newTaskName)
            taskRepository.saveTask(newTask, object : TaskDataSource.AddTaskCallback {
                override fun onTaskAdded() {
                    loadTasks()
                }
            })
            inputName.set("")
        }
    }

    /**
     * タスクの完了
     */
    fun completeTask(task: Task, checked: Boolean, position: Int) {
        task.completed = checked
        taskRepository.completeTask(task)
        items.removeAt(position)
    }

}
