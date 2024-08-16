package com.example.myapplication

import DB.DBHelper
import android.app.DatePickerDialog
import android.database.Cursor
import android.text.InputType
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.annotation.NonNull
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.util.*

class Showdata : AppCompatActivity() {

    private lateinit var picker: DatePickerDialog
    private lateinit var date: EditText
    private lateinit var btn: Button
    private lateinit var t1: TextView

    private var db: DBHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_showdata)
        // calling the action bar
        val actionBar: ActionBar? = supportActionBar

        // showing the back button in action bar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        date = findViewById(R.id.date)
        btn = findViewById(R.id.btn)
        date.inputType = InputType.TYPE_NULL

        t1 = findViewById(R.id.pre)

        date.setOnClickListener {
            val cldr = Calendar.getInstance()
            val day = cldr.get(Calendar.DAY_OF_MONTH)
            val year = cldr.get(Calendar.YEAR)
            val month = cldr.get(Calendar.MONTH)
            // date picker dialog
            picker = DatePickerDialog(this,
                { view, year1, monthOfYear, dayOfMonth ->
                    val d = "$dayOfMonth/${if ((month + 1) < 10) "0${month + 1}" else (month + 1)}/$year1"
                    date.setText(d)
                    Toast.makeText(applicationContext, "date is: $d", Toast.LENGTH_SHORT).show()
                }, year, month, day)
            picker.show()
        }

        db = DBHelper(this)
        btn.setOnClickListener {
            val s = date.text.toString()
            val result: Cursor = db!!.getAllDetail("$s ")

            while (result.moveToNext()) {
                t1.append("First Name: ${result.getString(1)}\n" +
                        "Date: ${result.getString(2)}\n" +
                        "Status: ${result.getString(3)}\n\n")
            }
        }
    }

    override fun onOptionsItemSelected(@NonNull item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // todo: goto back activity from here
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}