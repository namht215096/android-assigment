package com.example.ggplay

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var tab: TabLayout
    private lateinit var rv: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tab = findViewById(R.id.tabLayout)
        rv = findViewById(R.id.rvSections)

        rv.layoutManager = LinearLayoutManager(this)

        setupTabs()
    }

    private fun setupTabs() {
        tab.addTab(tab.newTab().setText("For you"))
        tab.addTab(tab.newTab().setText("Top charts"))
        tab.addTab(tab.newTab().setText("Other devices"))
        tab.addTab(tab.newTab().setText("Kids"))

        // Mặc định chọn For You
        loadData("For you")

        tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(t: TabLayout.Tab) {
                loadData(t.text.toString())
            }
            override fun onTabUnselected(t: TabLayout.Tab?) {}
            override fun onTabReselected(t: TabLayout.Tab?) {}
        })
    }

    private fun loadData(tabName: String) {

        val sections: List<Section> = when (tabName) {

            "For you" -> listOf(
                Section("Suggested for you", genItems()),
                Section("Recommended", genItems()),
                Section("Based on recent activity", genItems())
            )

            "Top charts" -> listOf(
                Section("Top free apps", genItems()),
                Section("Top grossing", genItems()),
                Section("Trending now", genItems())
            )

            "Other devices" -> listOf(
                Section("Apps for WearOS", genItems()),
                Section("Apps for Tablet", genItems())
            )

            "Kids" -> listOf(
                Section("Learn & play", genItems()),
                Section("Popular for kids", genItems())
            )

            else -> emptyList()
        }

        rv.adapter = SectionAdapter(sections)
    }

    private fun genItems(): List<Item> {
        return (1..10).map { Item("Item $it") }
    }
}
