package com.example.bricklist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import database.BrickListDatabase
import database.BrickListDatabase_Impl
import entities.Inventories
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    var database: BrickListDatabase? = null
    var inventory: Inventories = Inventories(1, "Inventory1", 1, 9)
    var newInventory: Inventories? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Observable.fromCallable {
            database = BrickListDatabase.getDatabase(this)

        }.doOnNext {
            database!!.inventoriesDao().insertInventories(inventory)
            newInventory = database!!.inventoriesDao().getInventoryById(inventoryID = inventory.id)
            Log.i("Inventory", newInventory!!.name)
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe()

    }
}
