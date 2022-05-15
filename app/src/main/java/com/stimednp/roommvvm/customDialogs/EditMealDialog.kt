package com.stimednp.roommvvm.customDialogs
import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import com.stimednp.roommvvm.data.db.entity.Meals
import com.stimednp.roommvvm.ui.mess_home.MessHomeViewModel
import com.stimednp.roommvvm.data.db.entity.MenuItems
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.sqlite.db.SupportSQLiteCompat.Api16Impl.cancel
import com.stimednp.roommvvm.R
import com.google.gson.Gson
import org.json.JSONArray
import com.google.gson.reflect.TypeToken
import com.stimednp.roommvvm.utils.Coroutines
import org.json.JSONException

/*class EditMealDialog(var activity: Activity, var currentMeal: Meals, private val mealViewModel: MessHomeViewModel) : Dialog(activity), AdapterView.OnItemSelectedListener {
    lateinit var tv_itemCount: TextView
    lateinit var yes: Button
    lateinit var no: Button
    lateinit var button_addItem: Button
    lateinit var button_removeItem: Button
    lateinit var mealsListOfTheDay: MutableList<MenuItems>
    private var selectedItem = 0
    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.edit_meal_dialog)
        yes = findViewById(R.id.btn_yes)
        no = findViewById(R.id.btn_no)
        button_addItem = findViewById(R.id.bt_addItem)
        button_removeItem = findViewById(R.id.bt_removeItem)
        tv_itemCount = findViewById(R.id.tv_itemCount)
        yes.setOnClickListener(View.OnClickListener {
            var sumOfMealsOftheDay = 0
            for (i in mealsListOfTheDay!!.indices) {
                val meals = mealsListOfTheDay!![i]
                sumOfMealsOftheDay += meals.price * meals.itemCount!!
                if (meals.itemCount!! <= 0) mealsListOfTheDay!!.remove(meals)
            }
            currentMeal.sumOfMeals = sumOfMealsOftheDay
            currentMeal.order_details = Gson().toJson(mealsListOfTheDay)
            Coroutines.io {
                mealViewModel.insertMeal(currentMeal)
            }
            dismiss()
        })
        no.setOnClickListener(View.OnClickListener { dismiss() })
        var mealsOfTheDay = arrayOf<String?>()
        try {
            val jsonArray = JSONArray(currentMeal.order_details)
            val listType = object : TypeToken<List<MenuItems?>?>() {}.type
            mealsListOfTheDay = Gson().fromJson(currentMeal.order_details, listType)
            mealsOfTheDay = arrayOfNulls(mealsListOfTheDay.size)
            for (i in mealsListOfTheDay.indices) {
                //JSONObject jsonObject = jsonArray.getJSONObject(i);
                mealsOfTheDay[i] = mealsListOfTheDay.get(i).title
            }
            tv_itemCount.setText(mealsListOfTheDay.get(selectedItem).itemCount.toString())
        } catch (exception: JSONException) {
            exception.printStackTrace()
        }
        button_addItem.setOnClickListener(View.OnClickListener {
            var itemCount = mealsListOfTheDay[selectedItem].itemCount!!
            if (itemCount > -1 && itemCount <= 200) itemCount += 1
            mealsListOfTheDay[selectedItem].itemCount = itemCount
            tv_itemCount.setText(mealsListOfTheDay[selectedItem].itemCount.toString())
        })
        button_removeItem.setOnClickListener(View.OnClickListener {
            var itemCount = mealsListOfTheDay[selectedItem].itemCount!!
            if (itemCount > 0) itemCount -= 1
            mealsListOfTheDay[selectedItem].itemCount = itemCount
            tv_itemCount.setText(mealsListOfTheDay[selectedItem].itemCount.toString())
        })
        val spinner = findViewById<Spinner>(R.id.mealsSpinner)
        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, mealsOfTheDay)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = this
    }

    override fun onItemSelected(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
        Log.d("Tag===", "onItemSelected==$i")
        //mealsListOfTheDay.get(i).setItemCount();
        selectedItem = i
        tv_itemCount.text = mealsListOfTheDay[selectedItem].itemCount.toString()
    }

    override fun onNothingSelected(adapterView: AdapterView<*>?) {
        Log.d("Tag===", "onNothingSelected==")
        tv_itemCount.text = mealsListOfTheDay[selectedItem].itemCount.toString()
    }
}*/

