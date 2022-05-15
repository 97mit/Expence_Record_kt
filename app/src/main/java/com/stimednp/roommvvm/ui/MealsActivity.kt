package com.stimednp.roommvvm.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.stimednp.roommvvm.R
import com.stimednp.roommvvm.databinding.ActivityMealsBinding
import com.stimednp.roommvvm.ui.menuItem.MenuItemsListActivity
import com.stimednp.roommvvm.ui.mess_home.MessHomeFragment
import com.stimednp.roommvvm.utils.UtilExtensions.logD
import com.stimednp.roommvvm.utils.UtilExtensions.myToast
import dagger.hilt.android.AndroidEntryPoint
import java.lang.reflect.Array.get


@AndroidEntryPoint
class MealsActivity : AppCompatActivity() {
    companion object {
        const val REQUEST_RESULT = 1
    }

    private lateinit var binding: ActivityMealsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.addItemFab.setOnClickListener {
            startForResult.launch(Intent(this,MenuItemsListActivity::class.java), ActivityOptionsCompat.makeBasic() )
        }
        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_mess)
        navView.setupWithNavController(navController)
        var navController_e : NavController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_mess)
        navController_e.addOnDestinationChangedListener { controller, destination, arguments ->
            if(destination.label?.equals("Home") != true){
                binding.addItemFab.visibility = View.GONE
            }else{
                binding.addItemFab.visibility = View.VISIBLE
            }
        }
    }

    private var startForResult= registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result: ActivityResult ->
        logD("startForResult==MealActivity")
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            // Handle the Intent
            //do stuff here
        }
    }
    override fun onStop() {
        super.onStop()
        startForResult.unregister()
    }

    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        MessHomeFragment().onActivityResult(requestCode, resultCode, data)
    }
    fun requestResult() {
        startActivityForResult(MenuItemsListActivity.newInstance(this), REQUEST_RESULT)
    }*/

}