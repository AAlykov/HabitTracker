package com.tomsk.alykov.habittracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}


//setupActionBarWithNavController(findNavController(R.id.navHostfragment))


/*override fun onSupportNavigateUp(): Boolean {
    val navController = findNavController(R.id.navHostfragment)
    return navController.navigateUp() || super.onSupportNavigateUp()
}*/