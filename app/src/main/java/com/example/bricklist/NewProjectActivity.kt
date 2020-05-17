package com.example.bricklist

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.preference.PreferenceManager
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.activity_new_project.*
import util.XmlParser
import xmlMappings.InventoryXML
import java.io.File

class NewProjectActivity : AppCompatActivity() {

    private var sharedPrefs: SharedPreferences? = null
    private var prefixURL: String? = null
    private var inventoryXml: InventoryXML? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_project)
        title = "New project"


        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this)
        prefixURL = sharedPrefs!!.getString("prefix", null)
        Log.i("PREFIX", prefixURL!!)
    }


    fun onCheckButtonClick(view: View){
        val url = "${prefixURL}${projectNum.text}.xml"
        Log.i("URL", url)
        val httpAsync = url.httpGet()
            .responseString { _, _, result ->
                when (result) {
                    is Result.Failure -> {
                        val ex = result.getException()
                        println(ex)
                        runOnUiThread{
                            Toast.makeText(this, "Project not found", Toast.LENGTH_LONG).show()
                        }

                    }
                    is Result.Success -> {
                        val data = result.get()
                        println(data)





//                        //reading value from Xml file fetched from server
//                        inventoryXml = xmlMapper.readValue(data, InventoryXML::class.java)
//
//                        //path to com.example.bricklist
//                        pathFile = filesDir.absolutePath + "/inventory3.xml"
//
//                        XmlParser.writeXml(inventoryXml!!.item, File(pathFile))
//
//                        //writing
////                        xmlString = xmlMapper.writeValueAsString(inventoryXml)
//
////                        println(xmlString)
//
////                        xmlMapper.writeValue(File(pathFile), inventoryXml)
//
//                        inventoryXml = xmlMapper.readValue(File(pathFile), InventoryXML::class.java)
//                        println(inventoryXml)

                        runOnUiThread{
                            Toast.makeText(this, "Project found - ADD button enabled", Toast.LENGTH_LONG).show()
                            addButton.isEnabled = true
                        }

                    }
                }
            }
        httpAsync.join()
    }

    fun onAddButtonClick(view: View){

    }

}
