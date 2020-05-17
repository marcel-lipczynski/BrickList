package adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.bricklist.ListOfBricksActivity
import com.example.bricklist.R
import entities.Inventories
import kotlinx.android.synthetic.main.inventory_item_list_cell.view.*

class InventoryListAdapter(
    private val inventories: ArrayList<Inventories>,
    private val context: Context
) : RecyclerView.Adapter<InventoriesViewHolder>() {

    override fun getItemCount(): Int {
        return inventories.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InventoriesViewHolder {
        return InventoriesViewHolder(LayoutInflater.from(context).inflate(R.layout.inventory_item_list_cell, parent, false))
    }

    override fun onBindViewHolder(holder: InventoriesViewHolder, position: Int) {
        holder.inventoryName.text = inventories[position].name
        holder.inventoryItemLayout.setOnClickListener{
            Toast.makeText(context, inventories[position].toString(), Toast.LENGTH_LONG).show()
            val i = Intent(context, ListOfBricksActivity::class.java)
            i.putExtra("position", position)
            holder.inventoryItemLayout.context.startActivity(i)
        }
    }

}


class InventoriesViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val inventoryName: TextView = view.inventoryName
    val inventoryItemLayout: LinearLayout = view.inventoryItemLayout
}