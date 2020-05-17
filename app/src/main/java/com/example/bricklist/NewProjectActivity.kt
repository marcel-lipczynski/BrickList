package com.example.bricklist

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.preference.PreferenceManager
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import database.BrickListDatabase
import entities.Inventories
import entities.InventoriesParts
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_new_project.*
import util.XmlParser
import xmlMappings.InventoryXML
import java.io.File

class NewProjectActivity : AppCompatActivity() {

    private var brickListDatabse: BrickListDatabase? = null
    private var sharedPrefs: SharedPreferences? = null
    private var prefixURL: String? = null
    private var inventoryXml: InventoryXML? = null
    private val jacksonXmlModule: JacksonXmlModule =
        JacksonXmlModule().apply { setDefaultUseWrapper(false) }
    private val xmlMapper = XmlMapper(jacksonXmlModule)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_project)
        title = "New project"

        addButton.isEnabled = false
        checkButton.isEnabled = false

        xmlMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
            .configure(MapperFeature.USE_GETTERS_AS_SETTERS, false)

        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this)
        prefixURL = sharedPrefs!!.getString("prefix", null)
        Log.i("PREFIX", prefixURL!!)

        projectNum.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val projectName = projectName.text.trim()
                val projectNum = projectNum.text.trim()
                checkButton.isEnabled = (projectName.isNotEmpty() && projectNum.isNotEmpty())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }


        })
        projectName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val projectName = projectName.text.trim()
                val projectNum = projectNum.text.trim()
                checkButton.isEnabled = (projectName.isNotEmpty() && projectNum.isNotEmpty())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }


        })


    }


    fun onCheckButtonClick(view: View) {
        val url = "${prefixURL}${projectNum.text}.xml"
        Log.i("URL", url)
        val httpAsync = url.httpGet()
            .responseString { _, _, result ->
                when (result) {
                    is Result.Failure -> {
                        val ex = result.getException()
                        println(ex)
                        runOnUiThread {
                            Toast.makeText(this, "Project not found", Toast.LENGTH_LONG).show()
                        }

                    }
                    is Result.Success -> {
                        val data = result.get()
                        println(data)

                        inventoryXml = xmlMapper.readValue(data, InventoryXML::class.java)
                        println(inventoryXml)

                        runOnUiThread {
                            Toast.makeText(
                                this,
                                "Project found - ADD button enabled",
                                Toast.LENGTH_LONG
                            ).show()
                            addButton.isEnabled = true
                        }
                    }
                }
            }
        httpAsync.join()
    }

    fun onAddButtonClick(view: View) {
        addButton.isEnabled = false

        val projectName = projectName.text.trim().toString()
        var newInventory = Inventories(name = projectName, active = 1, lastAccessed = 0)

        val itemsArray = inventoryXml?.item

        Observable.fromCallable {
            brickListDatabse = BrickListDatabase.getDatabase(this)
        }.doOnNext {
            brickListDatabse!!.inventoriesDao().insertInventories(newInventory)
            val insertedInventoryID =
                brickListDatabse!!.inventoriesDao().getInventoryByName(projectName).id
//            Log.i("Inventory", newInventory.name)



            if (itemsArray != null) {
                for (item in itemsArray) {
                    val typeID = brickListDatabse!!.itemTypeDao().getItemTypeByCode(item.itemType)
                    brickListDatabse!!.inventoriesPartsDao()
                        .insertInventoryParts(
                            InventoriesParts(
                                inventoryID = insertedInventoryID,
                                typeID = typeID,
                                itemID = item.itemId.toInt(),
                                quantitiyInSet = item.qty,
                                quantitiyInStore = 0,
                                colorID = item.color,
                                extra = item.extra.toInt()
                            )
                        )
                }
                Log.i("SUCCESS", "INVENTORY ADDED TO DATABASE!")
            }

        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe()

    }


}
