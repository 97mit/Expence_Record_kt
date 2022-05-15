package com.stimednp.roommvvm.ui.menuItem

import android.Manifest.permission.*
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.stimednp.roommvvm.R
import com.stimednp.roommvvm.data.db.entity.CartItems
import com.stimednp.roommvvm.data.db.entity.MenuItems
import com.stimednp.roommvvm.databinding.ActivityAddMenuItemBinding
import com.stimednp.roommvvm.ui.cartItems.CartItemListViewModel
import com.stimednp.roommvvm.ui.cartItems.CartItemsList.Companion.MENU_ITEM_DATA
import com.stimednp.roommvvm.utils.Coroutines
import com.stimednp.roommvvm.utils.UtilExtensions.myToast
import com.stimednp.roommvvm.utils.UtilExtensions.setTextEditable
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


@AndroidEntryPoint
class AddMenuItemActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private val viewModel by viewModels<MenuItemsViewModel>()
    private val cartItemsListViewModel by viewModels<CartItemListViewModel>()
    private lateinit var binding: ActivityAddMenuItemBinding
    private var menuItems: MenuItems? = null
    private var itemUnit: String? = ""
    private lateinit var bitmap: Bitmap

    private val PERMISSION_REQUEST_CODE = 200
    private var cartItems: CartItems? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMenuItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initClick()
        initView()
    }

    private fun initView() {
        if (intent.getStringExtra("type").equals("Cart")) {
            binding.spinnerUnits.visibility = View.VISIBLE
            binding.btImgUpload.visibility = View.VISIBLE
            binding.ivAddMenuItem.visibility = View.VISIBLE

            // Spinner click listener
            binding.spinnerUnits.setOnItemSelectedListener(this);
            cartItems = intent.extras?.getParcelable(MENU_ITEM_DATA)
            if (cartItems != null) { //this is for set data to form and update data
                binding.etItemName.setTextEditable(cartItems?.title ?: "")
                binding.etPrice.setTextEditable(cartItems?.price.toString() ?: "")
                binding.btDaveItem.text = getString(R.string.update)
            }

        } else {
            cartItems = intent.extras?.getParcelable(MenuItemsListActivity.MENU_ITEM_DATA)
            if (menuItems != null) { //this is for set data to form and update data
                binding.etItemName.setTextEditable(menuItems?.title ?: "")
                binding.etPrice.setTextEditable(menuItems?.price.toString() ?: "")
                binding.btDaveItem.text = getString(R.string.update)
            }
        }

    }

    private fun initClick() {
        binding.btDaveItem.setOnClickListener {
            if (intent.getStringExtra("type").equals("Cart")) {
                saveCartItemData()
            } else
                saveData()
        }
        binding.btImgUpload.setOnClickListener(View.OnClickListener {
            //intent to open camera app
            if (checkPermission()) {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                resultLauncher.launch(cameraIntent)
            } else {
                requestPermission()
            }


        })
    }

    var resultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            handleCameraImage(result.data)
        }
    }

    private fun handleCameraImage(intent: Intent?) {
        bitmap = intent?.extras?.get("data") as Bitmap
        binding.ivAddMenuItem.setImageBitmap(bitmap)
    }

    private fun saveCartItemData() {
        val id = if (cartItems != null) cartItems?.id else null
        val title = binding.etItemName.text.toString().trim()
        val price = binding.etPrice.text.toString().trim()

        if (title.isEmpty() || price.isEmpty()) {
            myToast(getString(R.string.form_empty))
            return
        }

        val cw = ContextWrapper(applicationContext)
        val directory = cw.getDir("imageDir", Context.MODE_PRIVATE)
        val file = File(directory, "image_"+binding.etItemName.text.toString()+ ".jpg")
        if (!file.exists()) {
            Log.d("path", file.toString())
            var fos: FileOutputStream? = null
            try {
                fos = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                fos.flush()
                fos.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        val cartItems = CartItems(id = id, title = title, price = Integer.parseInt(price), itemCount = 1, itemUnit = itemUnit,file.path)

        Coroutines.main {
            if (id != null) { //for update menuItems
                cartItemsListViewModel.updateCartItems(cartItems).also {
                    myToast("getString(R.string.success_update)")
                    finish()
                }
            } else { //for insert menuItems
                cartItemsListViewModel.insertCartItems(cartItems).also {
                    myToast("getString(R.string.success_save)")
                    finish()
                }
            }
        }
    }

    private fun saveData() {
        val id = if (menuItems != null) menuItems?.id else null
        val title = binding.etItemName.text.toString().trim()
        val price = binding.etPrice.text.toString().trim()

        if (title.isEmpty() || price.isEmpty()) {
            myToast(getString(R.string.form_empty))
            return
        }

        val menuItems = MenuItems(id = id, title = title, price = Integer.parseInt(price))

        Coroutines.main {
            if (id != null) { //for update menuItems
                viewModel.updateMenuItems(menuItems).also {
                    myToast(getString(R.string.success_update))
                    finish()
                }
            } else { //for insert menuItems
                viewModel.insertMenuItems(menuItems).also {
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

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        itemUnit = resources.getStringArray(R.array.units_array)[position]
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        itemUnit = ""
    }

    private fun checkPermission(): Boolean {
        val camera = ContextCompat.checkSelfPermission(applicationContext, CAMERA)
        val w_storage = ContextCompat.checkSelfPermission(applicationContext, WRITE_EXTERNAL_STORAGE)
        val r_storage = ContextCompat.checkSelfPermission(applicationContext, READ_EXTERNAL_STORAGE)
        return camera == PackageManager.PERMISSION_GRANTED && w_storage == PackageManager.PERMISSION_GRANTED && r_storage == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(CAMERA,WRITE_EXTERNAL_STORAGE,READ_EXTERNAL_STORAGE), PERMISSION_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> if (grantResults.size > 0) {
                val cameraAccepted = grantResults[0] === PackageManager.PERMISSION_GRANTED
                val storage_accepted = grantResults[1] === PackageManager.PERMISSION_GRANTED
                if (cameraAccepted && storage_accepted) {
                    val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    resultLauncher.launch(cameraIntent)
                    //Snackbar.make(binding.root, "Permission Granted, Now you can access location data and camera.", Snackbar.LENGTH_LONG).show()
                } else {
                    Snackbar.make(binding.root, "Permission Denied, You cannot access storage data and camera.", Snackbar.LENGTH_LONG).show()

                }
            }
        }
    }
}
