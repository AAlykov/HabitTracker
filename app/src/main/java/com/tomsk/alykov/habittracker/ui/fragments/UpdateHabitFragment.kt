package com.tomsk.alykov.habittracker.ui.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.tomsk.alykov.habittracker.R
import com.tomsk.alykov.habittracker.data.models.Habit
import com.tomsk.alykov.habittracker.ui.viewmodels.HabitViewModel
import com.tomsk.alykov.habittracker.utils.Calculation
import kotlinx.android.synthetic.main.fragment_create_habit.*
import kotlinx.android.synthetic.main.fragment_update_habit.*
import java.util.*

class UpdateHabitFragment : Fragment(), TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener  {

    private var title = ""
    private var desciption = ""
    private var drawableSelected = 0
    private var timeStamp = ""

    private var day = 0
    private var month = 0
    private var year = 0
    private var hour = 0
    private var minute = 0

    private var cleanDate = ""
    private var cleanTime = ""

    private lateinit var habitViewModel: HabitViewModel
    private val args by navArgs<UpdateHabitFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        habitViewModel = ViewModelProvider(this).get(HabitViewModel::class.java)
        return inflater.inflate(R.layout.fragment_update_habit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        et_habitTitle_update.setText(args.selectedHabit.habit_title)
        et_habitDescription_update.setText(args.selectedHabit.habit_desciption)
        tv_dateSelected_update.text = args.selectedHabit.habit_startTime
        pickDateAndTime()
        drawableSelectedF()
        btn_confirm_update.setOnClickListener {
            updateHabit()
        }
        setHasOptionsMenu(true)
    }

    private fun updateHabit() {
        title = et_habitTitle_update.text.toString()
        desciption = et_habitDescription_update.text.toString()
        timeStamp = "$cleanDate $cleanTime"
        if (!(title.isEmpty() || desciption.isEmpty() || timeStamp.isEmpty() || drawableSelected == 0)) {
            val habit = Habit(args.selectedHabit.id, title, desciption, timeStamp, drawableSelected)
            habitViewModel.updateHabit(habit)
            Toast.makeText(context, "Habit updated successfully!", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_updateHabitFragment_to_habitListFragment)
        } else {
            Toast.makeText(context, "Fill all fields!", Toast.LENGTH_LONG).show()
        }
    }



    private fun drawableSelectedF() {
        iv_fastFoodSelected_update.setOnClickListener {
            iv_fastFoodSelected_update.isSelected = !iv_fastFoodSelected_update.isSelected
            drawableSelected = R.drawable.ic_fastfood_color
            iv_smokingSelected_update.isSelected = false
            iv_teaSelected_update.isSelected = false
        }
        iv_smokingSelected_update.setOnClickListener {
            iv_smokingSelected_update.isSelected = !iv_smokingSelected_update.isSelected
            drawableSelected = R.drawable.ic_smoke_color
            iv_teaSelected_update.isSelected = false
            iv_fastFoodSelected_update.isSelected = false
        }
        iv_teaSelected_update.setOnClickListener {
            iv_teaSelected_update.isSelected = !iv_teaSelected_update.isSelected
            drawableSelected = R.drawable.ic_coffee_color
            iv_smokingSelected_update.isSelected = false
            iv_fastFoodSelected_update.isSelected = false
        }
    }

    private fun pickDateAndTime() {
        btn_pickDate_update.setOnClickListener {
            getDateCalendar()
            DatePickerDialog(requireContext(), this, year, month, day).show()
        }

        btn_pickTime_update.setOnClickListener {
            getTimeCalendar()
            TimePickerDialog(context, this, hour, minute, true).show()
        }

    }

    //get the current time
    private fun getTimeCalendar() {
        val cal = Calendar.getInstance()
        hour = cal.get(Calendar.HOUR_OF_DAY)
        minute = cal.get(Calendar.MINUTE)
    }

    //get the current date
    private fun getDateCalendar() {
        val cal = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.single_item_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_delete -> deleteHabit(args.selectedHabit)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteHabit(habit: Habit) {
        habitViewModel.deleteHabit(habit)
        Toast.makeText(context, "Habit successfully deleted", Toast.LENGTH_LONG).show()
        findNavController().navigate(R.id.action_updateHabitFragment_to_habitListFragment)
    }

    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        Log.d("Fragment", "Time: $p1:$p2")
        cleanTime = Calculation.cleanTime(p1, p2)
        tv_timeSelected_update.text = "Time: $cleanTime"
    }

    override fun onDateSet(p0: DatePicker?, yearX: Int, monthX: Int, dayX: Int) {
        cleanDate = Calculation.cleanDate(dayX, monthX, yearX)
        tv_dateSelected_update.text = "Date: $cleanDate"
    }

}