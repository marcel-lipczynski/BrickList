package adapters

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
    private val inventories: ArrayList<Inventories>,
    private val context: Context
) : RecyclerView.Adapter<InventoriesViewHolder>() {

    private val adapter: InventoryListAdapter = this

    override fun getItemCount(): Int {
        return inventories.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InventoriesViewHolder {
        return InventoriesViewHolder(LayoutInflater.from(context).inflate(R.layout.inventory_item_list_cell, parent, false))
    }

    override fun onBindViewHolder(holder: InventoriesViewHolder, position: Int) {

        holder.inventoryName.text = inventories[position].name
        holder.archiveInventory.isChecked = inventories[position].active == 0

        if(inventories[position].active == 1){
            holder.inventoryItemLayout.setOnClickListener{
                Toast.makeText(context, inventories[position].toString(), Toast.LENGTH_LONG).show()
                val i = Intent(context, ListOfBricksActivity::class.java)
                i.putExtra("inventoryId", inventories[position].id)
                i.putExtra("inventoryName", inventories[position].name)
                holder.inventoryItemLayout.context.startActivity(i)
            }
        }


        holder.archiveInventory.setOnClickListener{view->
            val activeValue = if(holder.archiveInventory.isChecked) 0 else 1
            Observable.fromCallable {
                val brickListDatabase = BrickListDatabase.getDatabase(context)
                brickListDatabase.inventoriesDao().updateInventoryActiveValue(activeValue, inventories[position].id)
                inventories[position] = brickListDatabase.inventoriesDao().getInventoryById(inventories[position].id)
//                adapter.notifyDataSetChanged()
                android.os.Handler(context.mainLooper).post{
                    adapter.notifyItemChanged(position)
                }
            }.doOnNext {
            }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe()
        }

    }

}


class InventoriesViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val inventoryName: TextView = view.inventoryName
    val inventoryItemLayout: LinearLayout = view.inventoryItemLayout
    val archiveInventory: Switch = view.archiveInventory
}