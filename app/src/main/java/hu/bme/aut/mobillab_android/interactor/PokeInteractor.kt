package hu.bme.aut.mobillab_android.interactor

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import hu.bme.aut.mobillab_android.PokeApplication
import hu.bme.aut.mobillab_android.database.AccountDao
import hu.bme.aut.mobillab_android.model.db.Account
import hu.bme.aut.mobillab_android.model.ui.Poke
import hu.bme.aut.mobillab_android.network.PokeApi
import hu.bme.aut.mobillab_android.util.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokeInteractor @Inject constructor(
    @IoDispatcher private val defaultDispatcher: CoroutineDispatcher,
    private val pokeApi: PokeApi,
    private val accountDao: AccountDao,
    @ApplicationContext val context: Context
) {
    suspend fun addToFavourites(pokemonId: Int, accountName: String) = withContext(defaultDispatcher){
        accountDao.addToAccountFavourites(getCurrentUsername(), pokemonId)
    }

    suspend fun getPokemons(): List<Poke> = withContext(defaultDispatcher){
        val currentUsername = getCurrentUsername()
        val pokemons = pokeApi.getPokemons()
        val account = accountDao.getAccountWithDeletedIdsAndFavouriteIds(currentUsername)!!
        val deletedPokemonIds = account.delIds
        val favPokemonIds = account.favIds

        val filteredPokemonList = mutableListOf<Poke>()
        for(pokeResult in pokemons.results){
            val pokeDetail = pokeApi.getPokemonDetail(pokeResult.name!!)
            if(!deletedPokemonIds.map { it.delId }.contains(pokeDetail.id))
                filteredPokemonList.add(
                    Poke.convertDomainPokeDetailToUiPoke(pokeDetail, favPokemonIds.map { it.favId }.contains(pokeDetail.id))
                )
        }
        filteredPokemonList
    }

    suspend fun removeFromFavourites(pokemonId: Int, accountName: String) = withContext(defaultDispatcher){
        accountDao.removePokemonFromFavourites(getCurrentUsername(), pokemonId)
    }

    suspend fun addAccount(accountName: String) = withContext(defaultDispatcher){
        if(accountDao.getAccountByUsername(accountName) != null)
            throw RuntimeException("Account already exists.")
        else accountDao.insertOne(
            Account(username = accountName)
        )
    }

    suspend fun removePokemon(pokemonId: Int) = withContext(defaultDispatcher){
        accountDao.deletePokemon(getCurrentUsername(), pokemonId)
    }

    private fun getCurrentUsername(): String {
        return (context as PokeApplication).currentUsername
            ?: throw RuntimeException("Current user is not set.")
    }

    fun login(accountName: String){
        (context as PokeApplication).currentUsername = accountName
    }

}