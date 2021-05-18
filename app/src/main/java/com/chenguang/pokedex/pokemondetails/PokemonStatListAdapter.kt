package com.chenguang.pokedex.pokemondetails

import android.animation.ObjectAnimator
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chenguang.pokedex.R
import com.chenguang.pokedex.databinding.LayoutPokemonStatItemBinding
import com.chenguang.pokedex.model.PokemonStat

class PokemonStatListAdapter(
    context: Context
) : ListAdapter<PokemonStat, PokemonStatListAdapter.PokemonStatViewHolder>(PokemonStatDiffUtil()) {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonStatViewHolder {
        val view = layoutInflater.inflate(R.layout.layout_pokemon_stat_item, parent, false)
        return PokemonStatViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonStatViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class PokemonStatViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = LayoutPokemonStatItemBinding.bind(itemView)

        fun bind(item: PokemonStat) {
            binding.pokemonStatItemTextView.text = item.name
            binding.pokemonStatItemProgressIndicator.setIndicatorColor(
                ContextCompat.getColor(itemView.context, item.getColor())
            )
            val objectAnimator = ObjectAnimator.ofInt(
                binding.pokemonStatItemProgressIndicator,
                "progress",
                0,
                item.getProgress()
            )
            objectAnimator.duration = 600
            objectAnimator.start()
        }
    }

    class PokemonStatDiffUtil : DiffUtil.ItemCallback<PokemonStat>() {
        override fun areItemsTheSame(oldItem: PokemonStat, newItem: PokemonStat): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PokemonStat, newItem: PokemonStat): Boolean {
            return oldItem == newItem
        }
    }
}
