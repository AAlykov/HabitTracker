package com.tomsk.alykov.habittracker.logic.repository

import androidx.lifecycle.LiveData
import com.tomsk.alykov.habittracker.data.models.Habit
import com.tomsk.alykov.habittracker.logic.dao.HabitDao

class HabitRepository(private val habitDao: HabitDao) {
    var getAllHabits: LiveData<List<Habit>> = habitDao.getAllHabits()

    /*fun f1() {
        getAllHabits = habitDao.getAllHabits()
    }*/

    suspend fun addHabit(habit: Habit) {
        habitDao.addHabit(habit)
    }

    suspend fun updateHabit(habit: Habit) {
        habitDao.updateHabit(habit)
    }

    suspend fun deleteHabit(habit: Habit) {
        habitDao.deleteHabit(habit)
    }

    suspend fun deleteAllHabits() {
        habitDao.deleteAllHabits()
    }

}