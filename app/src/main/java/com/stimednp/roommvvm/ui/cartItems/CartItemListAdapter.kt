package com.stimednp.roommvvm.ui.cartItems

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.stimednp.roommvvm.R
import com.stimednp.roommvvm.data.db.entity.CartItems
import com.stimednp.roommvvm.databinding.ItemCartItemListBinding

class CartItemListAdapter(
    private val listener: (cartItems:CartItems,action:String) -> Unit)
    : ListAdapter<CartItems, CartItemListAdapter.CartItemListViewHolder>(DiffUtilNote())  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemListViewHolder {
        val binding = ItemCartItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartItemListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartItemListViewHolder, position: Int) {
        val item = getItem(position)
        holder.bindItem(item,listener)
    }

    fun getNoteAt(position: Int): CartItems {
        return getItem(position)
    }

    class CartItemListViewHolder(private val binding: ItemCartItemListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(cartItems: CartItems, listener: (CartItems,String) -> Unit) {
            binding.apply {
                titleTV.text = cartItems.title
                price.text =cartItems.price.toString()+" /"+cartItems.itemUnit
                ivCartItem.setImageURI(Uri.parse(cartItems.icon))
                
            }
            binding.root.setOnClickListener {
                listener(cartItems,"add")
            }
            binding.btAddItem.setOnClickListener{
                listener(cartItems,"add")
            }
            binding.btAdd.setOnClickListener{
                listener(cartItems,"add")
            }
            binding.btRemove.setOnClickListener{
                listener(cartItems,"remove")
            }

        }
    }

    private class DiffUtilNote : DiffUtil.ItemCallback<CartItems>() {
        override fun areItemsTheSame(oldItem: CartItems, newItem: CartItems): Boolean {
            return newItem.id == oldItem.id
        }

        override fun areContentsTheSame(oldItem: CartItems, newItem: CartItems): Boolean {
            return newItem == oldItem
        }
    }
}
