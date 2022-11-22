package com.tomsk.alykov.habittracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.tomsk.alykov.habittracker.ui.infoscreen.IntroActivity

class MainActivity : AppCompatActivity() {
    private var userFirstTime = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadData()

        if (userFirstTime) {
            userFirstTime = false
            saveData()

            //val i = Intent(this, IntroActivity::class.java)
            //startActivity(i)
            startActivity(Intent(this, IntroActivity::class.java))
            finish()
        }

        setupActionBarWithNavController(findNavController(R.id.navHostfragment))
    }

    private fun loadData() {
        val sp = getSharedPreferences("SHARED_PREFS", MODE_PRIVATE)
        userFirstTime = sp.getBoolean("BOOLEAN_FIRST_TIME", true)
    }

    private fun saveData() {
        val sp = getSharedPreferences("SHARED_PREFS", MODE_PRIVATE)
        sp.edit().apply {
            putBoolean("BOOLEAN_FIRST_TIME", userFirstTime)
            apply()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.navHostfragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}





