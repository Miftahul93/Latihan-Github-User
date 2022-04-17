package com.example.mysubmissionbfaagithubuserapps.ui.theme

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.mysubmissionbfaagithubuserapps.R
import com.example.mysubmissionbfaagithubuserapps.databinding.ActivityThemeBinding
import com.example.mysubmissionbfaagithubuserapps.ui.favorite.FavoriteUserActivity


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class ThemeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityThemeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThemeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = resources.getText(R.string.pengaturan)

        val switchTheme = binding.switchTheme

        val pref = SettingPreferences.getInstance(dataStore)
        val themeViewModel =
            ViewModelProvider(this, ViewModelFactory(pref)).get(ThemeViewModel::class.java)

        themeViewModel.getThemeSettings().observe(this,
            { isDarkModeActive: Boolean ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    switchTheme.isChecked = true
                    Toast.makeText(applicationContext, "Darkmode Aktif", Toast.LENGTH_SHORT).show()
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    switchTheme.isChecked = false
                    Toast.makeText(applicationContext, "Darkmode Non Aktif", Toast.LENGTH_SHORT).show()
                }
            })

        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            themeViewModel.saveThemeSettings(isChecked)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings -> {
                Intent(this, ThemeActivity::class.java).also {
                    startActivity(it)
                }
            }

            R.id.btn_favorite -> {
                Intent(this, FavoriteUserActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}