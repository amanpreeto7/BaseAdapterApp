package com.o7solutions.baseadapterapp

import android.app.Dialog
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.o7solutions.baseadapterapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var baseAdapterClass: BaseAdapterClass
    lateinit var binding: ActivityMainBinding
    var studentList = mutableListOf<StudentDataClass>()
    lateinit var studentDatabase: StudentDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        studentDatabase = StudentDatabase.getDatabase(this)
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

        binding.fab.setOnClickListener {
            var dialog = Dialog(this)
            dialog.setContentView(R.layout.dialog_layout)
            var etName = dialog.findViewById<EditText>(R.id.etName)
            var btnAdd = dialog.findViewById<Button>(R.id.btnAdd)
            btnAdd.setOnClickListener {
                if(etName.text.toString().isNullOrEmpty()){
                    etName.error = "Enter your name"
                }else{
                   // studentList.add(StudentDataClass(name = etName.text.toString()))
                    class insertStudent : AsyncTask<Void, Void, Void>(){
                       override fun doInBackground(vararg params: Void?): Void? {

                           studentDatabase.studentDao().insertStudent(StudentDataClass(name = etName.text.toString()))
                           dialog.dismiss()
                           return null
                       }
                   }

                    insertStudent().execute()

                    baseAdapterClass.notifyDataSetChanged()
                }
            }
            dialog.show()
        }
    }
}