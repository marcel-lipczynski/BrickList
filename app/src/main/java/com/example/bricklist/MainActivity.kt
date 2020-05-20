package com.example.bricklist

import adapters.InventoryListAdapter
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import database.BrickListDatabase
import io.reactivex.Observable
import entities.Inventories
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var brickListDatabase: BrickListDatabase? = null
    private var inventories: List<Inventories>? = null


    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inventoriesRecyclerView.layoutManager = LinearLayoutManager(this)


        val createNewProjectFab: View = findViewById(R.id.addProject)
        createNewProjectFab.setOnClickListener {
            startActivity(Intent(this, NewProjectActivity::class.java))
        }

        Observable.fromCallable {
            brickListDatabase = BrickListDatabase.getDatabase(this)
            inventories =
                brickListDatabase!!.inventoriesDao().getAllInventories()
        }.doOnNext {
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe{
            run{
                inventoriesRecyclerView.adapter =
                    InventoryListAdapter(inventories!! as ArrayList<Inventories>, this)
            }
        }

    }

    @SuppressLint("CheckResult")
    override fun onResume() {
        super.onResume()

        Observable.fromCallable {
            brickListDatabase = BrickListDatabase.getDatabase(this)
            inventories =
                brickListDatabase!!.inventoriesDao().getAllInventories()
        }.doOnNext {
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe{
            run{
                inventoriesRecyclerView.adapter =
                    InventoryListAdapter(inventories!! as ArrayList<Inventories>, this)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.mainactivity_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_settings -> {
            val settingsIntent = Intent(this, SettingsActivity::class.java)
            startActivity(settingsIntent)
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }
}
