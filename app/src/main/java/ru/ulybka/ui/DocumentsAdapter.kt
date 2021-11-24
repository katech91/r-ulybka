package ru.ulybka.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.ulybka.R
import ru.ulybka.data.local.DocumentItem

class DocumentsAdapter: ListAdapter<DocumentItem, RecyclerView.ViewHolder>(DocumentsDiffCallback()) {

    var items: List<DocumentItem> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val convertView = inflater.inflate(R.layout.item_document, parent, false)
        Log.e("DocumentsAdapter", "WORK INIT")
        return DocumentItemViewHolder(convertView, parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.e("DocumentsAdapter", "position: $position")
        (holder as DocumentItemViewHolder).bind(getItem(position))
    }

//    override fun getItemCount(): Int = items.size

    class DocumentItemViewHolder(var converterView: View, parent: ViewGroup): RecyclerView.ViewHolder(converterView) {
        fun bind(document: DocumentItem) {
            converterView.findViewById<TextView>(R.id.tv_document_name).text = document.id.toString()
            converterView.findViewById<TextView>(R.id.tv_date).text = document.date
            converterView.findViewById<TextView>(R.id.tv_post).text = document.post
            converterView.findViewById<TextView>(R.id.tv_quantity_pos).text = document.numPos.toString()
            converterView.findViewById<TextView>(R.id.tv_quantity_checked).text = document.numChecked.toString()

        }
    }
    private class DocumentsDiffCallback: DiffUtil.ItemCallback<DocumentItem>() {
        override fun areItemsTheSame(oldItem: DocumentItem, newItem: DocumentItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: DocumentItem, newItem: DocumentItem): Boolean {
            val hold = oldItem.hashCode()
            val hnew = newItem.hashCode()
            return hnew == hold
        }
    }
}