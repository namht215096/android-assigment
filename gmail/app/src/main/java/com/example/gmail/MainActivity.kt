package com.example.gmail
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gmail.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val sampleList = listOf(
            Email("Edurila.com", "$19 Only (10 spots)...", "Are you looking to learn..."),
            Email("Chris Abad", "Help make Campaign Monitor better", "Let us know your thoughts..."),
            Email("Tuto.com", "8h de formation gratuite", "Photoshop, CSS, WordPress..."),
            Email("Support", "Société OVH - services", "Suivi de vos services..."),
            Email("Matt from Ionic", "Ionic Creator is here", "Announcing the all-new creator...")
        )

        recyclerView.adapter = EmailAdapter(sampleList)
    }
}
