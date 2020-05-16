package com.example.bricklist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.fasterxml.jackson.databind.MapperFeature

import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
import com.fasterxml.jackson.dataformat.xml.XmlMapper

import com.github.kittinunf.fuel.httpGet
import database.BrickListDatabase


import io.reactivex.Observable
import com.github.kittinunf.result.Result;
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import util.XmlParser
import xmlMappings.InventoryXML
import java.io.File


class MainActivity : AppCompatActivity() {

    private var database: BrickListDatabase? = null
    private var inventoryXml: InventoryXML? = null
    private val jacksonXmlModule: JacksonXmlModule =
        JacksonXmlModule().apply { setDefaultUseWrapper(false) }
    private val xmlMapper = XmlMapper(jacksonXmlModule)
    private var pathFile = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        xmlMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
            .configure(MapperFeature.USE_GETTERS_AS_SETTERS, false)


//        Observable.fromCallable {
//            database = BrickListDatabase.getDatabase(this)
//
//        }.doOnNext {
//            database!!.inventoriesDao().insertInventories(inventory)
//            newInventory = database!!.inventoriesDao().getInventoryById(inventoryID = inventory.id)
//            Log.i("Inventory", newInventory!!.name)
//        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe()

        Observable.fromCallable {

        }.doOnNext {

        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe()

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

                        //reading value from Xml file fetched from server
                        inventoryXml = xmlMapper.readValue(data, InventoryXML::class.java)

                        //path to com.example.bricklist
                        pathFile = filesDir.absolutePath + "/inventory3.xml"

                        XmlParser.writeXml(inventoryXml!!.item, File(pathFile))

                        //writing
//                        xmlString = xmlMapper.writeValueAsString(inventoryXml)

//                        println(xmlString)

//                        xmlMapper.writeValue(File(pathFile), inventoryXml)

                        inventoryXml = xmlMapper.readValue(File(pathFile), InventoryXML::class.java)
                        println(inventoryXml)

                    }
                }
            }
        httpAsync.join()


    }
}
