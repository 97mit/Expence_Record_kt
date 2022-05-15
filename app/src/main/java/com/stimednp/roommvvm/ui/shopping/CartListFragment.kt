package com.stimednp.roommvvm.ui.shopping

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.ActivityNavigator
import androidx.navigation.Navigation
import androidx.navigation.Navigator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stimednp.roommvvm.R
import com.stimednp.roommvvm.databinding.FragmentCartListBinding
import com.stimednp.roommvvm.ui.AddMessActivity
import com.stimednp.roommvvm.ui.MainActivity
import com.stimednp.roommvvm.ui.MealsActivity
import com.stimednp.roommvvm.ui.shopping.CartListViewModel
import com.stimednp.roommvvm.utils.Coroutines
import com.stimednp.roommvvm.utils.UtilExtensions.myToast
import com.stimednp.roommvvm.utils.UtilExtensions.openActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartListFragment : Fragment() {
    private val viewModel by viewModels<CartListViewModel>()
    private var _binding: FragmentCartListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var cartListAdapter: CartItemListAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCartListBinding.inflate(inflater, container, false)
        val root: View = binding.root


        cartListAdapter = CartItemListAdapter {
            var navController = view?.let { Navigation.findNavController(it)};
            val bundle = Bundle()
                bundle.putInt(CART_ID, it.id!!)
            navController?.navigate(R.id.action_nav_cart_list_to_fragment_cart_items,bundle);
        }
        initView()
        observeNotes()
        return root
    }

    private fun itemTouchHelperCallback(): ItemTouchHelper.SimpleCallback {
        return object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                Coroutines.main {
                    val cart = cartListAdapter.getCartAt(viewHolder.adapterPosition)
                    viewModel.deleteCart(cart).also {
                        activity?.myToast(getString(R.string.success_delete))
                    }
                }
            }
        }
    }
    private fun initView() {
        binding.addNoteFAB.setOnClickListener {
            activity?.openActivity(AddMessActivity::class.java){
                putString("type","Cart")
            }
        }

        binding.rvCartList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = cartListAdapter
        }

        ItemTouchHelper(itemTouchHelperCallback()).attachToRecyclerView(binding.rvCartList)
    }

    private fun observeNotes() {
        Coroutines.main {
            viewModel.getAllCarts().observe(viewLifecycleOwner, {
                cartListAdapter.submitList(it)
            })
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    companion object{
        val CART_ID = "cart_id"
    }
}