package hu.bme.aut.mobillab_android.mock

import hu.bme.aut.mobillab_android.database.AccountDao
import hu.bme.aut.mobillab_android.model.db.Account
import hu.bme.aut.mobillab_android.model.db.AccountWithDeletedIdsAndFavouriteIds
import hu.bme.aut.mobillab_android.model.db.DeletedId
import hu.bme.aut.mobillab_android.model.db.FavouriteId

class MockAccountDao: AccountDao {
    companion object{
        val accounts = mutableListOf(
            AccountWithDeletedIdsAndFavouriteIds(Account("user1"),
                favIds = listOf(4, 1).map { FavouriteId(it, "user1") },
                delIds = listOf(16).map { DeletedId(it, "user1") },
            )
        )
    }

    override suspend fun insertOne(account: Account) {
        accounts.add(AccountWithDeletedIdsAndFavouriteIds(Account(account.username),
            favIds = listOf(),
            delIds = listOf(),
        ))
    }

    override suspend fun addToAccountFavourites(username: String, pokemonId: Int) {
        val mutableList = accounts.find { it.account.username == username }!!.favIds.toMutableList()
        mutableList.add(FavouriteId(pokemonId, username))
        accounts.find { it.account.username == username }!!.favIds = mutableList
    }

    override suspend fun insertFavouriteIds(favId: FavouriteId){
        return
    }

    override suspend fun deleteFavouriteId(favId: FavouriteId){
        return
    }

    override suspend fun insertDeletedIds(delIds: DeletedId){
        return
    }

    override suspend fun getAccountWithDeletedIdsAndFavouriteIds(username: String): AccountWithDeletedIdsAndFavouriteIds?{
        return accounts.find { it.account.username == username }
    }

    override suspend fun removePokemonFromFavourites(username: String, pokemonId: Int) {
        val mutableList = accounts.find { it.account.username == username }!!.favIds.toMutableList()
        mutableList.remove(FavouriteId(pokemonId, username))
        accounts.find { it.account.username == username }!!.favIds = mutableList
    }

    override suspend fun deletePokemon(username: String, pokemonId: Int) {
        val mutableList = accounts.find { it.account.username == username }!!.delIds.toMutableList()
        mutableList.add(DeletedId(pokemonId, username))
        accounts.find { it.account.username == username }!!.delIds = mutableList
    }

    override suspend fun getAccountByUsername(username: String): Account?{
        return accounts.find { it.account.username == username }!!.account
    }




}