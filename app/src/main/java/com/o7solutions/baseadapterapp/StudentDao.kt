package com.o7solutions.baseadapterapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

/**
 * @Author: 017
 * @Date: 26/07/23
 * @Time: 1:44 pm
 */
@Dao
interface StudentDao {

    @Insert
    fun insertStudent(studentDataClass: StudentDataClass)

    @Query("SELECT * FROM StudentDataClass")
    fun getStudentList() : List<StudentDataClass>

    @Delete
    fun deleteStudent(studentDataClass: StudentDataClass)
}