package com.example.mysubmissionbfaagithubuserapps.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mysubmissionbfaagithubuserapps.R
import com.example.mysubmissionbfaagithubuserapps.databinding.ActivityMainBinding
import com.example.mysubmissionbfaagithubuserapps.model.User
import com.example.mysubmissionbfaagithubuserapps.ui.detail.DetailActivity
import com.example.mysubmissionbfaagithubuserapps.ui.favorite.FavoriteUserActivity
import com.example.mysubmissionbfaagithubuserapps.ui.theme.SettingPreferences
import com.example.mysubmissionbfaagithubuserapps.ui.theme.ThemeActivity
import com.example.mysubmissionbfaagithubuserapps.ui.theme.ThemeViewModel
import com.example.mysubmissionbfaagithubuserapps.ui.theme.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = resources.getText(R.string.app_name)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                Intent(this@MainActivity, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailActivity.EXTRA_AVATAR, data.avatarUrl)
                    startActivity(it)
                }
            }
        })

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MainViewModel::class.java
        )

        binding.apply {
            rvUser.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUser.setHasFixedSize(true)
            rvUser.adapter = adapter

            btnSearch.setOnClickListener {
                searchUsers()
            }

            tvSearch.setOnKeyListener { _, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    searchUsers()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }

        viewModel.getSearchUser().observe(this, {
            if (it != null) {
                adapter.setList(it)
                showLoading(false)
                binding.imgView.visibility = View.GONE
                binding.txtContent.visibility = View.GONE
            }
        })

        val pref = SettingPreferences.getInstance(dataStore)
        val themeViewModel =
            ViewModelProvider(this, ViewModelFactory(pref)).get(ThemeViewModel::class.java)

        themeViewModel.getThemeSettings().observe(this,
            { isDarkmodeActive: Boolean ->
                if (isDarkmodeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            })
    }

    private fun searchUsers() {
        binding.apply {
            val query = tvSearch.text.toString()
            if (query.isEmpty()) return
            showLoading(true)
            viewModel.setSearchUsers(query)
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
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