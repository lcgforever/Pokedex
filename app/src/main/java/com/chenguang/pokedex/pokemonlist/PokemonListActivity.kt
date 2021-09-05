package com.chenguang.pokedex.pokemonlist

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chenguang.pokedex.databinding.ActivityPokemonListBinding
import com.chenguang.pokedex.db.model.PokemonInfo
import com.chenguang.pokedex.pokemondetails.PokemonDetailsActivity
import com.chenguang.pokedex.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val GRID_SPAN_COUNT = 2

@AndroidEntryPoint
class PokemonListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPokemonListBinding
    private lateinit var pagingAdapter: PokemonInfoPagingDataAdapter

    private val viewModel: PokemonListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPokemonListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        val activity = this
        with(binding) {
            setSupportActionBar(pokemonListActivityToolbar)

            pokemonListActivitySwipeRefreshLayout.setOnRefreshListener {
                pagingAdapter.refresh()
            }

            pagingAdapter = PokemonInfoPagingDataAdapter(activity, activity::onPokemonItemClicked)
            val headerAdapter = PokemonLoadStateAdapter(activity, pagingAdapter::retry)
            val footerAdapter = PokemonLoadStateAdapter(activity, pagingAdapter::retry)
            pokemonListActivityRecyclerView.adapter = pagingAdapter.withLoadStateHeaderAndFooter(
                header = headerAdapter,
                footer = footerAdapter
            )
            val gridLayoutManager = GridLayoutManager(activity, GRID_SPAN_COUNT)
            gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (position == pagingAdapter.itemCount && footerAdapter.itemCount > 0) {
                        2
                    } else if (position == 0 && headerAdapter.itemCount > 0) {
                        2
                    } else {
                        1
                    }
                }
            }
            pokemonListActivityRecyclerView.layoutManager = gridLayoutManager
            pokemonListActivityRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val position = gridLayoutManager.findFirstVisibleItemPosition()
                    pokemonListActivityFab.isVisible = position > 0
                }
            })

            pokemonListActivityFab.setOnClickListener {
                gridLayoutManager.scrollToPosition(0)
            }

            lifecycleScope.launch {
                viewModel.pokemonListDataFlow.collectLatest { pagingData ->
                    pokemonListActivitySwipeRefreshLayout.isRefreshing = false
                    pagingAdapter.submitData(pagingData)
                }
            }
        }
    }

    private fun onPokemonItemClicked(pokemonInfo: PokemonInfo, sharedView: View) {
        val intent = Intent(this, PokemonDetailsActivity::class.java).apply {
            putExtra(Constants.INTENT_EXTRA_KEY_POKEMON_ID, pokemonInfo.pokemonId)
            putExtra(Constants.INTENT_EXTRA_KEY_POKEMON_NAME, pokemonInfo.name)
            putExtra(Constants.INTENT_EXTRA_KEY_POKEMON_IMAGE, pokemonInfo.getImageUrl())
        }
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this,
            Pair.create(
                binding.pokemonListActivityToolbar,
                ViewCompat.getTransitionName(binding.pokemonListActivityToolbar)
            ),
            Pair.create(sharedView, ViewCompat.getTransitionName(sharedView))
        )
        startActivity(intent, options.toBundle())
    }
}
