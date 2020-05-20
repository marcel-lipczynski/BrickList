package com.example.bricklist

import adapters.BrickListAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import database.BrickListDatabase
import entities.BrickItem
import entities.InventoriesParts
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_list_of_bricks.*
import util.XmlParser
import java.io.File

class ListOfBricksActivity : AppCompatActivity() {

    private var inventoriesParts: List<InventoriesParts>? = null
    private var brickListItems: ArrayList<BrickItem> = ArrayList()
    private var brickListDatabase: BrickListDatabase? = null
    private var inventoryId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_of_bricks)

        title = "Project ${intent.extras!!.getString("inventoryName")}"
        inventoryId = intent.extras!!.getInt("inventoryId")



        BrickListRecyclerView.layoutManager = LinearLayoutManager(this)
        BrickListRecyclerView.adapter = BrickListAdapter(brickListItems, this)


        Observable.fromCallable {
            brickListDatabase = BrickListDatabase.getDatabase(this)
        }.doOnNext {
//            brickListItems = brickListDatabase!!.

            inventoriesParts =
                brickListDatabase!!.inventoriesPartsDao().getAllPartsForInventoryOrdered(inventoryId!!)

            for (part in inventoriesParts!!) {

                val colorName = brickListDatabase!!.colorsDao().getColorByCodeFromXML(part.colorID)
                val brickName = brickListDatabase?.partsDao()?.getPartNameByPartCode(part.itemID)
                val itemType = brickListDatabase!!.itemTypeDao().getItemTypeByTypeId(part.typeID)
                //itemId z InventoriesParts to inaczej Code w tabeli Parts
                brickListItems.add(
                    BrickItem(id = part.id,
                        imagePath = "",
                        brickQuantity = part.quantitiyInSet,
                        actualBrickQuantity = part.quantitiyInStore,
                        colorID = part.colorID,
                        brickColor = colorName,
                        brickName = brickName ?: part.itemID,
                        itemID = part.itemID,
                        itemType = itemType
                    )
                )
            }
            runOnUiThread {
                BrickListRecyclerView.adapter =
                    BrickListAdapter(brickListItems, this)
            }

        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.listofbricks_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_save -> {
//            TODO Save XML while clicking on save icon!
            val pathFile = "${filesDir.absolutePath}/inventory${inventoryId}.xml"
            XmlParser.writeXml(brickListItems, File(pathFile))
            Toast.makeText(this, "XML saved successfully", Toast.LENGTH_LONG).show()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }


}
