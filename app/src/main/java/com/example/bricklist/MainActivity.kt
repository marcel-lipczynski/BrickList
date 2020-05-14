package com.example.bricklist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.github.kittinunf.fuel.httpGet
import database.BrickListDatabase

import entities.Inventories
import io.reactivex.Observable
import com.github.kittinunf.result.Result;
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class MainActivity : AppCompatActivity() {

    var database: BrickListDatabase? = null
    var inventory: Inventories = Inventories(1, "Inventory1", 1, 9)
    var newInventory: Inventories? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        Observable.fromCallable {
//            database = BrickListDatabase.getDatabase(this)
//
//        }.doOnNext {
//            database!!.inventoriesDao().insertInventories(inventory)
//            newInventory = database!!.inventoriesDao().getInventoryById(inventoryID = inventory.id)
//            Log.i("Inventory", newInventory!!.name)
//        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe()

        Observable.fromCallable {

            val httpAsync = "http://fcds.cs.put.poznan.pl/MyWeb/BL/615.xml".httpGet()
                .responseString { request, response, result ->
                    when (result) {
                        is Result.Failure -> {
                            val ex = result.getException()
                            println(ex)
                        }
                        is Result.Success -> {
                            val data = result.get()
                            println(data)
                        }
                    }
                }
            httpAsync.join()

        }.doOnNext {
//            database!!.inventoriesDao().insertInventories(inventory)
//            newInventory = database!!.inventoriesDao().getInventoryById(inventoryID = inventory.id)
//            Log.i("Inventory", newInventory!!.name)
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe()

    }
}
