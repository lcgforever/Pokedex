package com.chenguang.pokedex.network.model

import com.chenguang.pokedex.model.PokemonInfo
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

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

/**
 * Helper method to convert network response data model to client domain model
 */
fun PokemonLinkResponse.toPokemonInfo(): PokemonInfo {
    return PokemonInfo(
        // Url format: https://pokeapi.co/api/v2/pokemon/{id}/
        id = url.split("/").dropLast(1).last(),
        name = name,
        url = url
    )
}

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