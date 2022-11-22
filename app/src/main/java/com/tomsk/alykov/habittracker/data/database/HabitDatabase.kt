package com.tomsk.alykov.habittracker.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tomsk.alykov.habittracker.data.models.Habit
import com.tomsk.alykov.habittracker.logic.dao.HabitDao

@Database(entities = [Habit::class], version = 1)
abstract class HabitDatabase: RoomDatabase() {
    abstract fun habitDao(): HabitDao
    companion object {
        @Volatile //это означает как только мы получим к нему (экземпляру) доступ он станет виден для всех потоков
        private var INSTANCE: HabitDatabase? =null //Создаем экзмпляр БД

        fun getDatabase(context: Context): HabitDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HabitDatabase::class.java,
                    "habit_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}