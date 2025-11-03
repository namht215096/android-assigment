package com.example.myapplication
import android.view.ViewGroup
import android.widget.*
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var calendarView: CalendarView
    private lateinit var birthdayEditText: EditText
    private lateinit var selectButton: Button
    private lateinit var registerButton: Button

    private lateinit var firstNameEdit: EditText
    private lateinit var lastNameEdit: EditText
    private lateinit var addressEdit: EditText
    private lateinit var emailEdit: EditText
    private lateinit var agreeCheckBox: CheckBox

    private var isCalendarVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        calendarView = CalendarView(this)
        (findViewById<View>(R.id.main) as ViewGroup).addView(calendarView)
        calendarView.visibility = View.GONE

        birthdayEditText = findViewById(R.id.editTextText3)
        selectButton = findViewById(R.id.button)
        registerButton = findViewById(R.id.button2)

        firstNameEdit = findViewById(R.id.editTextText)
        lastNameEdit = findViewById(R.id.editTextText2)
        addressEdit = findViewById(R.id.editTextText4)
        emailEdit = findViewById(R.id.editTextText5)
        agreeCheckBox = findViewById(R.id.checkBox)


        selectButton.setOnClickListener {
            isCalendarVisible = !isCalendarVisible
            calendarView.visibility = if (isCalendarVisible) View.VISIBLE else View.GONE
        }

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val dateStr = "$dayOfMonth/${month + 1}/$year"
            birthdayEditText.setText(dateStr)
            calendarView.visibility = View.GONE
            isCalendarVisible = false
        }


        registerButton.setOnClickListener {
            checkFields()
        }
    }

    private fun checkFields() {
        val fields = listOf(firstNameEdit, lastNameEdit, birthdayEditText, addressEdit, emailEdit)
        var allFilled = true

        for (field in fields) {
            if (field.text.toString().trim().isEmpty()) {
                field.setBackgroundColor(Color.parseColor("#FFCCCC"))
                allFilled = false
            } else {
                field.setBackgroundColor(Color.WHITE)
            }
        }

        if (!agreeCheckBox.isChecked) {
            Toast.makeText(this, "You must agree to the terms", Toast.LENGTH_SHORT).show()
            allFilled = false
        }

        if (allFilled) {
            Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
        }
    }
}
