package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner

class MainActivity : AppCompatActivity() {

    private lateinit var spinnerFrom: Spinner
    private lateinit var spinnerTo: Spinner
    private lateinit var editFrom: EditText
    private lateinit var editTo: EditText

    // Tỷ giá theo USD
    private val rates = mapOf(
        "USD" to 1.0,
        "EUR" to 1.07,
        "GBP" to 1.24,
        "JPY" to 0.0067,
        "KRW" to 0.00077,
        "CNY" to 0.14,
        "VND" to 0.000039,
        "AUD" to 0.66,
        "CAD" to 0.73,
        "SGD" to 0.74
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spinnerFrom = findViewById(R.id.spinnerFrom)
        spinnerTo = findViewById(R.id.spinnerTo)
        editFrom = findViewById(R.id.editFrom)
        editTo = findViewById(R.id.editTo)

        val currencyList = rates.keys.toList()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, currencyList)

        spinnerFrom.adapter = adapter
        spinnerTo.adapter = adapter

        spinnerFrom.setSelection(0)
        spinnerTo.setSelection(1)

        editFrom.addTextChangedListener(createWatcher(true))
        editTo.addTextChangedListener(createWatcher(false))
    }

    private fun createWatcher(isFrom: Boolean) = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            if (isFrom && editFrom.isFocused) convert(true)
            if (!isFrom && editTo.isFocused) convert(false)
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    private fun convert(isFrom: Boolean) {
        val from = spinnerFrom.selectedItem.toString()
        val to = spinnerTo.selectedItem.toString()

        val rateFrom = rates[from]!!
        val rateTo = rates[to]!!

        if (isFrom) {
            val input = editFrom.text.toString().toDoubleOrNull() ?: 0.0
            val result = input * (rateFrom / rateTo)
            editTo.setText(String.format("%.4f", result))
        } else {
            val input = editTo.text.toString().toDoubleOrNull() ?: 0.0
            val result = input * (rateTo / rateFrom)
            editFrom.setText(String.format("%.4f", result))
        }
    }
}
