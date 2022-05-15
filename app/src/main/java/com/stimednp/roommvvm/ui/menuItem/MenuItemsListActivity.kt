package com.stimednp.roommvvm.ui.menuItem

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.stimednp.roommvvm.R
import com.stimednp.roommvvm.data.db.entity.Meals
import com.stimednp.roommvvm.data.db.entity.MenuItems
import com.stimednp.roommvvm.databinding.ActivityMenuItemsListBinding
import com.stimednp.roommvvm.ui.MealsActivity
import com.stimednp.roommvvm.ui.mess_home.MessHomeViewModel
import com.stimednp.roommvvm.utils.Coroutines
import com.stimednp.roommvvm.utils.SwipeHelper
import com.stimednp.roommvvm.utils.UtilExtensions
import com.stimednp.roommvvm.utils.UtilExtensions.logD
import com.stimednp.roommvvm.utils.UtilExtensions.myToast
import com.stimednp.roommvvm.utils.UtilExtensions.openActivity
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class MenuItemsListActivity : AppCompatActivity() {
    companion object {
        const val MENU_ITEM_DATA = "MENU_ITEM_DATA"
    }
    private val viewModel by viewModels<MenuItemsViewModel>()
    private val messHomeViewModel by viewModels<MessHomeViewModel>()
    private lateinit var binding: ActivityMenuItemsListBinding
    private var menuItems: MenuItems? = null

    private lateinit var menuListAdapter: MenuListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuItemsListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        menuListAdapter = MenuListAdapter{
//            openActivity(MealsActivity::class.java) {
//                putParcelable(MENU_ITEM_DATA, it)
//            }
            /*val intent = Intent(this, MealsActivity::class.java)

            resultLauncher.launch(intent)*/
            addMeal(it)
        }
        initView()
        observeMenuItem()
    }
    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        myToast("fsdfsdf")
        if (result.resultCode == Activity.RESULT_OK) {
            // There are no request codes
            val data: Intent? = result.data
            //doSomeOperations()
        }
    }
    private fun addItemToMeal(currentItem: MenuItems){
        var meals = Meals()
        meals.mealDate= Math.random().toString()
        meals.itemCount=5
        meals.messId=1
        meals.order_details=Gson().toJson(currentItem)
        meals.sumOfMeals=currentItem.price
        val intentWithResult = Intent().apply {
            putExtra("menu_item_data", currentItem)
        }
        setResult(RESULT_OK, intentWithResult)
        Coroutines.main {
            messHomeViewModel.insertMeal(meals)
        }




    }

    private fun addMeal(currentItem: MenuItems) {
        val datefromedit: String? = getIntent()?.getStringExtra("type")
        val date: String= datefromedit ?: SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        var mealItems: String =""
        Coroutines.main {
            mealItems = messHomeViewModel.getMealOfTheDay(date)
            if (mealItems == null || mealItems=="")
                mealItems = "[]"
            try {
                val jsonArray = JSONArray(mealItems)
                //jsonArray.put(new JSONObject(gson.toJson(currentItem)));
                //jsonArray.put(new JSONObject(gson.toJson(currentItem)));
                var sum: Int = currentItem.price
                var itemCount = 1

                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    sum += jsonObject.getInt("price") * jsonObject.getInt("itemCount")
                    if (jsonObject.getInt("id") == currentItem.id) {
                        itemCount = jsonObject.getInt("itemCount")
                        itemCount += 1
                        jsonObject.put("itemCount", itemCount)
                        jsonArray.put(i, jsonObject)
                    }
                }
                if (itemCount <= 1) {
                    currentItem.itemCount = itemCount
                    jsonArray.put(JSONObject(Gson().toJson(currentItem)))
                }

                val stringOfArray = jsonArray.toString()
                val meals = Meals()
                meals.order_details = stringOfArray
                meals.mealDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                if (datefromedit != null) {
                    meals.mealDate = datefromedit
                }
                meals.sumOfMeals = sum
                Coroutines.main {
                    messHomeViewModel.insertMeal(meals)
                    logD("Save here")
                    finish()
                }
            }catch (e:Exception){
                e.printStackTrace()
            }
        }


    }
    private fun initView() {
        binding.addMenuItemFab.setOnClickListener {
            openActivity(AddMenuItemActivity::class.java)
        }

        binding.rvMenuItemList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = menuListAdapter
        }

        val itemTouchHelper = ItemTouchHelper(object : SwipeHelper(binding.rvMenuItemList) {
            override fun instantiateUnderlayButton(position: Int): List<UnderlayButton> {
                var buttons = listOf<UnderlayButton>()
                val deleteButton = deleteButton(position)
                val editButton = editButton(position)
                    buttons = listOf(deleteButton,editButton)
                return buttons
            }
        })
        itemTouchHelper.attachToRecyclerView(binding.rvMenuItemList)

    }

    private fun deleteButton(position: Int) : SwipeHelper.UnderlayButton {
        return SwipeHelper.UnderlayButton(
            this,
            "Delete",
            14.0f,
            android.R.color.holo_red_light,
            object : SwipeHelper.UnderlayButtonClickListener {
                override fun onClick() {
                    val menu = menuListAdapter.getNoteAt(position)
                    Coroutines.io {
                        viewModel.deleteMenuItems(menu).also {
                            //myToast(getString(R.string.success_delete))
                        }
                    }
                }
            })
    }

    private fun observeMenuItem() {
        Coroutines.main {
            viewModel.getAllMenuItems().observe(this) {
                menuListAdapter.submitList(it)
            }
        }
    }
    private fun editButton(position: Int) : SwipeHelper.UnderlayButton {
        return SwipeHelper.UnderlayButton(
            this,
            "Edit",
            14.0f,
            android.R.color.holo_green_light,
            object : SwipeHelper.UnderlayButtonClickListener {
                override fun onClick() {
                    openActivity(AddMenuItemActivity::class.java) {
                        putParcelable(MENU_ITEM_DATA,menuListAdapter.getNoteAt(position))
                    }
                }
            })
    }
    private fun clearMenuItem() {
        val dialog = AlertDialog.Builder(this, R.style.ThemeOverlay_AppCompat_Dialog)
        dialog.setTitle(getString(R.string.clear_note))
            .setMessage(getString(R.string.sure_clear_items))
            .setPositiveButton(android.R.string.ok) { _, _ ->
                Coroutines.main {
                    viewModel.clearMenuItems().also {
                        myToast(getString(R.string.success_clear))
                    }
                }
            }.setNegativeButton(android.R.string.cancel, null).create().show()
    }

    override fun onCreateOptionsMenu(menu: Menu):Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.clearNoteItem -> clearMenuItem()
        }
        return super.onOptionsItemSelected(item)
    }
}