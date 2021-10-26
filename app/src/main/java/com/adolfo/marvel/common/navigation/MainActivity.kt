package com.adolfo.marvel.common.navigation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import com.adolfo.core.extensions.gone
import com.adolfo.core.extensions.isVisible
import com.adolfo.marvel.common.extensions.viewBinding
import com.adolfo.core.extensions.visible
import com.adolfo.marvel.R
import com.adolfo.marvel.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initNavigation()
        initListeners()
    }

    private fun initNavigation() {
        setSupportActionBar(binding.toolbar)

        val navHostFragment = supportFragmentManager
            .findFragmentById(binding.fragmentContainer.id) as NavHostFragment
        navController = navHostFragment.navController
    }

    private fun initListeners() {
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        navController.addOnDestinationChangedListener { _, dest, _ ->
            supportActionBar?.title = when (dest.id) {
                R.id.fragmentCharacters -> getString(R.string.empty)
                else -> supportActionBar?.title
            }

            binding.toolbar.visibility = when (dest.id) {
                R.id.fragmentCharacterDetail -> View.VISIBLE
                else -> View.GONE
            }
        }
    }

    fun navigateTo(direction: NavDirections) {
        navController.navigate(direction)
    }

    fun navigateBack() {
        navController.popBackStack()
    }

    fun setToolbarTitle(title: String) {
        supportActionBar?.title = title
    }

    fun showLoader(show: Boolean) {
        if (binding.loader.isVisible() != show && binding.animation.isAnimating != show) {
            if (show) {
                binding.animation.playAnimation()
                binding.loader.visible()
            } else {
                binding.loader.gone()
            }
        }
    }
}
