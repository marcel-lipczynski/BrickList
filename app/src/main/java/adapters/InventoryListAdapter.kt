package adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bricklist.ListOfBricksActivity
import com.example.bricklist.MainActivity
import com.example.bricklist.R
import database.BrickListDatabase
import entities.Inventories
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.inventory_item_list_cell.view.*
import java.util.logging.Handler

class InventoryListAdapter(
    private var inventories: ArrayList<Inventories>,
    private val context: Context
) : RecyclerView.Adapter<InventoriesViewHolder>() {

    private val adapter: InventoryListAdapter = this

    override fun getItemCount(): Int {
        return inventories.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InventoriesViewHolder {
        return InventoriesViewHolder(
            LayoutInflater.from(context).inflate(R.layout.inventory_item_list_cell, parent, false)
        )
    }

    @SuppressLint("CheckResult")
    override fun onBindViewHolder(holder: InventoriesViewHolder, position: Int) {

        holder.inventoryName.text = inventories[position].name
//        holder.archiveInventory.isChecked = inventories[position].active == 0

        var isInventoryInitiallyActive: Int? = null;
        Observable.fromCallable {
            val brickListDatabase = BrickListDatabase.getDatabase(context)
            isInventoryInitiallyActive = brickListDatabase.inventoriesDao().getInventoryByName(inventories[position].name).active
        }.doOnNext{
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe{
            run {
                holder.archiveInventory.isChecked = (isInventoryInitiallyActive == 0)
            }
        }


        holder.inventoryItemLayout.setOnClickListener {

//            Observable.fromCallable {
//                val brickListDatabase = BrickListDatabase.getDatabase(context)
//
//                val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
//                inventories = (if (sharedPrefs.getBoolean("archive", false)) {
//                    brickListDatabase.inventoriesDao().getAllActiveInventories()
//                } else {
//                    brickListDatabase.inventoriesDao().getAllInventories()
//                }) as ArrayList<Inventories>

                    val i = Intent(context, ListOfBricksActivity::class.java)
                    i.putExtra("inventoryId", inventories[position].id)
                    i.putExtra("inventoryName", inventories[position].name)
                    holder.inventoryItemLayout.context.startActivity(i)


//            }.doOnNext {
//                if (inventories[position].active == 1) {
//                    val i = Intent(context, ListOfBricksActivity::class.java)
//                    i.putExtra("inventoryId", inventories[position].id)
//                    i.putExtra("inventoryName", inventories[position].name)
//                    holder.inventoryItemLayout.context.startActivity(i)
//                    }
//            }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe()

//                Toast.makeText(context, inventories[position].toString(), Toast.LENGTH_LONG).show()

        }


//        holder.archiveInventory.setOnClickListener{view->
//            val activeValue = if(holder.archiveInventory.isChecked) 0 else 1
//            Observable.fromCallable {
//                val brickListDatabase = BrickListDatabase.getDatabase(context)
//                brickListDatabase.inventoriesDao().updateInventoryActiveValue(activeValue, inventories[position].id)
//
//                val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
//                inventories = (if (sharedPrefs.getBoolean("archive", false)) {
//                    brickListDatabase.inventoriesDao().getAllActiveInventories()
//                } else {
//                    brickListDatabase.inventoriesDao().getAllInventories()
//                }) as ArrayList<Inventories>
//
////                android.os.Handler(context.mainLooper).post{
////                    adapter.notifyItemChanged(position)
////                }
//            }.doOnNext {
//            }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe()
//        }
        holder.archiveInventory.setOnCheckedChangeListener { _, isChecked ->

//            holder.inventoryItemLayout.alpha = if(inventories[position].active == 0) 0.3f else 1f

            val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
            val showArchived = sharedPrefs.getBoolean("archive", false)
            val brickListDatabase = BrickListDatabase.getDatabase(context)

            if (isChecked) {

                holder.inventoryItemLayout.alpha = 0.3f

                Observable.fromCallable {
                    brickListDatabase.inventoriesDao()
                        .updateInventoryActiveValue(0, inventories[position].id)
                    brickListDatabase.inventoriesDao().getAllInventories()
                }.doOnNext {

                }.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe()


                if (!showArchived) {
                    val params: ViewGroup.LayoutParams = holder.itemView.layoutParams
                    holder.itemView.visibility = View.VISIBLE
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT
                    holder.itemView.layoutParams = params
                } else {
                    val params: ViewGroup.LayoutParams = holder.itemView.layoutParams
                    holder.itemView.visibility = View.GONE
                    params.height = 0
                    params.width = 0
                    holder.itemView.layoutParams = params
                }

            } else{
                Observable.fromCallable {
                    brickListDatabase.inventoriesDao()
                        .updateInventoryActiveValue(1, inventories[position].id)
                    brickListDatabase.inventoriesDao().getAllInventories()
                }.doOnNext {

                }.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe()
                holder.inventoryItemLayout.alpha = 1f

                val params: ViewGroup.LayoutParams = holder.itemView.layoutParams
                holder.itemView.visibility = View.VISIBLE
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT
                params.width = ViewGroup.LayoutParams.MATCH_PARENT
                holder.itemView.layoutParams = params

            }

        }
    }

}


class InventoriesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val inventoryName: TextView = view.inventoryName
    val inventoryItemLayout: LinearLayout = view.inventoryItemLayout
    val archiveInventory: Switch = view.archiveInventory
}