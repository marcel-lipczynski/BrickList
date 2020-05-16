package com.example.bricklist

import adapters.BrickListAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import entities.BrickItem
import kotlinx.android.synthetic.main.activity_list_of_bricks.*

class ListOfBricksActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_of_bricks)

        val items: ArrayList<BrickItem> = ArrayList()
        items.add(BrickItem("", 3, 3, "M", "MisterPolska"))

        BrickListRecyclerView.layoutManager = LinearLayoutManager(this)
        BrickListRecyclerView.adapter = BrickListAdapter(items, this)

    }
}
