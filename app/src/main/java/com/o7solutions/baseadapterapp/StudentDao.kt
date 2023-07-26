package com.o7solutions.baseadapterapp

import androidx.room.Dao
import androidx.room.Insert

/**
 * @Author: 017
 * @Date: 26/07/23
 * @Time: 1:44 pm
 */
@Dao
interface StudentDao {

    @Insert
    fun insertStudent(studentDataClass: StudentDataClass)
}