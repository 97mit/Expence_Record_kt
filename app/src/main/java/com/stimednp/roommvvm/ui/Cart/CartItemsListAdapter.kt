package com.stimednp.roommvvm.ui.Cart
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.stimednp.roommvvm.R
import com.stimednp.roommvvm.data.db.entity.ShopCart
import com.stimednp.roommvvm.databinding.ItemCartItemListBinding

class CartItemsListAdapter(
    private val listener: (cartItems:ShopCart,action:String) -> Unit)
    : ListAdapter<ShopCart, CartItemsListAdapter.CartItemListViewHolder>(DiffUtilNote())  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemListViewHolder {
        val binding = ItemCartItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartItemListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartItemListViewHolder, position: Int) {
        val item = getItem(position)
        holder.bindItem(item,listener)
    }

    fun getNoteAt(position: Int): ShopCart {
        return getItem(position)
    }

    class CartItemListViewHolder(private val binding: ItemCartItemListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(cartItems: ShopCart, listener: (ShopCart,String) -> Unit) {
            binding.apply {
                titleTV.text = cartItems.title
                price.text = cartItems.price.toString()+" /"+cartItems.itemUnit
                quantity.text = cartItems.itemCount.toString()
                ivCartItem.setImageURI(Uri.parse(cartItems.icon))

            }


                binding.btAddItem.visibility = View.GONE
                binding.llCountView.visibility = View.VISIBLE
//            binding.root.setOnClickListener {
//                listener(cartItems,"add")
//            }
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

    private class DiffUtilNote : DiffUtil.ItemCallback<ShopCart>() {
        override fun areItemsTheSame(oldItem: ShopCart, newItem: ShopCart): Boolean {
            return newItem.id == oldItem.id
        }

        override fun areContentsTheSame(oldItem: ShopCart, newItem: ShopCart): Boolean {
            return newItem == oldItem
        }
    }
}
