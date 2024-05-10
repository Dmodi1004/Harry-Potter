package com.example.harrypotter.views.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentManager
import com.example.harrypotter.R
import com.example.harrypotter.databinding.ActivityMainBinding
import com.example.harrypotter.utils.changeFragmentWithBack
import com.example.harrypotter.utils.changeMainFunctions
import com.example.harrypotter.views.fragments.BooksFragment
import com.example.harrypotter.views.fragments.CharactersFragment
import com.example.harrypotter.views.fragments.InfoFragment
import com.example.harrypotter.views.fragments.SpellFragment


class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val fragmentManager: FragmentManager
        get() = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(2000)
        installSplashScreen()
        setContentView(binding.root)

        changeMainFunctions(this, BooksFragment())

        binding.bottomNavigation.setOnItemSelectedListener { menuItem ->

            when (menuItem.itemId) {
                R.id.books -> {
                    changeMainFunctions(this, BooksFragment())
                }

                R.id.characters -> {
                    changeFragmentWithBack(this, CharactersFragment())
                }

                R.id.spell -> {
                    changeFragmentWithBack(this, SpellFragment())
                }

                R.id.info -> {
                    changeFragmentWithBack(this, InfoFragment())
                }
            }
            return@setOnItemSelectedListener true
        }
        fragmentManager.addOnBackStackChangedListener {
            updateBottomNavigationIndicator()
        }
    }

    private fun updateBottomNavigationIndicator() {
        val currentFragment = fragmentManager.findFragmentById(R.id.frameLayout)
        val selectedItemId = when (currentFragment) {
            is BooksFragment -> R.id.books
            is CharactersFragment -> R.id.characters
            is SpellFragment -> R.id.spell
            is InfoFragment -> R.id.info
            else -> return
        }
        binding.bottomNavigation.menu.findItem(selectedItemId)?.isChecked = true
    }

}