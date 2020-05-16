package com.example.bricklist

import adapters.BrickListAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import entities.BrickItem
import kotlinx.android.synthetic.main.activity_list_of_bricks.*

class ListOfBricksActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_of_bricks)

        val items: ArrayList<BrickItem> = ArrayList()
        items.add(BrickItem("", 1, 3, "BLUE", "2 x 3 BRICK"))

        BrickListRecyclerView.layoutManager = LinearLayoutManager(this)
        BrickListRecyclerView.adapter = BrickListAdapter(items, this)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.listofbricks_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_save -> {
//            TODO Save XML

            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }



}
