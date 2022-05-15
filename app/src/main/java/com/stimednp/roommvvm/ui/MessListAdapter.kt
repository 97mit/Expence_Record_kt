package com.stimednp.roommvvm.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.stimednp.roommvvm.data.db.entity.MessInfo
import com.stimednp.roommvvm.databinding.ItemNoteBinding

class MessListAdapter(private val listener: (MessInfo) -> Unit) : ListAdapter<MessInfo, MessListAdapter.MessViewHolder>(DiffUtilNote()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MessViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MessViewHolder, position: Int) {
        val item = getItem(position)
        holder.bindItem(item, listener)
    }

    fun getNoteAt(position: Int): MessInfo {
        return getItem(position)
    }

    class MessViewHolder(private val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(messInfo: MessInfo, listener: (MessInfo) -> Unit) {
            binding.apply {
                titleTV.text = messInfo.title
                descriptionTV.text = messInfo.description
            }
            binding.root.setOnClickListener {
                listener(messInfo)
            }
        }
    }

    private class DiffUtilNote : DiffUtil.ItemCallback<MessInfo>() {
        override fun areItemsTheSame(oldItem: MessInfo, newItem: MessInfo): Boolean {
            return newItem.id == oldItem.id
        }

        override fun areContentsTheSame(oldItem: MessInfo, newItem: MessInfo): Boolean {
            return newItem == oldItem
        }
    }
}