package com.mirea.mitrofanovms.mireaproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.mirea.mitrofanovms.mireaproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        binding.appBarMain.fab.setOnClickListener { view ->
            when (findNavController(R.id.nav_host_fragment_content_main).currentDestination?.id) {
                R.id.nav_files -> showCreateFileDialog()
                else -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null)
                    .setAnchorView(R.id.fab).show()
            }
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,R.id.nav_data, R.id.nav_webview, R.id.nav_work, R.id.nav_camera, R.id.nav_microphone,
                R.id.nav_sensor
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
    private fun showCreateFileDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_create_file, null)
        val etDialogFileName = dialogView.findViewById<EditText>(R.id.etDialogFileName)

        AlertDialog.Builder(this)
            .setTitle("Создать новый файл")
            .setView(dialogView)
            .setPositiveButton("Создать") { _, _ ->
                val filename = etDialogFileName.text.toString()
                if (filename.isNotEmpty()) {
                    findNavController(R.id.nav_host_fragment_content_main).navigate(R.id.nav_files)
                }
            }
            .setNegativeButton("Отмена", null)
            .show()
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
