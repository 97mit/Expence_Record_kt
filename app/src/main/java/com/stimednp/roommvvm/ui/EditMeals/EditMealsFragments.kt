package com.stimednp.roommvvm.ui.EditMeals
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.stimednp.roommvvm.R
import com.stimednp.roommvvm.data.db.entity.MenuItems
import com.stimednp.roommvvm.databinding.FragmentEditMealsFragmentsBinding
import com.stimednp.roommvvm.databinding.FragmentMessHomeBinding
import com.stimednp.roommvvm.ui.menuItem.MenuItemsListActivity
import com.stimednp.roommvvm.utils.Coroutines
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import androidx.lifecycle.Observer

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EditMealsFragments.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class EditMealsFragments : Fragment() {
    // TODO: Rename and change types of parameters
    var mealListStr: String = "[]"
    private val viewModel by viewModels<EditMealViewModel>()
    var myFormat = "yyyy-MM-dd"
    var dateFormat = SimpleDateFormat(myFormat, Locale.US)
    val myCalendar = Calendar.getInstance()
    private var _binding: FragmentEditMealsFragmentsBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        _binding = FragmentEditMealsFragmentsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val date = OnDateSetListener { view, year, month, day ->
            myCalendar[Calendar.YEAR] = year
            myCalendar[Calendar.MONTH] = month
            myCalendar[Calendar.DAY_OF_MONTH] = day
            updateLabel()
        }
        binding.etMealDateSelect.setOnClickListener {
            DatePickerDialog(
                requireActivity(),
                date,
                myCalendar[Calendar.YEAR],
                myCalendar[Calendar.MONTH],
                myCalendar[Calendar.DAY_OF_MONTH]
            ).show()
        }

        Coroutines.main {
            viewModel.getAllLiveMealOfTheDay(dateFormat.format(myCalendar.time)).observe(requireActivity(),Observer<String>{
                updateLabel()
            })
        }


        //editMealViewModel.getAllMealsOfTheDay(dateFormat.format(myCalendar.time)).observe(this, androidx.lifecycle.Observer<String?> { updateLabel() })

    }


    private fun updateLabel() {
        binding.llDetailsView.removeAllViews()
        binding.etMealDateSelect.setText(dateFormat.format(myCalendar.time))
        val type = object : TypeToken<List<MenuItems?>?>() {}.type
        Coroutines.main {
            mealListStr = viewModel.getAllMealOfTheDay(dateFormat.format(myCalendar.time))?:"[]"

            val mealList: List<MenuItems> = Gson().fromJson<List<MenuItems>>(mealListStr, type)
            for (meal in mealList) {
                //totalSum+=meal.getPriority()*meal.getItemCount();
                val ll = LinearLayout(context)
                ll.orientation = LinearLayout.HORIZONTAL
                val tv_title = TextView(context)
                tv_title.setText(meal.title)
                tv_title.setPadding(5, 5, 5, 5)
                tv_title.textSize = 15f
                tv_title.width = 200
                val params: ViewGroup.LayoutParams = TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f)
                tv_title.layoutParams = params
                val tv_count = TextView(context)
                tv_count.setText(java.lang.String.valueOf(meal.itemCount).toString() + "X " + resources.getString(R.string.rupee) + java.lang.String.valueOf(meal.price) + " = ")
                tv_count.setPadding(5, 5, 5, 5)
                tv_count.textSize = 15f
                tv_count.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                val tv_price = TextView(context)
                tv_price.text = resources.getString(R.string.rupee) + java.lang.String.valueOf(meal.price * meal.itemCount!!)
                tv_price.setPadding(10, 5, 5, 5)
                tv_price.textSize = 15f
                tv_price.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                ll.addView(tv_title)
                ll.addView(tv_count)
                ll.addView(tv_price)
                binding.llDetailsView.addView(ll)
                //tv.clearComposingText();
            }
            binding.fabAddItem.setOnClickListener {
                val intent = Intent(context, MenuItemsListActivity::class.java)
                intent.putExtra("type", dateFormat.format(myCalendar.time))
                startActivity(intent)
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
         * @return A new instance of fragment EditMealsFragments.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EditMealsFragments().apply {
                arguments = Bundle().apply {

                }
            }
    }
}