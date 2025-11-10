package com.example.intlist

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {

    private val MAX_N = 20000

    private lateinit var edtNumber: EditText
    private lateinit var radioGroup: RadioGroup
    private lateinit var txtEmpty: TextView
    private lateinit var listNumbers: LinearLayout

    private lateinit var rbOdd: RadioButton
    private lateinit var rbEven: RadioButton
    private lateinit var rbPrime: RadioButton
    private lateinit var rbPerfect: RadioButton
    private lateinit var rbSquare: RadioButton
    private lateinit var rbFibo: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edtNumber = findViewById(R.id.edtNumber)
        radioGroup = findViewById(R.id.radioGroup)
        txtEmpty = findViewById(R.id.txtEmpty)
        listNumbers = findViewById(R.id.listNumbers)

        rbOdd = findViewById(R.id.rbOdd)
        rbEven = findViewById(R.id.rbEven)
        rbPrime = findViewById(R.id.rbPrime)
        rbPerfect = findViewById(R.id.rbPerfect)
        rbSquare = findViewById(R.id.rbSquare)
        rbFibo = findViewById(R.id.rbFibo)

        // chọn mặc định Số lẻ
        radioGroup.check(R.id.rbOdd)

        // Thêm click listener để có thể bỏ tick
        setupRadioButton(rbOdd)
        setupRadioButton(rbEven)
        setupRadioButton(rbPrime)
        setupRadioButton(rbPerfect)
        setupRadioButton(rbSquare)
        setupRadioButton(rbFibo)

        // cập nhật khi người dùng gõ số
        edtNumber.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) { updateList() }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // cập nhật khi thay đổi lựa chọn
        radioGroup.setOnCheckedChangeListener { _, _ -> updateList() }

        // cập nhật lần đầu
        updateList()
    }

    private fun setupRadioButton(rb: RadioButton) {
        rb.setOnClickListener {
            if (radioGroup.checkedRadioButtonId == rb.id) {
                // nếu đang chọn nút này, bỏ chọn
                radioGroup.clearCheck()
                updateList() // cập nhật danh sách
            } else {
                // chọn nút này
                radioGroup.check(rb.id)
            }
        }
    }

    private fun updateList() {
        listNumbers.removeAllViews()
        txtEmpty.visibility = View.GONE

        val input = edtNumber.text.toString().trim()
        val n = input.toIntOrNull()

        if (n == null || n <= 1) {
            showEmpty("Không có số nào thỏa mãn")
            return
        }

        if (n > MAX_N) {
            showEmpty("Số quá lớn (tối đa $MAX_N) — nhập lại")
            return
        }

        val result = (1 until n).filter { matchesType(it) }

        if (result.isEmpty()) {
            showEmpty("Không có số nào thỏa mãn")
        } else {
            txtEmpty.visibility = View.GONE
            for (num in result) {
                val tv = TextView(this)
                tv.text = num.toString()
                tv.textSize = 18f
                val pad = (resources.displayMetrics.density * 8).toInt()
                tv.setPadding(0, pad, 0, pad)
                listNumbers.addView(tv)
            }
        }
    }

    private fun showEmpty(message: String) {
        listNumbers.removeAllViews()
        txtEmpty.text = message
        txtEmpty.visibility = View.VISIBLE
    }

    private fun matchesType(x: Int): Boolean {
        return when (radioGroup.checkedRadioButtonId) {
            R.id.rbOdd -> isOdd(x)
            R.id.rbEven -> isEven(x)
            R.id.rbPrime -> isPrime(x)
            R.id.rbPerfect -> isPerfect(x)
            R.id.rbSquare -> isSquare(x)
            R.id.rbFibo -> isFibo(x)
            else -> true // nếu không chọn gì => hiển thị tất cả
        }
    }

    private fun isOdd(x: Int) = x % 2 != 0
    private fun isEven(x: Int) = x % 2 == 0

    private fun isPrime(n: Int): Boolean {
        if (n < 2) return false
        val lim = sqrt(n.toDouble()).toInt()
        for (i in 2..lim) if (n % i == 0) return false
        return true
    }

    private fun isPerfect(n: Int): Boolean {
        if (n <= 1) return false
        var sum = 1
        val half = n / 2
        for (i in 2..half) if (n % i == 0) sum += i
        return sum == n
    }

    private fun isSquare(n: Int): Boolean {
        val s = sqrt(n.toDouble()).toInt()
        return s * s == n
    }

    private fun isFibo(n: Int): Boolean {
        fun isSquareNum(x: Long): Boolean {
            val s = kotlin.math.sqrt(x.toDouble()).toLong()
            return s * s == x
        }
        val a = 5L * n * n + 4L
        val b = 5L * n * n - 4L
        return isSquareNum(a) || isSquareNum(b)
    }
}
