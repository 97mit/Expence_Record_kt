package com.stimednp.roommvvm.ui.mess_home

import android.os.Bundle
import android.support.v4.media.session.MediaSessionCompat.Token.fromBundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.Person.fromBundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.stimednp.roommvvm.data.db.entity.Meals
import com.stimednp.roommvvm.data.db.entity.MenuItems
import com.stimednp.roommvvm.databinding.FragmentMonthSummeryBinding
import com.stimednp.roommvvm.utils.Coroutines
import com.stimednp.roommvvm.utils.UtilExtensions.logD
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import androidx.core.app.Person
import androidx.media.AudioAttributesCompat.fromBundle

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MonthSummeryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class MonthSummeryFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var month: String = "01"
    private  var year: String="2022"
    private lateinit var sumOfTheMonth: String
    private lateinit var jsonObject: JSONObject
    private lateinit var mealOfMonth: List<Meals>
    private var _binding: FragmentMonthSummeryBinding? = null
    private val viewModel by viewModels<MessHomeViewModel>()

    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            month = it.getString(ARG_MONTH).toString()
            year = it.getString(ARG_YEAR).toString()
            sumOfTheMonth = it.getInt(SUM_OF_MONTH).toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMonthSummeryBinding.inflate(inflater, container, false)
        if (arguments != null) {
            // The getPrivacyPolicyLink() method will be created automatically.
            //val url: String = MessHomeFragment.fromBundle(arguments).getPrivacyPolicyLink()
        }
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        jsonObject = JSONObject()

        setupBill()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MonthSummeryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(month: String, year: String, sum_of_month:String) =
            MonthSummeryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_MONTH, month)
                    putString(ARG_YEAR, year)
                    putString(SUM_OF_MONTH, sum_of_month)
                }
            }
    }


    private fun setupBill() {
        binding.tvPrice.setText(sumOfTheMonth)
        Coroutines.main {
        var mealOfMonth: List<Meals> = viewModel.getAllMealOfMonthData(month, year)

        val allItemListOfMonth: MutableList<MenuItems> = ArrayList<MenuItems>()
        for (meals in mealOfMonth) {
            val gson = Gson()
            val type = object : TypeToken<List<MenuItems?>?>() {}.type
            val itemListOfDay: List<MenuItems> = gson.fromJson(meals.order_details, type)
            for (i in itemListOfDay.indices) {
                itemListOfDay[i].addedDate=(meals.mealDate)
            }
            allItemListOfMonth.addAll(itemListOfDay)
        }
        logD(Gson().toJson(mealOfMonth))
        val items: MutableList<MenuItems> = getAllUniqueEnemies(allItemListOfMonth) as MutableList<MenuItems>
            binding.rvTotalBill.layoutManager = LinearLayoutManager(requireContext())
            binding.rvTotalBill.setHasFixedSize(true)
        val billListAdapter = BillListAdapter(requireActivity(), items, jsonObject, object : BillListAdapter.ItemClickListener {
            override fun onItemClick(currentItem: MenuItems?) {

            }
        })
            binding.rvTotalBill.adapter = billListAdapter
        /*for (Note itemOfTheDay: list) {
            LinearLayout ll = new LinearLayout(this);
            ll.setOrientation(LinearLayout.HORIZONTAL);
            ll.setWeightSum(1);
            TextView textView = new TextView(this);
            textView.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,1));
            textView.setText(itemOfTheDay.getTitle()+"----");
            TextView textView1 = new TextView(this);
            textView1.setText(itemOfTheDay.getItemCount()+"X"+itemOfTheDay.getPriority()+"= "+getResources().getString(R.string.rupee)+itemOfTheDay.getItemCount()*itemOfTheDay.getPriority());
            View view  =new View(this);
            view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2));
            view.setBackgroundColor(this.getResources().getColor(R.color.black));
            ll.addView(textView);
            ll.addView(textView1);
            scrollViewContent.addView(ll);
            scrollViewContent.addView(view);
        }*/
        }
    }

    private fun getAllUniqueEnemies(list: List<MenuItems>): List<MenuItems> {
        val uniqueList: MutableList<MenuItems> = java.util.ArrayList<MenuItems>()
        val enemyIds: MutableList<Int> = java.util.ArrayList()
        for (i in list.indices) {
            if (!enemyIds.contains(list[i].id)) {
                list[i].id?.let { enemyIds.add(it) }
                uniqueList.add(list[i])
                try {
                    if (list[i].itemCount!! > 0) jsonObject?.put(java.lang.String.valueOf(list[i].id), JSONArray().put(list[i].addedDate))
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            } else {
                for (j in uniqueList.indices) {
                    val item: MenuItems = uniqueList[j]
                    if (item.id === list[i].id) {
                        uniqueList[j].itemCount = uniqueList[j].itemCount?.plus(list[i].itemCount!!)
                        try {
                            val jArr = jsonObject!!.getJSONArray(java.lang.String.valueOf(uniqueList[j].id))
                            if (list[i].itemCount!! > 0) jArr.put(list[i].addedDate)
                            jsonObject.put(java.lang.String.valueOf(uniqueList[j].id), jArr)
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }
                }
            }
        }
        logD(jsonObject.toString())
        return uniqueList
    }
}