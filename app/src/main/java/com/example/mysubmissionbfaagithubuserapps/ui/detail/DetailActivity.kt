package com.example.mysubmissionbfaagithubuserapps.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.mysubmissionbfaagithubuserapps.R
import com.example.mysubmissionbfaagithubuserapps.database.FavoriteUser
import com.example.mysubmissionbfaagithubuserapps.databinding.ActivityDetailBinding
import com.example.mysubmissionbfaagithubuserapps.ui.favorite.FavoriteUserActivity
import com.example.mysubmissionbfaagithubuserapps.ui.theme.ThemeActivity
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.*


class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailUserViewModel
    private lateinit var favoriteUser: FavoriteUser
    private var flagFav: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Detail User"//(R.string.Detail_User.toString())

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val avatarUrl = intent.getStringExtra(EXTRA_AVATAR)
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        viewModel = ViewModelProvider(this).get(DetailUserViewModel::class.java)

        if (username != null) {
            viewModel.setUserDetail(username)
        }
        viewModel.getUserDetail().observe(this, {
            if (it != null) {
                binding.apply {
                    tvName.text = it.name
                    tvUsername.text = it.login
                    tvFollowers.text = "${it.followers}"
                    tvFollowing.text = "${it.following}"
                    tvRepository.text = "${it.repository}"
                    tvCompany.text = it.company
                    tvLocation.text = it.location
                    Glide.with(this@DetailActivity)
                        .load(it.avatarUrl)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .centerCrop()
                        .circleCrop()
                        .placeholder(R.drawable.loading)
                        .fallback(R.drawable.blank)
                        .error(R.drawable.error101)
                        .into(ivProfil)
                }

                favoriteUser = FavoriteUser(it.avatarUrl, it.id, it.login)//, it.avatarUrl)
            }

            CoroutineScope(Dispatchers.IO).launch {
                val count = viewModel.checkUserFavorite(id)
                withContext(Dispatchers.Main) {
                    if (count == 1) {
                        binding.fabFavorite.setImageDrawable(
                            ContextCompat.getDrawable(
                                applicationContext,
                                R.drawable.ic_favorite
                            )
                        )
                        viewModel.addToFavoriteUser(username.toString(), id, avatarUrl.toString())
                        flagFav = 1
                    }
                }
            }
        })

        binding.fabFavorite.setOnClickListener {
            if (flagFav == 1) {
                binding.fabFavorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.ic_favorite_border
                    )
                )
                viewModel.removeFavoriteUser(id)

                val snackBar = Snackbar.make(
                    it,
                    "User telah dihapus dari daftar Favorite",
                    Snackbar.LENGTH_LONG
                )
                with(snackBar) {
                    this.setAction("DISMIS") {
                        this.dismiss()
                    }
                    this.show()
                }
                flagFav = 0
            } else {
                binding.fabFavorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.ic_favorite
                    )
                )
                if (username != null) {
                    viewModel.addToFavoriteUser(username, id, avatarUrl.toString())
                }

                val snackBar =
                    Snackbar.make(it, "Sukses Menambahkan kedaftar Favorite", Snackbar.LENGTH_LONG)
                with(snackBar) {
                    this.setAction("DISMIS") {
                        this.dismiss()
                    }
                    this.show()
                }
                flagFav = 1
            }
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this, bundle)
        val viewPager: ViewPager2 = binding.viewpager
        viewPager.adapter = sectionsPagerAdapter
        val tab: TabLayout = binding.tabs

        TabLayoutMediator(tab, viewPager) { tabb, position ->
            tabb.text = resources.getString(TAB_TITLES[position])
        }.attach()
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

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_AVATAR = "extra_avatar"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_1,
            R.string.tab_2
        )
    }
}