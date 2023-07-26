package com.o7solutions.baseadapterapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * @Author: 017
 * @Date: 26/07/23
 * @Time: 1:42 pm
 */
@Database(version = 1, entities = [StudentDataClass::class])
abstract class StudentDatabase : RoomDatabase() {
    abstract fun studentDao() : StudentDao

    companion object{
        var studentDatabase : StudentDatabase ?= null
        fun getDatabase (context: Context) : StudentDatabase{
            if(studentDatabase == null) {
                studentDatabase = Room.databaseBuilder(
                    context,
                    StudentDatabase::class.java,
                    "Student Database"
                ).build()
            }
          return studentDatabase!!
        }
    }
}