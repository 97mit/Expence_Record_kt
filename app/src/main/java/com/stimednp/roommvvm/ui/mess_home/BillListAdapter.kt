package com.stimednp.roommvvm.ui.mess_home

import android.content.Context
import com.stimednp.roommvvm.data.db.entity.MenuItems
import org.json.JSONObject
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.stimednp.roommvvm.R
import org.json.JSONArray
import android.widget.LinearLayout
import android.widget.TextView
import org.json.JSONException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.ArrayList

class BillListAdapter(context: Context, items: List<MenuItems>, jsonObject: JSONObject, var itemClickListener: ItemClickListener) : RecyclerView.Adapter<BillListAdapter.ViewHolder>() {
    private var items: List<MenuItems> = ArrayList()
    private val context: Context
    private val jsonObject: JSONObject
    var mExpandedPosition = -1
    var previousExpandedPosition = -1

    interface ItemClickListener {
        fun onItemClick(currentItem: MenuItems?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_bill_details_list, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val isExpanded = position == mExpandedPosition
        holder.detailsViewDates.visibility = if (isExpanded) View.VISIBLE else View.GONE
        holder.detailsViewDates.removeAllViews()
        val (id, title, price, itemCount) = items[position]
        holder.itemName.text = title
        holder.totalCostOfItem.text = itemCount.toString() + "X" + price + "= " + context.resources.getString(R.string.rupee) + itemCount!! * price
        if (isExpanded) previousExpandedPosition = position
        try {
            val jArr = jsonObject.getJSONArray(id.toString())
            val ll = LinearLayout(context)
            val sdf = SimpleDateFormat("EEE, d MMM yyyy")
            val pdf = SimpleDateFormat("yyyy-MM-dd")
            for (i in 0 until jArr.length()) {
                ll.orientation = LinearLayout.VERTICAL
                val tv_mealDate = TextView(context)
                tv_mealDate.text = sdf.format(pdf.parse(jArr[i].toString()))
                tv_mealDate.setPadding(5, 5, 5, 5)
                tv_mealDate.textSize = 15f
                tv_mealDate.width = 200
                ll.addView(tv_mealDate)
            }
            holder.detailsViewDates.addView(ll)
        } catch (e: JSONException) {
            e.printStackTrace()
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        holder.itemName.setOnClickListener {
            mExpandedPosition = if (isExpanded) -1 else position
            notifyItemChanged(previousExpandedPosition)
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var totalCostOfItem: TextView
        val itemName: TextView
        val detailsViewDates: LinearLayout

        init {
            itemName = itemView.findViewById(R.id.tv_item)
            totalCostOfItem = itemView.findViewById(R.id.tv_total)
            detailsViewDates = itemView.findViewById(R.id.detailsViewDates)
        }
    }

    init {
        this.items = items
        this.jsonObject = jsonObject
        this.context = context
    }
}