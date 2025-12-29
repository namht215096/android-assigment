package com.example.filemanagement

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var tvPath: TextView
    private lateinit var currentDir: File
    private var files: MutableList<File> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listView)
        tvPath = findViewById(R.id.tvPath)

        // 🔥 Android 12: kiểm tra quyền All files access
        if (!Environment.isExternalStorageManager()) {
            requestAllFilesPermission()
        } else {
            loadRoot()
        }

        listView.setOnItemClickListener { _, _, position, _ ->
            val file = files[position]
            if (file.isDirectory) {
                loadFiles(file)
            } else {
                openFile(file)
            }
        }

        registerForContextMenu(listView)
    }

    /* ================= PERMISSION ================= */
    private fun requestAllFilesPermission() {
        AlertDialog.Builder(this)
            .setTitle("Cấp quyền truy cập file")
            .setMessage("Ứng dụng cần quyền truy cập tất cả file để quản lý TXT, thư mục...")
            .setPositiveButton("Cấp quyền") { _, _ ->
                val intent = Intent(
                    Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION,
                    Uri.parse("package:$packageName")
                )
                startActivity(intent)
            }
            .setCancelable(false)
            .show()
    }

    override fun onResume() {
        super.onResume()
        // 🔥 Sau khi user bật quyền → load lại file
        if (Environment.isExternalStorageManager()) {
            loadRoot()
        }
    }

    /* ================= LOAD FILE ================= */
    private fun loadRoot() {
        currentDir = Environment.getExternalStorageDirectory()
        loadFiles(currentDir)
    }

    private fun loadFiles(dir: File) {
        currentDir = dir
        tvPath.text = dir.absolutePath

        val list = dir.listFiles()
        files = if (list != null) {
            list.sortedWith(
                compareBy<File> { !it.isDirectory }.thenBy { it.name.lowercase() }
            ).toMutableList()
        } else {
            mutableListOf()
        }

        listView.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            files.map { it.name }
        )
    }

    private fun openFile(file: File) {
        val intent = Intent(this, FileViewerActivity::class.java)
        intent.putExtra("path", file.absolutePath)
        startActivity(intent)
    }

    /* ================= OPTION MENU ================= */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_option, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_new_folder -> createNewFolder()
            R.id.menu_new_file -> createNewFile()
        }
        return true
    }

    private fun createNewFolder() {
        val editText = EditText(this)
        AlertDialog.Builder(this)
            .setTitle("Tạo thư mục mới")
            .setView(editText)
            .setPositiveButton("OK") { _, _ ->
                File(currentDir, editText.text.toString()).mkdir()
                loadFiles(currentDir)
            }
            .setNegativeButton("Hủy", null)
            .show()
    }

    private fun createNewFile() {
        val editText = EditText(this)
        AlertDialog.Builder(this)
            .setTitle("Tạo file TXT mới")
            .setView(editText)
            .setPositiveButton("OK") { _, _ ->
                File(currentDir, editText.text.toString() + ".txt").createNewFile()
                loadFiles(currentDir)
            }
            .setNegativeButton("Hủy", null)
            .show()
    }

    /* ================= CONTEXT MENU ================= */
    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menu.add("Đổi tên")
        menu.add("Xóa")
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val file = files[info.position]

        when (item.title) {
            "Đổi tên" -> renameFile(file)
            "Xóa" -> deleteFile(file)
        }
        return true
    }

    private fun renameFile(file: File) {
        val editText = EditText(this)
        editText.setText(file.name)

        AlertDialog.Builder(this)
            .setTitle("Đổi tên")
            .setView(editText)
            .setPositiveButton("OK") { _, _ ->
                file.renameTo(File(file.parent, editText.text.toString()))
                loadFiles(currentDir)
            }
            .setNegativeButton("Hủy", null)
            .show()
    }

    private fun deleteFile(file: File) {
        AlertDialog.Builder(this)
            .setTitle("Xác nhận")
            .setMessage("Bạn có chắc muốn xóa?")
            .setPositiveButton("Xóa") { _, _ ->
                file.deleteRecursively()
                loadFiles(currentDir)
            }
            .setNegativeButton("Hủy", null)
            .show()
    }

    /* ================= BACK ================= */
    override fun onBackPressed() {
        val root = Environment.getExternalStorageDirectory().absolutePath
        if (currentDir.absolutePath != root) {
            currentDir.parentFile?.let { loadFiles(it) }
        } else {
            super.onBackPressed()
        }
    }
}
