package com.stimednp.roommvvm.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.stimednp.roommvvm.R
import com.stimednp.roommvvm.data.db.entity.CartList
import com.stimednp.roommvvm.data.db.entity.MessInfo
import com.stimednp.roommvvm.databinding.ActivityAddNoteBinding
import com.stimednp.roommvvm.ui.home.MessViewModel
import com.stimednp.roommvvm.ui.shopping.CartListViewModel
import com.stimednp.roommvvm.utils.Coroutines
import com.stimednp.roommvvm.utils.UtilExtensions.myToast
import com.stimednp.roommvvm.utils.UtilExtensions.setTextEditable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddMessActivity : AppCompatActivity() {
    private val viewModel by viewModels<MessViewModel>()
    private val cartListViewModel by viewModels<CartListViewModel>()
    private lateinit var binding: ActivityAddNoteBinding

    private var messInfo: MessInfo? = null
    private var cartList: CartList? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        messInfo = intent.extras?.getParcelable(MainActivity.NOTE_DATA)

        initToolbar()
        initView()
        initClick()
    }

    private fun initToolbar() {
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initView() {

        if (messInfo != null) { //this is for set data to form and update data
            binding.titleET.setTextEditable(messInfo?.title ?: "")
            binding.descriptionET.setTextEditable(messInfo?.description ?: "")
            binding.saveButton.text = getString(R.string.update)
        }
    }

    private fun initClick() {
        binding.saveButton.setOnClickListener {
            if (intent.getStringExtra("type").equals("Cart")){
                saveCartData()
            }else
                saveData()
        }
    }

    private fun saveCartData() {
        val id = if (cartList != null) cartList?.id else null
        val title = binding.titleET.text.toString().trim()
        val desc = binding.descriptionET.text.toString().trim()

        if (title.isEmpty() || desc.isEmpty()) {
            myToast(getString(R.string.form_empty))
            return
        }

        val cart = CartList(id = id, title = title, description = desc)
        Coroutines.main {
            if (id != null) { //for update cart
                cartListViewModel.updateCart(cart).also {
                    myToast("cart updated")
                    finish()
                }
            } else { //for insert cart
                cartListViewModel.insertCart(cart).also {
                    myToast("new cart inserted")
                    finish()
                }
            }
        }
    }
    private fun saveData() {
        val id = if (messInfo != null) messInfo?.id else null
        val title = binding.titleET.text.toString().trim()
        val desc = binding.descriptionET.text.toString().trim()

        if (title.isEmpty() || desc.isEmpty()) {
            myToast(getString(R.string.form_empty))
            return
        }

        val note = MessInfo(id = id, title = title, description = desc)
        Coroutines.main {
            if (id != null) { //for update note
                viewModel.updateMess(note).also {
                    myToast(getString(R.string.success_update))
                    finish()
                }
            } else { //for insert note
                viewModel.insertMess(note).also {
                    myToast(getString(R.string.success_save))
                    finish()
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}