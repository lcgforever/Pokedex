package com.chenguang.pokedex.pokemonlist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.chenguang.pokedex.R
import com.chenguang.pokedex.databinding.LayoutPokemonInfoItemBinding
import com.chenguang.pokedex.model.PokemonInfo

class PokemonInfoListAdapter(
    context: Context
) : ListAdapter<PokemonInfo, PokemonInfoListAdapter.PokemonInfoViewHolder>(PokemonInfoDiffUtil()) {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonInfoViewHolder {
        val view = layoutInflater.inflate(R.layout.layout_pokemon_info_item, parent, false)
        return PokemonInfoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonInfoViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class PokemonInfoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = LayoutPokemonInfoItemBinding.bind(itemView)

        fun bind(item: PokemonInfo) {
            binding.pokemonInfoItemTextView.text = item.name
            Glide.with(itemView)
                .load(item.getImageUrl())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.pokemonInfoItemImageView)
        }
    }

    class PokemonInfoDiffUtil : DiffUtil.ItemCallback<PokemonInfo>() {
        override fun areItemsTheSame(oldItem: PokemonInfo, newItem: PokemonInfo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PokemonInfo, newItem: PokemonInfo): Boolean {
            return oldItem == newItem
        }
    }
}
