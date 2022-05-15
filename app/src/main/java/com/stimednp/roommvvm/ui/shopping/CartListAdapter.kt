package com.stimednp.roommvvm.ui.shopping

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.stimednp.roommvvm.data.db.entity.CartList
import com.stimednp.roommvvm.databinding.ItemNoteBinding

class CartItemListAdapter(private val listener: (CartList) -> Unit) : ListAdapter<CartList, CartItemListAdapter.CartItemListViewHolder>(DiffUtilNote()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemListViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartItemListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartItemListViewHolder, position: Int) {
        val item = getItem(position)
        holder.bindItem(item, listener)
    }

    fun getCartAt(position: Int): CartList {
        return getItem(position)
    }

    class CartItemListViewHolder(private val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(messInfo: CartList, listener: (CartList) -> Unit) {
            binding.apply {
                titleTV.text = messInfo.title
                descriptionTV.text = messInfo.description
            }
            binding.root.setOnClickListener {
                listener(messInfo)
            }
        }
    }

    private class DiffUtilNote : DiffUtil.ItemCallback<CartList>() {
        override fun areItemsTheSame(oldItem: CartList, newItem: CartList): Boolean {
            return newItem.id == oldItem.id
        }

        override fun areContentsTheSame(oldItem: CartList, newItem: CartList): Boolean {
            return newItem == oldItem
        }
    }
}