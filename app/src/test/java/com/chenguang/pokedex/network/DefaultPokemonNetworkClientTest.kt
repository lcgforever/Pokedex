package com.chenguang.pokedex.network

import com.chenguang.pokedex.network.model.PokemonListResponse
import com.chenguang.pokedex.network.model.PokemonResponse
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.Response

class DefaultPokemonNetworkClientTest {

    private val mockNetworkService = mock<PokemonNetworkService>()
    private val subject = DefaultPokemonNetworkClient(mockNetworkService)

    @Test
    fun testFetchPokemonDataList() {
        runBlocking {
            val mockResponse = mock<Response<PokemonListResponse>>()
            whenever(mockNetworkService.fetchPokemonList(anyInt(), anyInt())).thenReturn(
                mockResponse
            )

            val result = subject.fetchPokemonList(offset = 0)

            assertEquals(mockResponse, result)
        }
    }

    @Test
    fun testFetchPokemonDataByName() {
        runBlocking {
            val mockResponse = mock<Response<PokemonResponse>>()
            whenever(mockNetworkService.fetchPokemonDataById(anyString())).thenReturn(
                mockResponse
            )

            val result = subject.fetchPokemonDataById("test")

            assertEquals(mockResponse, result)
        }
    }
}
