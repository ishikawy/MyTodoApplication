package com.example.mytodoapplication.ui.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.mytodoapplication.data.Task
import com.example.mytodoapplication.databinding.TaskItemBinding

class RecyclerViewAdapter(
        private var items: List<Task>,
        private val viewModel: MainViewModel
) : RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent!!.context)
        val binding = TaskItemBinding.inflate(layoutInflater, parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerViewAdapter.ItemViewHolder, position: Int) {
        val data = items[position]
        holder.binding.task = data
        holder.binding.complete.setOnClickListener {
            viewModel.completeTask(data, holder.binding.complete.isChecked, position)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    /**
     * ViewHolder
     */
    class ItemViewHolder(var binding: TaskItemBinding) : RecyclerView.ViewHolder(binding.root)

}