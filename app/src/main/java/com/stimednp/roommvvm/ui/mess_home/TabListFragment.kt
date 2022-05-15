package com.stimednp.roommvvm.ui.mess_home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stimednp.roommvvm.R
import com.stimednp.roommvvm.customDialogs.EditMealDialog
import com.stimednp.roommvvm.data.db.entity.Meals
import com.stimednp.roommvvm.databinding.FragmentTabListBinding
import dagger.hilt.android.AndroidEntryPoint


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
const val ARG_MONTH = "month"
const val ARG_YEAR = "year"
const val SUM_OF_MONTH = "sum_of_month"

/**
 * A simple [Fragment] subclass.
 * Use the [TabListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class TabListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var month: String = "01"
    private  var year: String="2022"
    private lateinit var binding: FragmentTabListBinding
    var sumOfTheMonth = 0
    private val viewModel by viewModels<MessHomeViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            month = it.getString(ARG_MONTH).toString()
            year = it.getString(ARG_YEAR).toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rv_mealList: RecyclerView = view.findViewById(R.id.rv_mealList)
        val tv_price = view.findViewById<TextView>(R.id.tv_price)
        rv_mealList.layoutManager = LinearLayoutManager(activity)
        rv_mealList.setHasFixedSize(true)
        val mealsAdapter = MealListAdapter(activity, object : MealListAdapter.ItemClickListener {
            override fun onItemClick(currentMeal: Meals) {
                val cdd =  EditMealDialog(currentMeal, viewModel)
                cdd.show(childFragmentManager, "New Plant")
            }
        })
        rv_mealList.adapter = mealsAdapter

        viewModel.getAllMealsOfMonth(month, year).observe(viewLifecycleOwner, Observer<List<Meals>?> { meals -> //textView.setText(s);
            mealsAdapter.setMeals(meals)

            assert(meals != null)
            for (meal in meals!!) {
                sumOfTheMonth += meal.sumOfMeals!!
            }
            tv_price.text = StringBuilder().append(resources.getText(R.string.rupee)).append(sumOfTheMonth.toString()).toString()
            tv_price.setOnClickListener {
                var navController = Navigation.findNavController(view);
                val bundle = Bundle()
                bundle.putString(ARG_MONTH, month)
                bundle.putSerializable(ARG_YEAR, year)
                bundle.putInt(SUM_OF_MONTH, sumOfTheMonth)
                navController.navigate(R.id.action_navigation_home_to_navigation_home_month_summary,bundle);

            }
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TabListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(month: String, year: String) =
            TabListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_MONTH, month)
                    putString(ARG_YEAR, year)
                }
            }
    }
}