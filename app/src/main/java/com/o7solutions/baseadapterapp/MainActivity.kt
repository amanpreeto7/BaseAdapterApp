package com.o7solutions.baseadapterapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.o7solutions.baseadapterapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var baseAdapterClass: BaseAdapterClass
    lateinit var binding: ActivityMainBinding
    var studentList = mutableListOf<StudentDataClass>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        baseAdapterClass = BaseAdapterClass(studentList)
        binding.listView.adapter = baseAdapterClass
        studentList.add(StudentDataClass(1, "Nitish"))
        studentList.add(StudentDataClass(2, "Ameesha"))
        studentList.add(StudentDataClass(name = "Ajay"))
        binding.listView.setOnItemClickListener{adapter, view, position, id->
            System.out.println("position $position id $id")
            studentList.removeAt(position)
            baseAdapterClass.notifyDataSetChanged()

        }
    }
}