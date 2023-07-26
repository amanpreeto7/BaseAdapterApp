package com.o7solutions.baseadapterapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @Author: 017
 * @Date: 21/07/23
 * @Time: 1:57 pm
 */
@Entity
data class StudentDataClass(
    @PrimaryKey(autoGenerate = true)
    var id : Long = 0L,

    @ColumnInfo
    var name : String ?= null
)
