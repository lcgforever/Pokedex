package com.chenguang.pokedex.pokemonlist

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.chenguang.pokedex.databinding.ActivityPokemonListBinding
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
        binding.pokemonListActivitySwipeRefreshLayout.setOnRefreshListener {
            pagingAdapter.refresh()
        }

        pagingAdapter = PokemonInfoPagingDataAdapter(this)
        val headerAdapter = PokemonLoadStateAdapter(this@PokemonListActivity, pagingAdapter::retry)
        val footerAdapter = PokemonLoadStateAdapter(this@PokemonListActivity, pagingAdapter::retry)
        binding.pokemonListActivityRecyclerView.adapter = pagingAdapter.withLoadStateHeaderAndFooter(
            header = headerAdapter,
            footer = footerAdapter
        )
        val gridLayoutManager = GridLayoutManager(this, GRID_SPAN_COUNT)
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
        binding.pokemonListActivityRecyclerView.layoutManager = gridLayoutManager

        lifecycleScope.launch {
            viewModel.pokemonListDataFlow.collectLatest { pagingData ->
                binding.pokemonListActivitySwipeRefreshLayout.isRefreshing = false
                pagingAdapter.submitData(pagingData)
            }
        }
    }
}
