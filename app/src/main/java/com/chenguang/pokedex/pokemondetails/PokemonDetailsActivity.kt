package com.chenguang.pokedex.pokemondetails

import android.content.res.ColorStateList
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.chenguang.pokedex.R
import com.chenguang.pokedex.databinding.ActivityPokemonDetailsBinding
import com.chenguang.pokedex.model.PokemonDetails
import com.chenguang.pokedex.util.Constants
import com.google.android.material.textview.MaterialTextView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PokemonDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPokemonDetailsBinding

    private val viewModel: PokemonDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPokemonDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        with(binding) {
            setSupportActionBar(pokemonDetailsActivityToolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setHomeButtonEnabled(true)

            val pokemonId = intent.getStringExtra(Constants.INTENT_EXTRA_KEY_POKEMON_ID) ?: ""
            val pokemonName = intent.getStringExtra(Constants.INTENT_EXTRA_KEY_POKEMON_NAME) ?: ""
            val pokemonImage = intent.getStringExtra(Constants.INTENT_EXTRA_KEY_POKEMON_IMAGE) ?: ""

            ViewCompat.setTransitionName(pokemonDetailsActivityCardCenterImageView, pokemonName)

            val formattedId = getString(R.string.pokemon_id_format, pokemonId.toInt())
            pokemonDetailsActivityCardTopTextView.text = formattedId
            pokemonDetailsActivityCardBottomTextView.text = formattedId
            pokemonDetailsActivityCardNameTextView.text = pokemonName
            pokemonDetailsActivityToolbar.title = pokemonName
            Glide.with(pokemonDetailsActivityToolbar)
                .load(pokemonImage)
                .into(pokemonDetailsActivityCardCenterImageView)

            lifecycleScope.launch {
                viewModel.fetchPokemonDetails(
                    pokemonId = pokemonId,
                    onStart = {},
                    onCompletion = {},
                    onError = {
                        val message = getString(R.string.pokemon_data_fetch_failed_text, it.localizedMessage)
                        Toast.makeText(this@PokemonDetailsActivity, message, Toast.LENGTH_LONG).show()
                    }
                ).collectLatest { pokemonDetails ->
                    pokemonDetailsActivityCardHeightTextValue.text =
                        getString(R.string.pokemon_height_format, pokemonDetails.height)
                    pokemonDetailsActivityCardWeightTextValue.text =
                        getString(R.string.pokemon_weight_format, pokemonDetails.weight)
                    renderPokemonTypeList(pokemonDetails)
                    renderPokemonStatList(pokemonDetails)
                }
            }
        }
    }

    private fun renderPokemonTypeList(pokemonDetails: PokemonDetails) {
        val padding = resources.getDimensionPixelSize(R.dimen.pokemon_type_padding)
        pokemonDetails.types.forEachIndexed { index, type ->
            val typeTextView = MaterialTextView(this).apply {
                text = type.name
                background = ContextCompat.getDrawable(this@PokemonDetailsActivity, R.drawable.bg_pokemon_card)
                val typeColor = ContextCompat.getColor(this@PokemonDetailsActivity, type.getColor())
                backgroundTintList = ColorStateList.valueOf(typeColor)
                setTextAppearance(this@PokemonDetailsActivity, R.style.BodyTextWhite)
                setPadding(padding, 0, padding, 0)
            }
            binding.pokemonDetailsActivityCardTypeContainer.addView(typeTextView)
            if (index > 0) {
                (typeTextView.layoutParams as? LinearLayout.LayoutParams)?.let { layoutParams ->
                    layoutParams.marginStart = resources.getDimensionPixelSize(R.dimen.pokemon_type_margin)
                }
            }
        }
    }

    private fun renderPokemonStatList(pokemonDetails: PokemonDetails) {
        binding.pokemonDetailsActivityCardRecyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = PokemonStatListAdapter(this)
        binding.pokemonDetailsActivityCardRecyclerView.adapter = adapter
        adapter.submitList(pokemonDetails.stats.filter { it.isVisible() })
    }

    override fun onSupportNavigateUp(): Boolean {
        finishAfterTransition()
        return true
    }
}
