package com.stimednp.roommvvm.ui.Cart

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.stimednp.roommvvm.R
import com.stimednp.roommvvm.adapter.GenericAdapter
import com.stimednp.roommvvm.customDialogs.EditMealDialog
import com.stimednp.roommvvm.data.db.entity.CartItems
import com.stimednp.roommvvm.data.db.entity.CartList
import com.stimednp.roommvvm.data.db.entity.Meals
import com.stimednp.roommvvm.data.db.entity.ShopCart
import com.stimednp.roommvvm.databinding.FragmentCartBinding
import com.stimednp.roommvvm.databinding.FragmentMessHomeBinding
import com.stimednp.roommvvm.databinding.ItemCartItemListBinding
import com.stimednp.roommvvm.ui.mess_home.ARG_MONTH
import com.stimednp.roommvvm.ui.mess_home.ARG_YEAR
import com.stimednp.roommvvm.ui.mess_home.MealListAdapter
import com.stimednp.roommvvm.ui.mess_home.SUM_OF_MONTH
import com.stimednp.roommvvm.ui.cartItems.CartItemListAdapter
import com.stimednp.roommvvm.ui.menuItem.AddMenuItemActivity
import com.stimednp.roommvvm.ui.shopping.CartListFragment.Companion.CART_ID
import com.stimednp.roommvvm.ui.shopping.CartListViewModel
import com.stimednp.roommvvm.utils.Coroutines
import com.stimednp.roommvvm.utils.UtilExtensions.myToast
import com.stimednp.roommvvm.utils.UtilExtensions.openActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [CartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class CartFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private val viewModel by viewModels<CartViewModel>()
    private val cartViewModel by viewModels<CartViewModel>()
    private var cartId: Int? = null
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private lateinit var cartListAdapter: CartItemsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        arguments?.let {
            cartId = it.getInt(CART_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        activity?.title = "Test"
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        val root: View = binding.root
        observeCartItem()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fabAddItemToCart.setOnClickListener(View.OnClickListener {
            var navController = view.let { Navigation.findNavController(it) };
            val bundle = Bundle()
            bundle.putInt(CART_ID, cartId!!)
            bundle.putInt(SHOP_CART_ID, -1)
            navController.navigate(R.id.action_fragment_cart_items_to_nav_cart_item_list, bundle);
        })


        binding.rvCart.layoutManager = LinearLayoutManager(activity)
        binding.rvCart.setHasFixedSize(true)
        cartListAdapter = CartItemsListAdapter { it, action ->
            if (action.equals("add", true)) {
                addItemToCart(it)
            }
            if (action.equals("remove", true)) {
                removeToCart(it)
            }

        }
        binding.rvCart.adapter = cartListAdapter
        observeCartItem()


    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.cart_items_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.share_cart -> {
                Coroutines.main {
                    var str = "*=======Shopping List=======*\n"
                    var test:List<ShopCart>? = cartViewModel.getAllShopCartItems(cartId!!)
                    test?.forEach { it->
                      str+=it.title+" ("+it.itemCount+" "+it.itemUnit+") ==>"+it.itemCount+" x "+it.price+"="+(it.price * it.itemCount!!)+"\n"
                    }
                    val intent= Intent()
                    intent.action=Intent.ACTION_SEND
                    intent.putExtra(Intent.EXTRA_TEXT,str)
                    intent.type="text/plain"
                    startActivity(Intent.createChooser(intent,"Share To:"))
                }

            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun removeToCart(cartItems: ShopCart) {
        Coroutines.main {
            if (cartItems.itemCount!! > 1 && cartItems.cartId == cartId) {
                cartItems.itemCount = cartItems.itemCount?.minus(1)
                cartViewModel.updateShopCart(cartItems)
            } else if (cartItems.cartId == cartId) {
                cartViewModel.deleteShopCart(cartItems)
            }
        }
    }


    fun addItemToCart(cartItems: ShopCart) {
        Coroutines.main {
            if (cartItems.itemCount!! > 0 && cartItems.cartId == cartId) {
                cartItems.itemCount = cartItems.itemCount?.plus(1)
                cartViewModel.updateShopCart(cartItems)
            } else if (cartItems.cartId == cartId) {
                cartViewModel.insertShopCart(cartItems)
            }
        }
    }

    private fun observeCartItem() {
        Coroutines.main {
            viewModel.getAllLiveShopCartItems(cartId!!).observe(requireActivity()) {
                cartListAdapter.submitList(it)
                cartListAdapter.notifyDataSetChanged()

            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CartFragment.
         */
        val SHOP_CART_ID = "shop_cart_id"

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(cartId: Int) =
            CartFragment().apply {
                arguments = Bundle().apply {
                    putInt(CART_ID, cartId)
                }
            }
    }
}