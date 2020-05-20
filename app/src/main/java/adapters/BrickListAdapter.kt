package adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.bricklist.R
import database.BrickListDatabase
import entities.BrickItem
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.brick_item_list_cell.view.*

class BrickListAdapter(private val brickItems: ArrayList<BrickItem>, private val context: Context) :
    RecyclerView.Adapter<BrickListViewHolder>() {

    override fun getItemCount(): Int {
        return brickItems.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrickListViewHolder {
        return BrickListViewHolder(
            LayoutInflater.from(context).inflate(R.layout.brick_item_list_cell, parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: BrickListViewHolder, position: Int) {

        if (brickItems[position].actualBrickQuantity == brickItems[position].brickQuantity) {
            holder.brickItemLinearLayout.setBackgroundColor(Color.GREEN)
        } else {
            holder.brickItemLinearLayout.setBackgroundColor(Color.WHITE)
        }

        Glide.with(context)
            .load("sdsdsds").error(
                Glide.with(context)
                    .load("https://images.genius.com/c745ae8eec9dd6000f52a07aa84e4457.1000x1000x1.jpg")
                    .error(Glide.with(context).load("hello"))
            )
            .into(holder.brickImage)

        holder.brickName.text = brickItems[position].brickName
        holder.brickColor.text = brickItems[position].brickColor
        holder.bricksQuantity.text =
            "${brickItems[position].actualBrickQuantity} of ${brickItems[position].brickQuantity}"


        holder.plus.setOnClickListener {
            if (brickItems[position].actualBrickQuantity + 1 <= brickItems[position].brickQuantity) {
                brickItems[position].actualBrickQuantity += 1
            }
            if (brickItems[position].actualBrickQuantity == brickItems[position].brickQuantity) {
                holder.brickItemLinearLayout.setBackgroundColor(Color.GREEN)
            } else {
                holder.brickItemLinearLayout.setBackgroundColor(Color.WHITE)
            }
            holder.bricksQuantity.text =
                "${brickItems[position].actualBrickQuantity} of ${brickItems[position].brickQuantity}"

            Observable.fromCallable {
                val brickListDatabase = BrickListDatabase.getDatabase(context)
                brickListDatabase.inventoriesPartsDao().updateQuantityInStoreById(
                    brickItems[position].actualBrickQuantity,
                    brickItems[position].id
                )
            }.doOnNext {
            }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe()


        }

        holder.minus.setOnClickListener {
            if (brickItems[position].actualBrickQuantity - 1 >= 0) {
                brickItems[position].actualBrickQuantity -= 1
            }
            if (brickItems[position].actualBrickQuantity == brickItems[position].brickQuantity) {
                holder.brickItemLinearLayout.setBackgroundColor(Color.GREEN)
            } else {
                holder.brickItemLinearLayout.setBackgroundColor(Color.WHITE)
            }
            holder.bricksQuantity.text =
                "${brickItems[position].actualBrickQuantity} of ${brickItems[position].brickQuantity}"

            Observable.fromCallable {
                val brickListDatabase = BrickListDatabase.getDatabase(context)
                brickListDatabase.inventoriesPartsDao().updateQuantityInStoreById(
                    brickItems[position].actualBrickQuantity,
                    brickItems[position].id
                )
            }.doOnNext {
            }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe()

        }


    }
}


class BrickListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val brickImage: ImageView = view.brickImage
    val brickName: TextView = view.brickName
    val bricksQuantity: TextView = view.bricksQuantity
    val brickColor: TextView = view.brickColor
    val plus: Button = view.plus
    val minus: Button = view.minus
    val brickItemLinearLayout: LinearLayout = view.brickItemLinearLayout
}