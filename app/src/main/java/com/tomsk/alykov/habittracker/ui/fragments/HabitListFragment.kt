package com.tomsk.alykov.habittracker.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tomsk.alykov.habittracker.R
import com.tomsk.alykov.habittracker.data.models.Habit
import com.tomsk.alykov.habittracker.ui.viewmodels.HabitViewModel
import kotlinx.android.synthetic.main.fragment_habit_list.*

class HabitListFragment : Fragment() {

    private lateinit var habitList: List<Habit>
    private lateinit var habitViewModel: HabitViewModel
    private lateinit var adapter: HabitListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_habit_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = HabitListAdapter()
        rv_habits.adapter = adapter
        rv_habits.layoutManager = LinearLayoutManager(context)

        ////Instantiate and create viewmodel observers
        viewModels()

        fab_add.setOnClickListener {
            findNavController().navigate(R.id.action_habitListFragment_to_createHabitFragment)
        }
        //Show the options menu in this fragment
        setHasOptionsMenu(true)

        swipeToRefresh.setOnRefreshListener {
            adapter.setData(habitList)
            swipeToRefresh.isRefreshing = false
        }

    }
    private fun viewModels() {
        habitViewModel = ViewModelProvider(this).get(HabitViewModel::class.java)
        habitViewModel.getAllHabits.observe(viewLifecycleOwner, Observer {
            adapter.setData(it)
            habitList = it

            if (it.isEmpty()) {
                rv_habits.visibility = View.GONE
                tv_emptyView.visibility = View.VISIBLE
            } else {
                rv_habits.visibility = View.VISIBLE
                tv_emptyView.visibility = View.GONE
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        //super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.nav_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_delete -> habitViewModel.deleteAllHabits()
        }
        return super.onOptionsItemSelected(item)
    }



}