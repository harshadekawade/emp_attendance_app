package com.example.myapplication

import DB.DBHelper
import android.content.Intent
import android.text.format.DateFormat
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var FName: EditText
    private lateinit var date: EditText
    private lateinit var pre: RadioButton
    private lateinit var absent: RadioButton
    private lateinit var rb: RadioButton
    private lateinit var rg: RadioGroup
    private lateinit var submit: Button
    private lateinit var clear: Button
    private lateinit var show: Button

    private lateinit var d: Date
    private lateinit var db: DBHelper
    private var status: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        supportActionBar?.hide()

        FName = findViewById(R.id.fname)
        date = findViewById(R.id.date)
        pre = findViewById(R.id.present)
        absent = findViewById(R.id.absent)
        rg = findViewById(R.id.rgroup)
        submit = findViewById(R.id.submit)
        clear = findViewById(R.id.clear)
        show = findViewById(R.id.show)

        db = DBHelper(this)

        // Auto getting current date
        d = Date()
        val s: CharSequence = DateFormat.format("d/MM/yyyy ", d.time)
        date.setText(s)

        clear.setOnClickListener {
            FName.setText("")
            pre.isChecked = false
            absent.isChecked = false
        }

        submit.setOnClickListener {
            val selectId = rg.checkedRadioButtonId
            rb = findViewById(selectId)

            val FNameText = FName.text.toString()
            val dateText = date.text.toString()
            val statusText = rb.text.toString()

            if (FNameText.isEmpty() && dateText.isEmpty() && !pre.isChecked && !absent.isChecked) {
                Toast.makeText(this@MainActivity, "Field is Empty", Toast.LENGTH_SHORT).show()
            } else {
                val checkInsertData = db.insertData(FNameText, dateText, statusText)
                if (checkInsertData) {
                    Toast.makeText(this@MainActivity, "Data Inserted", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@MainActivity, "Data Not Inserted", Toast.LENGTH_SHORT).show()
                }
                FName.setText("")
                pre.isChecked = false
                absent.isChecked = false
            }
        }

        show.setOnClickListener {
            val intent = Intent(this, Showdata::class.java)
            startActivity(intent)
        }
    }
}

