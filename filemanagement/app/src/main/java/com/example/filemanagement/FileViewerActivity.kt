package com.example.filemanagement

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

class FileViewerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_viewer)

        val imageView = findViewById<ImageView>(R.id.imageView)
        val textView = findViewById<TextView>(R.id.textView)

        val path = intent.getStringExtra("path")

        if (path == null) {
            textView.text = "Không có đường dẫn file"
            textView.visibility = View.VISIBLE
            return
        }

        val file = File(path)
        
        imageView.visibility = View.GONE
        textView.visibility = View.GONE

        val name = file.name.lowercase()

        /* ================= TXT ================= */
        if (name.endsWith(".txt")) {
            textView.visibility = View.VISIBLE
            try {
                val builder = StringBuilder()
                val reader = BufferedReader(
                    InputStreamReader(file.inputStream())
                )

                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    builder.append(line).append("\n")
                }
                reader.close()

                textView.text = builder.toString()
            } catch (e: Exception) {
                textView.text = "Lỗi đọc file TXT:\n${e.message}"
            }
            return
        }

        /* ================= IMAGE ================= */
        if (
            name.endsWith(".png") ||
            name.endsWith(".jpg") ||
            name.endsWith(".jpeg") ||
            name.endsWith(".bmp")
        ) {
            imageView.visibility = View.VISIBLE

            val bitmap = BitmapFactory.decodeFile(file.absolutePath)
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap)
            } else {
                textView.visibility = View.VISIBLE
                textView.text = "Không thể mở ảnh"
            }
            return
        }

        /* ================= KHÔNG HỖ TRỢ ================= */
        textView.visibility = View.VISIBLE
        textView.text = "Không hỗ trợ định dạng file này"
    }

    override fun onBackPressed() {
        finish()
    }
}
