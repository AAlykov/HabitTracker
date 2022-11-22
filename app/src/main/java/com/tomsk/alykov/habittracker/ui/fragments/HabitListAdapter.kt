package com.tomsk.alykov.habittracker.ui.fragments

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.tomsk.alykov.habittracker.R
import com.tomsk.alykov.habittracker.data.models.Habit
import com.tomsk.alykov.habittracker.utils.Calculation
import kotlinx.android.synthetic.main.recycler_habit_item.view.*

class HabitListAdapter: RecyclerView.Adapter<HabitListAdapter.MyViewHolder>() {
    var habitList = emptyList<Habit>()

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.cv_cardView.setOnClickListener {
                val position = adapterPosition
                Log.d("HabitsListAdapter", "Item clicked at: $position")

                val action = HabitListFragmentDirections.actionHabitListFragmentToUpdateHabitFragment((habitList[position]),habitList[position].habit_startTime )
                itemView.findNavController().navigate(action)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder((LayoutInflater.from(parent.context).inflate(R.layout.recycler_habit_item, parent, false)))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentHabit = habitList[position]
        holder.itemView.iv_habit_icon.setImageResource(currentHabit.imageId)
        holder.itemView.tv_item_title.text = currentHabit.habit_title
        holder.itemView.tv_item_description.text = currentHabit.habit_desciption
        holder.itemView.tv_timeElapsed.text = Calculation.calculateTimeBetweenDates(currentHabit.habit_startTime)
        holder.itemView.tv_item_createdTimeStamp.text = "Since: ${currentHabit.habit_startTime}"
        holder.itemView.tv_item_title.text = "${currentHabit.habit_title}"


    }

    override fun getItemCount(): Int {
        return habitList.size
    }

    fun setData(habit: List<Habit>) {
        this.habitList = habit
        notifyDataSetChanged()
    }

}