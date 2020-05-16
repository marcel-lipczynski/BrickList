package adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bricklist.R
import entities.BrickItem
import kotlinx.android.synthetic.main.brick_item_list_cell.view.*

class BrickListAdapter(private val brickItems: ArrayList<BrickItem>, private val context: Context) :
    RecyclerView.Adapter<ViewHolder>() {

    override fun getItemCount(): Int {
        return brickItems.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.brick_item_list_cell, parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context)
            .load("https://images.genius.com/c745ae8eec9dd6000f52a07aa84e4457.1000x1000x1.jpg")
            .into(holder.brickImage)
        holder.brickName.text = brickItems[position].brickName
        holder.brickColor.text = brickItems[position].brickColor
        holder.bricksQuantity.text =
            "${brickItems[position].actualBrickQuantity} of ${brickItems[position].brickQuantity}"
    }


}


class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val brickImage: ImageView = view.brickImage
    val brickName: TextView = view.brickName
    val bricksQuantity: TextView = view.bricksQuantity
    val brickColor: TextView = view.brickColor
}