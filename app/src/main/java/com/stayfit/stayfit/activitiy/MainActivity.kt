package com.stayfit.stayfit.activitiy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.stayfit.stayfit.R
import com.stayfit.stayfit.databinding.ActivityMainBinding
import com.stayfit.stayfit.util.Utils

class MainActivity : AppCompatActivity() {

    private var binding : ActivityMainBinding? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding!!.root
        setContentView(view)

        Utils.createList(this)

        val intent = intent
        val definite = intent.getIntExtra("definite",0)
        binding!!.bottomNavView.menu.clear()

        if (definite == 3 || definite == 4){
            binding!!.bottomNavView.inflateMenu(R.menu.nav_menu_pt)
        } else {
            binding!!.bottomNavView.inflateMenu(R.menu.nav_menu_user)
        }

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView2) as NavHostFragment
        val navController = navHostFragment.navController
        //val navController = this.findNavController(R.id.fragmentContainerView2)
        binding!!.bottomNavView.setupWithNavController(navController)
    }
}