class EditMealDialog(var currentMeal: Meals, private val mealViewModel: MessHomeViewModel) : DialogFragment(), AdapterView.OnItemSelectedListener {
    lateinit var tv_itemCount: TextView
    lateinit var yes: Button
    lateinit var no: Button
    lateinit var button_addItem: Button
    lateinit var button_removeItem: Button
    lateinit var mealsListOfTheDay: MutableList<MenuItems>
    private var selectedItem = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            var newPlantView = inflater.inflate(R.layout.edit_meal_dialog, null)
            button_addItem = newPlantView.findViewById(R.id.bt_addItem)
            button_removeItem = newPlantView.findViewById(R.id.bt_removeItem)
            tv_itemCount = newPlantView.findViewById(R.id.tv_itemCount)
            var mealsOfTheDay = arrayOf<String?>()
            try {
                val jsonArray = JSONArray(currentMeal.order_details)
                val listType = object : TypeToken<List<MenuItems?>?>() {}.type
                mealsListOfTheDay = Gson().fromJson(currentMeal.order_details, listType)
                mealsOfTheDay = arrayOfNulls(mealsListOfTheDay.size)
                for (i in mealsListOfTheDay.indices) {
                    //JSONObject jsonObject = jsonArray.getJSONObject(i);
                    mealsOfTheDay[i] = mealsListOfTheDay.get(i).title
                }
                tv_itemCount.setText(mealsListOfTheDay.get(selectedItem).itemCount.toString())
            } catch (exception: JSONException) {
                exception.printStackTrace()
            }

            button_addItem.setOnClickListener(View.OnClickListener {
                var itemCount = mealsListOfTheDay[selectedItem].itemCount!!
                if (itemCount > -1 && itemCount <= 200) itemCount += 1
                mealsListOfTheDay[selectedItem].itemCount = itemCount
                tv_itemCount.setText(mealsListOfTheDay[selectedItem].itemCount.toString())
            })
            button_removeItem.setOnClickListener(View.OnClickListener {
                var itemCount = mealsListOfTheDay[selectedItem].itemCount!!
                if (itemCount > 0) itemCount -= 1
                mealsListOfTheDay[selectedItem].itemCount = itemCount
                tv_itemCount.setText(mealsListOfTheDay[selectedItem].itemCount.toString())
            })
            val spinner = newPlantView.findViewById<Spinner>(R.id.mealsSpinner)
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, mealsOfTheDay)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
            spinner.onItemSelectedListener = this

            builder.setView(newPlantView)
                .setPositiveButton(getString(R.string.update), DialogInterface.OnClickListener{ dialog, which ->
                    var sumOfMealsOftheDay = 0
                    for (i in mealsListOfTheDay!!.indices) {
                        val meals = mealsListOfTheDay!![i]
                        sumOfMealsOftheDay += meals.price * meals.itemCount!!
                        if (meals.itemCount!! <= 0) mealsListOfTheDay!!.remove(meals)
                    }
                    currentMeal.sumOfMeals = sumOfMealsOftheDay
                    currentMeal.order_details = Gson().toJson(mealsListOfTheDay)
                    Coroutines.io {
                        mealViewModel.insertMeal(currentMeal)
                    }
                    getDialog()?.cancel()
                })
                .setNegativeButton(getString(R.string.cancel), DialogInterface.OnClickListener { dialog, which ->
                    getDialog()?.cancel()
                })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onItemSelected(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
        Log.d("Tag===", "onItemSelected==$i")
        //mealsListOfTheDay.get(i).setItemCount();
        selectedItem = i
        tv_itemCount.text = mealsListOfTheDay[selectedItem].itemCount.toString()
    }

    override fun onNothingSelected(adapterView: AdapterView<*>?) {
        Log.d("Tag===", "onNothingSelected==")
        tv_itemCount.text = mealsListOfTheDay[selectedItem].itemCount.toString()
    }

}