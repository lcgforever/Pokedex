package com.chenguang.pokedex.network.model

import com.chenguang.pokedex.db.model.PokemonAbility
import com.chenguang.pokedex.db.model.PokemonData
import com.chenguang.pokedex.db.model.PokemonInfo
import com.chenguang.pokedex.db.model.PokemonStat
import com.chenguang.pokedex.db.model.PokemonType
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Locale

/**
 * Network response for pokemon query results
 */
@JsonClass(generateAdapter = true)
data class PokemonListResponse(
    @Json(name = "count") val count: Int,
    @Json(name = "next") val next: String?,
    @Json(name = "previous") val previous: String?,
    @Json(name = "results") val results: List<PokemonLinkResponse>
)

@JsonClass(generateAdapter = true)
data class PokemonLinkResponse(
    @Json(name = "name") val name: String,
    @Json(name = "url") val url: String
)

@JsonClass(generateAdapter = true)
data class PokemonResponse(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "base_experience") val baseExperience: Int,
    @Json(name = "height") val height: Int,
    @Json(name = "weight") val weight: Int,
    @Json(name = "types") val types: List<TypeResponse>,
    @Json(name = "stats") val stats: List<StatResponse>,
    @Json(name = "abilities") val abilities: List<AbilityResponse>
)

@JsonClass(generateAdapter = true)
data class TypeResponse(
    @Json(name = "slot") val slot: Int,
    @Json(name = "type") val typeData: TypeDataResponse
)

@JsonClass(generateAdapter = true)
data class TypeDataResponse(
    @Json(name = "name") val name: String,
    @Json(name = "url") val url: String
)

@JsonClass(generateAdapter = true)
data class StatResponse(
    @Json(name = "base_stat") val value: Int,
    @Json(name = "stat") val statData: StatDataResponse
)

@JsonClass(generateAdapter = true)
data class StatDataResponse(
    @Json(name = "name") val name: String,
    @Json(name = "url") val url: String
)

@JsonClass(generateAdapter = true)
data class AbilityResponse(
    @Json(name = "slot") val slot: Int,
    @Json(name = "is_hidden") val isHidden: Boolean,
    @Json(name = "ability") val abilityData: AbilityDataResponse
)

@JsonClass(generateAdapter = true)
data class AbilityDataResponse(
    @Json(name = "name") val name: String,
    @Json(name = "url") val url: String
)

/**
 * Helper methods to convert network response data model to database model
 */
fun PokemonLinkResponse.toPokemonInfo(): PokemonInfo {
    return PokemonInfo(
        // Url format: https://pokeapi.co/api/v2/pokemon/{id}/
        pokemonId = url.split("/").dropLast(1).last(),
        name = name.capitalize(Locale.getDefault()),
        url = url
    )
}

fun PokemonResponse.toPokemonData(): PokemonData {
    return PokemonData(
        pokemonId = id.toString(),
        name = name.capitalize(Locale.getDefault()),
        baseExperience = baseExperience,
        height = height.toFloat() / 10,
        weight = weight.toFloat() / 10,
    )
}

fun PokemonResponse.toPokemonTypeList(): List<PokemonType> {
    return types.map {
        PokemonType(
            typeId = it.typeData.url.split("/").dropLast(1).last(),
            slot = it.slot,
            name = it.typeData.name.capitalize(Locale.getDefault()),
            url = it.typeData.url
        )
    }
}

fun PokemonResponse.toPokemonStatList(): List<PokemonStat> {
    return stats.map {
        PokemonStat(
            statId = it.statData.url.split("/").dropLast(1).last(),
            ownerId = id.toString(),
            value = it.value,
            name = it.statData.name.capitalize(Locale.getDefault()),
            url = it.statData.url
        )
    }
}

fun PokemonResponse.toPokemonAbilityList(): List<PokemonAbility> {
    return abilities.map {
        PokemonAbility(
            abilityId = it.abilityData.url.split("/").dropLast(1).last(),
            slot = it.slot,
            isHidden = it.isHidden,
            name = it.abilityData.name.capitalize(Locale.getDefault()),
            url = it.abilityData.url
        )
    }
}
