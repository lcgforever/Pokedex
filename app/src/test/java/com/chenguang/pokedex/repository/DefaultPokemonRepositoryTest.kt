package com.chenguang.pokedex.repository

import com.chenguang.pokedex.network.PokemonNetworkClient
import com.chenguang.pokedex.network.model.PokemonResponse
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.Response

class DefaultPokemonRepositoryTest {

    private val mockPokemonNetworkClient = mock<PokemonNetworkClient>()
    private val subject = DefaultPokemonRepository(mockPokemonNetworkClient)

    @Test
    fun testFetchPokemonData_withSuccessResponse() {
        runBlocking {
            val testId = "testId"
            val mockPokemonResponse = PokemonResponse(
                id = 1,
                name = testId,
                baseExperience = 100,
                height = 10,
                weight = 20,
                types = emptyList(),
                stats = emptyList(),
                abilities = emptyList()
            )
            val mockResponse = mock<Response<PokemonResponse>> {
                on { isSuccessful } doReturn true
                on { body() } doReturn mockPokemonResponse
            }
            whenever(mockPokemonNetworkClient.fetchPokemonDataById(testId)).thenReturn(mockResponse)

            val result = subject.fetchPokemonData(testId).first()

            assertEquals(1, result.id)
            assertEquals(testId.capitalize(), result.name)
            assertEquals(100, result.baseExperience)
            assertEquals(1F, result.height)
            assertEquals(2F, result.weight)
            assertEquals(0, result.types.size)
            assertEquals(0, result.stats.size)
            assertEquals(0, result.abilities.size)
        }
    }

    @Test(expected = Throwable::class)
    fun testFetchPokemonData_withErrorResponse() {
        runBlocking {
            val testId = "testId"
            val testErrorMessage = "testErrorMessage"
            val mockResponse = mock<Response<PokemonResponse>> {
                on { message() } doReturn testErrorMessage
            }
            whenever(mockPokemonNetworkClient.fetchPokemonDataById(testId)).thenReturn(mockResponse)

            subject.fetchPokemonData(testId).collect()
        }
    }
}
