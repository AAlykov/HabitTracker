package com.tomsk.alykov.habittracker.logic.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.tomsk.alykov.habittracker.data.models.Habit

@Dao
interface HabitDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addHabit(habit: Habit)

    @Update
    suspend fun updateHabit(habit: Habit)

    @Delete
    suspend fun deleteHabit(habit: Habit)

    @Query("select * from habit_table order by id")
    fun getAllHabits(): LiveData<List<Habit>>

    @Query("delete from habit_table")
    suspend fun deleteAllHabits()
}