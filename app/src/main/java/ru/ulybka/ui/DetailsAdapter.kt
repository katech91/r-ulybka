package ru.ulybka.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.ulybka.R
import ru.ulybka.data.local.DetailsItem

class DetailsAdapter: ListAdapter<DetailsItem, RecyclerView.ViewHolder>(DetailsDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val convertView = inflater.inflate(R.layout.item_details, parent, false)
        Log.e("DetailsAdapter", "WORK INIT")
        return DetailsItemViewHolder(convertView, parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.e("DetailsAdapter", "position: $position")
        (holder as DetailsItemViewHolder).bind(getItem(position), position)
    }

    class DetailsItemViewHolder(var converterView: View, parent: ViewGroup): RecyclerView.ViewHolder(converterView) {
        fun bind(details: DetailsItem, position: Int) {
            val pos = position + 1
            converterView.findViewById<TextView>(R.id.tv_number).text = pos.toString()
            converterView.findViewById<TextView>(R.id.tv_name).text = details.name
            converterView.findViewById<TextView>(R.id.tv_group).text = details.group
            converterView.findViewById<TextView>(R.id.tv_category).text = details.category
            converterView.findViewById<TextView>(R.id.tv_quantity).text = details.quantity.toString()
            if (details.isChecked) {
                converterView.findViewById<ImageView>(R.id.iv_checked).visibility = View.VISIBLE
            }


        }
    }
    private class DetailsDiffCallback: DiffUtil.ItemCallback<DetailsItem>() {
        override fun areItemsTheSame(oldItem: DetailsItem, newItem: DetailsItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: DetailsItem, newItem: DetailsItem): Boolean {
            val hold = oldItem.hashCode()
            val hnew = newItem.hashCode()
            return hnew == hold
        }
    }
}