package com.chenguang.pokedex.pokemonlist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.chenguang.pokedex.R
import com.chenguang.pokedex.databinding.LayoutPokemonInfoItemBinding
import com.chenguang.pokedex.db.model.PokemonInfo

class PokemonInfoPagingDataAdapter(
    context: Context,
    private val onItemClicked: (item: PokemonInfo, sharedView: View) -> Unit
) : PagingDataAdapter<PokemonInfo, PokemonInfoPagingDataAdapter.PokemonInfoViewHolder>(PokemonInfoDiffUtil()) {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonInfoViewHolder {
        val view = layoutInflater.inflate(R.layout.layout_pokemon_info_item, parent, false)
        return PokemonInfoViewHolder(view, onItemClicked)
    }

    override fun onBindViewHolder(holder: PokemonInfoViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onViewRecycled(holder: PokemonInfoViewHolder) {
        holder.clear()
    }

    class PokemonInfoViewHolder(
        view: View,
        private val onItemClicked: (item: PokemonInfo, sharedView: View) -> Unit
    ) : RecyclerView.ViewHolder(view) {

        private val binding = LayoutPokemonInfoItemBinding.bind(itemView)

        fun bind(item: PokemonInfo?) {
            with(binding) {
                if (item == null) {
                    pokemonInfoItemTextView.text = ""
                    pokemonInfoItemImageView.setImageDrawable(null)
                    pokemonInfoItemCardView.setOnClickListener(null)
                } else {
                    pokemonInfoItemTextView.text = item.name
                    Glide.with(itemView)
                        .load(item.getImageUrl())
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .dontTransform()
                        .into(pokemonInfoItemImageView)
                    ViewCompat.setTransitionName(pokemonInfoItemImageView, item.name)
                    pokemonInfoItemCardView.setOnClickListener {
                        onItemClicked(item, pokemonInfoItemImageView)
                    }
                }
            }
        }

        fun clear() {
            Glide.with(itemView)
                .clear(binding.pokemonInfoItemImageView)
        }
    }

    class PokemonInfoDiffUtil : DiffUtil.ItemCallback<PokemonInfo>() {
        override fun areItemsTheSame(oldItem: PokemonInfo, newItem: PokemonInfo): Boolean {
            return oldItem.pokemonId == newItem.pokemonId
        }

        override fun areContentsTheSame(oldItem: PokemonInfo, newItem: PokemonInfo): Boolean {
            return oldItem == newItem
        }
    }
}
