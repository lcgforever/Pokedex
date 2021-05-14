package com.chenguang.pokedex.pokemonlist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chenguang.pokedex.R
import com.chenguang.pokedex.databinding.LayoutLoadingStateItemBinding

class PokemonLoadStateAdapter(
    context: Context,
    private val retry: () -> Unit
) : LoadStateAdapter<PokemonLoadStateAdapter.LoadStateViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val view = layoutInflater.inflate(R.layout.layout_loading_state_item, parent, false)
        return LoadStateViewHolder(view, retry)
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    class LoadStateViewHolder(view: View, retry: () -> Unit) : RecyclerView.ViewHolder(view) {

        private val binding = LayoutLoadingStateItemBinding.bind(itemView)

        init {
            binding.loadingStateItemRetryButton.setOnClickListener { retry() }
        }

        fun bind(loadState: LoadState) {
            with(binding) {
                if (loadState is LoadState.Error) {
                    loadingStateItemErrorText.text = loadState.error.localizedMessage
                }
                loadingStateItemProgressBar.isVisible = loadState is LoadState.Loading
                loadingStateItemErrorContainer.isVisible = loadState is LoadState.Error
            }
        }
    }
}
