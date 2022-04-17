package com.example.mysubmissionbfaagithubuserapps.ui.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mysubmissionbfaagithubuserapps.R
import com.example.mysubmissionbfaagithubuserapps.database.FavoriteUser
import com.example.mysubmissionbfaagithubuserapps.databinding.ActivityFavoriteUserBinding
import com.example.mysubmissionbfaagithubuserapps.model.User
import com.example.mysubmissionbfaagithubuserapps.ui.detail.DetailActivity
import com.example.mysubmissionbfaagithubuserapps.ui.home.UserAdapter
import com.example.mysubmissionbfaagithubuserapps.ui.theme.ThemeActivity
import java.util.ArrayList

class FavoriteUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteUserBinding
    private lateinit var adapter: UserAdapter
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = resources.getText(R.string.favorite_User)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()
        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                Intent(this@FavoriteUserActivity, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailActivity.EXTRA_AVATAR, data.avatarUrl)
                    startActivity(it)
                }
            }
        })

        binding.apply {
            rvUser.setHasFixedSize(true)
            rvUser.layoutManager = LinearLayoutManager(this@FavoriteUserActivity)
            rvUser.adapter = adapter
        }

        viewModel.getUserFavorite()?.observe(this, {
            if (it != null) {
                val list = viewList(it)
                adapter.setList(list)
            }
        })
    }

    private fun viewList(users: List<FavoriteUser>): ArrayList<User> {
        val listUsers = ArrayList<User>()
        for (user in users) {
            val viewListUser = User(
                user.avatar_url,
                user.id,
                user.login
            )
            listUsers.add(viewListUser)
        }
        return listUsers
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