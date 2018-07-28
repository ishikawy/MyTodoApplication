package com.example.mytodoapplication

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.mytodoapplication.data.AppDatabase
import com.example.mytodoapplication.data.TaskLocalDataSource
import com.example.mytodoapplication.data.TaskRepository
import com.example.mytodoapplication.ui.main.MainViewModel


class ViewModelFactory(private val application: Application) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val database = AppDatabase.getInstance(application)
        return MainViewModel(TaskRepository(TaskLocalDataSource(database.taskDao()))) as T
    }

}
