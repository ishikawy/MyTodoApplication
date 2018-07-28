package com.example.mytodoapplication.ui.main

import android.app.Activity
import android.app.Application
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.BottomSheetBehavior
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.example.mytodoapplication.ViewModelFactory
import com.example.mytodoapplication.data.Task
import com.example.mytodoapplication.databinding.MainFragmentBinding

class MainFragment : Fragment() {

    private lateinit var application: Application
    private lateinit var binding: MainFragmentBinding
    private lateinit var adapter: RecyclerViewAdapter

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        application = activity!!.application
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, ViewModelFactory(application)).get(MainViewModel::class.java)
        binding.viewmodel = viewModel
        viewModel.items.addOnListChangedCallback(object : ObservableList.OnListChangedCallback<ObservableArrayList<Task>>() {
            override fun onChanged(sender: ObservableArrayList<Task>?) {}
            override fun onItemRangeMoved(sender: ObservableArrayList<Task>?, fromPosition: Int, toPosition: Int, itemCount: Int) {}
            override fun onItemRangeChanged(sender: ObservableArrayList<Task>?, positionStart: Int, itemCount: Int) {}

            override fun onItemRangeInserted(sender: ObservableArrayList<Task>?, positionStart: Int, itemCount: Int) {
                adapter.notifyDataSetChanged()
            }

            override fun onItemRangeRemoved(sender: ObservableArrayList<Task>?, positionStart: Int, itemCount: Int) {
                adapter.notifyItemRemoved(positionStart)
                adapter.notifyItemRangeChanged(0, itemCount)
            }
        })

        var activity = activity ?: return

        setupBottomSheet(activity)

        setupRecyclerView(activity)
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadTasks()
    }

    /**
     * ボトムシートのセット
     */
    private fun setupBottomSheet(activity: Activity) {
        val bottomSheet = binding.bottomSheet
        val behavior = BottomSheetBehavior.from(bottomSheet)
        behavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        binding.fab.visibility = View.GONE
                        binding.etEditTaskName.requestFocus()
                        Handler().post {
                            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                            imm.showSoftInput(binding.etEditTaskName, InputMethodManager.SHOW_IMPLICIT)
                        }
                    }
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        binding.fab.visibility = View.VISIBLE
                        Handler().post {
                            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                            imm.hideSoftInputFromWindow(view!!.windowToken, InputMethodManager.HIDE_IMPLICIT_ONLY)
                        }
                    }
                    BottomSheetBehavior.STATE_SETTLING -> {
                        binding.fab.visibility = View.GONE
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                //
            }
        })
        behavior.state = BottomSheetBehavior.STATE_HIDDEN

        // fabのクリック
        binding.fab.setOnClickListener {
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
        // 追加ボタンのクリック
        binding.btnEditSave.setOnClickListener {
            viewModel.addNewTask()
        }
        // キャンセルボタンのクリック
        binding.btnEditCancel.setOnClickListener {
            binding.etEditTaskName.setText("")
            behavior.state = BottomSheetBehavior.STATE_HIDDEN
        }
    }

    /**
     * RecyclerViewのセット
     */
    private fun setupRecyclerView(activity: Activity) {
        val recyclerView: RecyclerView = binding.recyclerView
        adapter = RecyclerViewAdapter(viewModel.items, viewModel)
        val linearLayoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

}
