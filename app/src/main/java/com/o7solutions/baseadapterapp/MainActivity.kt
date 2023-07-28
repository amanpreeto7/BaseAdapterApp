package com.o7solutions.baseadapterapp

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.o7solutions.baseadapterapp.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.Calendar

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
        //studentList.add(StudentDataClass(1, "Nitish"))
        //studentList.add(StudentDataClass(2, "Ameesha"))
        //studentList.add(StudentDataClass(name = "Ajay"))

        binding.listView.setOnItemLongClickListener { parent, view, position, id ->
            var dialog = Dialog(this)
            dialog.setContentView(R.layout.dialog_layout)
            var etName = dialog.findViewById<EditText>(R.id.etName)
            var btnAdd = dialog.findViewById<Button>(R.id.btnAdd)
            btnAdd.setText("Update")
            btnAdd.setOnClickListener {
                if(etName.text.toString().isNullOrEmpty()){
                    etName.error = "Enter your name"
                }else{
                    class updateStudent : AsyncTask<Void, Void, Void>(){
                        override fun doInBackground(vararg params: Void?): Void? {
                            studentDatabase.studentDao().updateStudent(StudentDataClass(name = etName.text.toString(), id = studentList[position].id))
                            dialog.dismiss()
                            return null
                        }

                        override fun onPostExecute(result: Void?) {
                            super.onPostExecute(result)
                            getStudents()
                        }
                    }

                    updateStudent().execute()
                    baseAdapterClass.notifyDataSetChanged()
                }
            }
            dialog.show()
            return@setOnItemLongClickListener true
        }

        binding.listView.setOnItemClickListener{adapter, view, position, id->
            System.out.println("position $position id $id")
           // studentList.removeAt(position)
           // baseAdapterClass.notifyDataSetChanged()

            class deleteStudent : AsyncTask<Void, Void, Void>(){
                override fun doInBackground(vararg params: Void?): Void? {
                    studentDatabase.studentDao().deleteStudent(studentList[position])
                    return null
                }

                override fun onPostExecute(result: Void?) {
                    super.onPostExecute(result)
                    getStudents()
                }
            }
            deleteStudent().execute()

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

                       override fun onPostExecute(result: Void?) {
                           super.onPostExecute(result)
                           getStudents()
                       }
                   }

                    insertStudent().execute()

                    baseAdapterClass.notifyDataSetChanged()
                }
            }
            dialog.show()
        }

        getStudents()

        binding.btnDatePicker.setOnClickListener {
            var datePickerDialog = DatePickerDialog(this)
            datePickerDialog.show()
            datePickerDialog.setOnDateSetListener { view, year, month, dayOfMonth ->
                System.out.println("Date picked $dayOfMonth/$month/$year")
                var sdf = SimpleDateFormat("dd/MMM/yyyy")
                var calendar = Calendar.getInstance()
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DATE, dayOfMonth)
                var changedFormat = sdf.format(calendar.time)
                System.out.println("Formatted date ${changedFormat}")
            }
           // datePickerDialog.updateDate(1990, 4, 12)
            datePickerDialog.datePicker.minDate = Calendar.getInstance().timeInMillis
        }

        binding.btnTimePicker.setOnClickListener {
            var timePicker = TimePickerDialog(this,{_, hours, minutes->
                var simpleDateFormat = SimpleDateFormat("hh:mm")
                var calendar = Calendar.getInstance()
                calendar.set(Calendar.HOUR, hours)
                calendar.set(Calendar.MINUTE, minutes)
                var changedTime = simpleDateFormat.format(calendar.time)
                System.out.println("Calendar time $changedTime")
            }, 4, 30, true)
            timePicker.show()
        }
    }

    fun getStudents(){
        studentList.clear()
        class getData : AsyncTask<Void, Void, Void>(){
            override fun doInBackground(vararg params: Void?): Void? {
                studentList.addAll(studentDatabase.studentDao().getStudentList())
                return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                baseAdapterClass.notifyDataSetChanged()
            }
        }

        getData().execute()
    }
}