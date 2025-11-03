package com.example.bai2

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var display: TextView
    private var operand1: Double? = null
    private var operator: String? = null
    private var userIsTyping = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display = findViewById(R.id.textView7)

        val numberButtons = listOf(
            R.id.button16, R.id.button21, R.id.button22,
            R.id.button15, R.id.button18, R.id.button19,
            R.id.button6, R.id.button8, R.id.button10, R.id.button24
        )

        for (id in numberButtons) {
            findViewById<Button>(id).setOnClickListener { numberClicked(it as Button) }
        }

        findViewById<Button>(R.id.button23).setOnClickListener { operatorClicked("+") }
        findViewById<Button>(R.id.button20).setOnClickListener { operatorClicked("-") }
        findViewById<Button>(R.id.button9).setOnClickListener { operatorClicked("*") }
        findViewById<Button>(R.id.button4).setOnClickListener { operatorClicked("/") }

        findViewById<Button>(R.id.button).setOnClickListener { clearEntry() }   // CE
        findViewById<Button>(R.id.button2).setOnClickListener { clearAll() }    // C
        findViewById<Button>(R.id.button3).setOnClickListener { backspace() }   // BS
        findViewById<Button>(R.id.button26).setOnClickListener { calculate() }  // =
        findViewById<Button>(R.id.button17).setOnClickListener { toggleSign() } // +/-
        findViewById<Button>(R.id.button25).setOnClickListener { addDecimalPoint() }

    }

    private fun numberClicked(btn: Button) {
        val value = btn.text.toString()
        if (userIsTyping) {
            if (display.text.toString() == "0") display.text = value
            else display.append(value)
        } else {
            display.text = value
            userIsTyping = true
        }
    }

    private fun addDecimalPoint() {
        val current = display.text.toString()
        if (!current.contains(".")) {
            display.append(".")
            userIsTyping = true
        }
    }

    private fun operatorClicked(op: String) {
        operand1 = display.text.toString().toDoubleOrNull()
        operator = op
        userIsTyping = false
    }

    private fun calculate() {
        val operand2 = display.text.toString().toDoubleOrNull() ?: return
        val op1 = operand1
        val op = operator
        if (op1 == null || op == null) return

        val result = when (op) {
            "+" -> op1 + operand2
            "-" -> op1 - operand2
            "*" -> op1 * operand2
            "/" -> if (operand2 != 0.0) op1 / operand2 else {
                display.text = "Err"
                return
            }
            else -> operand2
        }


        display.text = if (result % 1.0 == 0.0) result.toInt().toString() else result.toString()

        operand1 = null
        operator = null
        userIsTyping = false
    }

    private fun clearEntry() {
        display.text = "0"
        userIsTyping = false
    }

    private fun clearAll() {
        display.text = "0"
        operand1 = null
        operator = null
        userIsTyping = false
    }

    private fun backspace() {
        val current = display.text.toString()
        display.text = if (current.length > 1) current.dropLast(1) else "0"
    }

    private fun toggleSign() {
        val current = display.text.toString()
        if (current != "0") {
            display.text = if (current.startsWith("-")) current.substring(1) else "-$current"
        }
    }
}
