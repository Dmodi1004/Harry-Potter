package com.example.harrypotter.utils

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.harrypotter.R

const val BASE_URL = "https://harry-potter-api-en.onrender.com/"

fun Activity.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun getBookImageResourceId(bookId: Int): Int {
    return bookImageMap[bookId] ?: R.drawable.hp_splash
}

private val bookImageMap = mapOf(
    1 to R.drawable.harry_potter_and_the_philosophers_stone,
    2 to R.drawable.harry_potter_and_the_chamber_of_secrets,
    3 to R.drawable.harry_potter_and_the_prisoner_of_azkaban,
    4 to R.drawable.harry_potter_and_the_goblet_of_fire,
    5 to R.drawable.harry_potter_and_the_order_of_the_phoenix,
    6 to R.drawable.harry_potter_and_the_half_blood_prince,
    7 to R.drawable.harry_potter_and_the_deathly_hallows,
    8 to R.drawable.harry_potter_and_the_cursed_child
)

fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    val networkInfo = connectivityManager?.activeNetworkInfo
    return networkInfo != null && networkInfo.isConnected
}

fun changeMainFunctions(fragmentActivity: FragmentActivity, fragment: Fragment) {
    fragmentActivity.supportFragmentManager
        .beginTransaction()
        .replace(R.id.frameLayout, fragment)
        .commit()
}

fun changeFragmentWithBack(fragmentActivity: FragmentActivity, fragment: Fragment) {
    fragmentActivity.supportFragmentManager
        .beginTransaction()
        .replace(R.id.frameLayout, fragment)
        .addToBackStack(null)
        .commit()
}