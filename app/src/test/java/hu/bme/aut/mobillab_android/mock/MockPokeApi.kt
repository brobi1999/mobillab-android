package hu.bme.aut.mobillab_android.mock

import hu.bme.aut.mobillab_android.model.domain.PokeDetail
import hu.bme.aut.mobillab_android.model.domain.PokeList
import hu.bme.aut.mobillab_android.model.domain.PokeResult
import hu.bme.aut.mobillab_android.network.PokeApi

class MockPokeApi: PokeApi {

    companion object{
        val pokemonList = PokeList(
            results = listOf(
                PokeResult("pidgey", "https://pokeapi.co/api/v2/pokemon/16/"),
                PokeResult("charmander", "https://pokeapi.co/api/v2/pokemon/4/"),
                PokeResult("bulbasaur", "https://pokeapi.co/api/v2/pokemon/1/"),
                PokeResult("rattata", "https://pokeapi.co/api/v2/pokemon/19/")
            ),
        )
        val pokemonDetailList = listOf<PokeDetail>(
            PokeDetail(id = 16, name = "pidgey"),
            PokeDetail(id = 4, name = "charmander"),
            PokeDetail(id = 1, name = "bulbasaur"),
            PokeDetail(id = 19, name = "rattata")

        )
    }

    override suspend fun addToFavourites(pokemonId: Int, accountName: String) {
        return
    }

    override suspend fun getPokemonDetail(pokemonName: String): PokeDetail {
        return pokemonDetailList.find { it -> it.name == pokemonName }!!
    }

    override suspend fun getPokemons(): PokeList {
        return pokemonList
    }

    override suspend fun removeFromFavourites(pokemonId: Int, accountName: String) {
        return
    }

    override suspend fun addAccount(accountName: String) {
        return
    }

    override suspend fun removePokemon(pokemonId: Int) {
        return
    }
}