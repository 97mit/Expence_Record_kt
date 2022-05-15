package com.stimednp.roommvvm.ui.mess_home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.stimednp.roommvvm.R
import com.stimednp.roommvvm.databinding.FragmentMessHomeBinding
import com.stimednp.roommvvm.utils.Coroutines
import com.stimednp.roommvvm.utils.UtilExtensions.logD
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class MessHomeFragment : Fragment(),AdapterView.OnItemSelectedListener {
    private lateinit var viewPager: ViewPager2
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private var _binding: FragmentMessHomeBinding? = null
    private val viewModel by viewModels<MessHomeViewModel>()
    lateinit var default_year: String
    lateinit var months_of_default_year: List<String>
    lateinit var yearsOfMeals: Array<String>
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        _binding = FragmentMessHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        default_year = Calendar.getInstance()[Calendar.YEAR].toString()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Coroutines.main {
            initView()
        }
    }
    private fun tab_fragments(months_of_default_year:List<String>): MutableList<Fragment> {
        var frag_list = mutableListOf<Fragment>()
        for (month in months_of_default_year){
            val fragment = TabListFragment.newInstance(month,default_year)
            /*val args = Bundle()
            args.putString(ARG_MONTH,"04")
            args.putString(ARG_YEAR,"2022")
            fragment.arguments = args*/
            frag_list.add(fragment)
        }
        return frag_list
    }
    private suspend fun initView() {
            yearsOfMeals= viewModel.getAllYears()
            months_of_default_year = viewModel.getAllMonth(default_year)
            /*year spinner*/
            val adapter = activity?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, yearsOfMeals) }
            adapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spYearSelect.setAdapter(adapter)
            binding.spYearSelect.setOnItemSelectedListener(this)

            /*Tabs*/
            viewPager = binding.pager
            viewPager.offscreenPageLimit = 3
            viewPagerAdapter = ViewPagerAdapter(childFragmentManager, tab_fragments(months_of_default_year), lifecycle)
            viewPager.adapter = viewPagerAdapter
            TabLayoutMediator(binding.tabs, viewPager) { tab, position ->

                //var tabNames = resources.getStringArray(R.array.month_array)[months_of_default_year.get(position).toInt()]
                tab.text = resources.getStringArray(R.array.month_array)[months_of_default_year.get(position).toInt()]
            }.attach()

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_meal_list_fragment, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.edit_meals -> {
                /*Navigation to Edit meals*/
                var navController = view?.let { Navigation.findNavController(it) };
                val bundle = Bundle()
//                bundle.putString(ARG_MONTH, month)
//                bundle.putSerializable(ARG_YEAR, year)
//                bundle.putInt(SUM_OF_MONTH, sumOfTheMonth)
                navController?.navigate(R.id.action_navigation_home_to_navigation_edit_meal,bundle);
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
    }


    override fun onDetach() {
        super.onDetach()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        default_year = parent?.getSelectedItem() as String

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}