package com.stimednp.roommvvm.ui.menuItem

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.stimednp.roommvvm.data.db.entity.MenuItems
import com.stimednp.roommvvm.databinding.ItemMenuBinding

class MenuListAdapter(
    private val listener: (MenuItems) -> Unit)
    : ListAdapter<MenuItems, MenuListAdapter.MenuViewHolder>(DiffUtilNote())  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = ItemMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val item = getItem(position)
        holder.bindItem(item, listener)
    }

    fun getNoteAt(position: Int): MenuItems {
        return getItem(position)
    }

    class MenuViewHolder(private val binding: ItemMenuBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(menuItem: MenuItems, listener: (MenuItems) -> Unit) {
            binding.apply {
                titleTV.text = menuItem.title
                price.text = menuItem.price.toString()
            }
            binding.root.setOnClickListener {
                listener(menuItem)
            }

        }
    }

    private class DiffUtilNote : DiffUtil.ItemCallback<MenuItems>() {
        override fun areItemsTheSame(oldItem: MenuItems, newItem: MenuItems): Boolean {
            return newItem.id == oldItem.id
        }

        override fun areContentsTheSame(oldItem: MenuItems, newItem: MenuItems): Boolean {
            return newItem == oldItem
        }
    }
}
