package com.tomsk.alykov.habittracker.ui.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.tomsk.alykov.habittracker.R
import com.tomsk.alykov.habittracker.data.models.Habit
import com.tomsk.alykov.habittracker.ui.viewmodels.HabitViewModel
import com.tomsk.alykov.habittracker.utils.Calculation
import kotlinx.android.synthetic.main.fragment_create_habit.*
import kotlinx.android.synthetic.main.fragment_create_habit.view.*
import java.util.Calendar
import kotlin.math.min

class CreateHabitFragment : Fragment(), TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_create_habit, container, false)
        habitViewModel = ViewModelProvider(this).get(HabitViewModel::class.java)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btn_confirm.setOnClickListener {
            addHabitToDB()
        }
        pickDateAndTime()
        drawableSelectedF()
    }

    private fun addHabitToDB() {
        title = et_habitTitle.text.toString()
        desciption = et_habitDescription.text.toString()
        timeStamp = "$cleanDate $cleanTime"

        if (!(title.isEmpty() || desciption.isEmpty() || timeStamp.isEmpty() || drawableSelected == 0)) {
            val habit = Habit(0, title, desciption, timeStamp, drawableSelected)
            habitViewModel.addHabit(habit)
            Toast.makeText(context, "Habit created successfully!", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_createHabitFragment_to_habitListFragment)
        } else {
            Toast.makeText(context, "Fill all fields!", Toast.LENGTH_LONG).show()
        }

    }


    private fun drawableSelectedF() {
        iv_fastFoodSelected.setOnClickListener {
            iv_fastFoodSelected.isSelected = !iv_fastFoodSelected.isSelected
            drawableSelected = R.drawable.ic_fastfood_color
            iv_smokingSelected.isSelected = false
            iv_teaSelected.isSelected = false
        }
        iv_smokingSelected.setOnClickListener {
            iv_smokingSelected.isSelected = !iv_smokingSelected.isSelected
            drawableSelected = R.drawable.ic_smoke_color
            iv_teaSelected.isSelected = false
            iv_fastFoodSelected.isSelected = false
        }
        iv_teaSelected.setOnClickListener {
            iv_teaSelected.isSelected = !iv_teaSelected.isSelected
            drawableSelected = R.drawable.ic_coffee_color
            iv_smokingSelected.isSelected = false
            iv_fastFoodSelected.isSelected = false
        }
    }

    private fun pickDateAndTime() {
        btn_pickDate.setOnClickListener {
            getDateCalendar()
            DatePickerDialog(requireContext(), this, year, month, day).show()
        }

        btn_pickTime.setOnClickListener {
            getTimeCalendar()
            TimePickerDialog(context, this, hour, minute, true).show()
        }

    }

    //get the time set
    override fun onTimeSet(TimePicker: TimePicker?, p1: Int, p2: Int) {
        Log.d("Fragment", "Time: $p1:$p2")
        cleanTime = Calculation.cleanTime(p1, p2)
        tv_timeSelected.text = "Time: $cleanTime"
    }

    //get the date set
    override fun onDateSet(p0: DatePicker?, yearX: Int, monthX: Int, dayX: Int) {
        cleanDate = Calculation.cleanDate(dayX, monthX, yearX)
        tv_dateSelected.text = "Date: $cleanDate"
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


}