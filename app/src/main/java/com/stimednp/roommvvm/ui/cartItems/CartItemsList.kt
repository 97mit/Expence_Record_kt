package com.stimednp.roommvvm.ui.cartItems

import android.content.Intent.getIntent
import android.os.Bundle
import android.view.*
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.stimednp.roommvvm.R
import com.stimednp.roommvvm.data.db.entity.CartItems
import com.stimednp.roommvvm.data.db.entity.ShopCart
import com.stimednp.roommvvm.databinding.FragmentCartItemsListBinding
import com.stimednp.roommvvm.ui.Cart.CartFragment.Companion.SHOP_CART_ID
import com.stimednp.roommvvm.ui.Cart.CartViewModel
import com.stimednp.roommvvm.ui.menuItem.AddMenuItemActivity
import com.stimednp.roommvvm.ui.menuItem.MenuItemsListActivity
import com.stimednp.roommvvm.ui.shopping.CartListFragment.Companion.CART_ID
import com.stimednp.roommvvm.utils.Coroutines
import com.stimednp.roommvvm.utils.SwipeHelper
import com.stimednp.roommvvm.utils.UtilExtensions.myToast
import com.stimednp.roommvvm.utils.UtilExtensions.openActivity
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CartItemsList.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class CartItemsList : Fragment() {
    // TODO: Rename and change types of parameters
    var cartId: Int = 0
    private var shopCartId: Int? = null
    private lateinit var binding: FragmentCartItemsListBinding

    private val viewModel by viewModels<CartItemListViewModel>()
    private val cartViewModel by viewModels<CartViewModel>()
    private var menuItems: CartItems? = null

    private lateinit var cartListAdapter: CartItemListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        arguments?.let {
            cartId = it.getInt(CART_ID)
            shopCartId = it.getInt(SHOP_CART_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartItemsListBinding.inflate(layoutInflater)
        val root: View = binding.root
        cartListAdapter = CartItemListAdapter{it,action->
            activity?.myToast(action)
            if (action.equals("add",true)){
                addItemToCart(it)
            }
            if (action.equals("remove",true)){
                removeToCart(it)
            }
        }
        initView()
        observeCartItem()
        return root
    }
    fun removeToCart(cartItems: CartItems) {
        var shopCartItems:ShopCart? = null


        Coroutines.main {
            if (cartItems.itemCount!! > 0) {
                //shopCartItems = ShopCart(cartItems.id,cartItems.title,cartItems.price, cartItems.itemCount?.minus(1),cartItems.itemUnit,cartId,cartItems.icon)
                //cartItems.itemCount = cartItems.itemCount?.minus(1)
                cartItems.itemCount=cartItems.itemCount?.minus(1)
                cartViewModel.updateShopCart(shopCartItems)
            } else {
                cartViewModel.deleteShopCart(shopCartItems)
            }
        }
    }
    fun addItemToCart(cartItems: CartItems){
        Coroutines.main {
            var shopCartItems: ShopCart? = cartViewModel.getSingleCartItem(cartItems.id!!,cartId)
            if (shopCartItems!= null) {
                shopCartItems.itemCount = shopCartItems.itemCount?.plus(cartItems.itemCount!!)
                cartViewModel.updateShopCart(shopCartItems)
            } else {
                shopCartItems = ShopCart(null,cartItems.title,cartItems.price, cartItems.itemCount,cartItems.itemUnit,cartId,cartItems.id,cartItems.icon)
                cartViewModel.insertShopCart(shopCartItems)
            }
        }
    }

    fun initView(){

        binding.rvCartItems.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = cartListAdapter
        }

        val itemTouchHelper = ItemTouchHelper(object : SwipeHelper(binding.rvCartItems) {
            override fun instantiateUnderlayButton(position: Int): List<UnderlayButton> {
                var buttons = listOf<UnderlayButton>()
                val deleteButton = deleteButton(position)
                val editButton = editButton(position)
                buttons = listOf(deleteButton,editButton)
                return buttons
            }
        })
        itemTouchHelper.attachToRecyclerView(binding.rvCartItems)
    }

    private fun deleteButton(position: Int) : SwipeHelper.UnderlayButton {
        return SwipeHelper.UnderlayButton(
            requireActivity(),
            "Delete",
            14.0f,
            android.R.color.holo_red_light,
            object : SwipeHelper.UnderlayButtonClickListener {
                override fun onClick() {
                    val menu = cartListAdapter.getNoteAt(position)
                    Coroutines.io {
                        viewModel.deleteCartItems(menu).also {
                            //myToast(getString(R.string.success_delete))
                        }
                    }
                }
            })
    }

    private fun observeCartItem() {
        Coroutines.main {
            viewModel.getAllCartItems().observe(requireActivity()) {
                cartListAdapter.submitList(it)
            }
        }
    }
    private fun editButton(position: Int) : SwipeHelper.UnderlayButton {
        return SwipeHelper.UnderlayButton(
            requireActivity(),
            "Edit",
            14.0f,
            android.R.color.holo_green_light,
            object : SwipeHelper.UnderlayButtonClickListener {
                override fun onClick() {
                    activity?.openActivity(AddMenuItemActivity::class.java) {
                        putString("type","Cart")
                        putParcelable(MENU_ITEM_DATA,cartListAdapter.getNoteAt(position))
                    }
                }
            })
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.cart_item_list_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_new_items -> activity?.openActivity(AddMenuItemActivity::class.java){
                putString("type","Cart")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val MENU_ITEM_DATA = "MENU_ITEM_DATA"
        @JvmStatic
        fun newInstance(cartId: Int,shopCartId:Int) =
            CartItemsList().apply {
                arguments = Bundle().apply {
                    putInt(CART_ID, cartId)
                    putInt(SHOP_CART_ID, shopCartId)
                }
            }
    }
}