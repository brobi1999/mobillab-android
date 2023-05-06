package hu.bme.aut.mobillab_android.database

import androidx.room.*
import hu.bme.aut.mobillab_android.model.db.Account
import hu.bme.aut.mobillab_android.model.db.AccountWithDeletedIdsAndFavouriteIds
import hu.bme.aut.mobillab_android.model.db.DeletedId
import hu.bme.aut.mobillab_android.model.db.FavouriteId

@Dao
interface AccountDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOne(account : Account)

    @Transaction
    suspend fun addToAccountFavourites(username: String, pokemonId: Int) {
        val account = getAccountWithDeletedIdsAndFavouriteIds(username) ?: throw RuntimeException("Account not found.")
        if(account.favIds.map { it.favId }.contains(pokemonId))
            throw RuntimeException("The pokemon with the specified id is already in the favourites list of the account.")
        insertFavouriteIds(FavouriteId(favId = pokemonId, containerUsername = username))
    }

    @Insert
    suspend fun insertFavouriteIds(favId: FavouriteId)

    @Delete
    suspend fun deleteFavouriteId(favId: FavouriteId)

    @Insert
    suspend fun insertDeletedIds(delIds: DeletedId)

    @Transaction
    @Query("SELECT * FROM accounts WHERE username = :username")
    suspend fun getAccountWithDeletedIdsAndFavouriteIds(username: String): AccountWithDeletedIdsAndFavouriteIds?

    @Transaction
    suspend fun removePokemonFromFavourites(username: String, pokemonId: Int) {
        val account = getAccountWithDeletedIdsAndFavouriteIds(username) ?: throw RuntimeException("Account not found.")
        if(!account.favIds.map { it.favId }.contains(pokemonId))
            return
        deleteFavouriteId(FavouriteId(favId = pokemonId, containerUsername = username))
    }

    @Transaction
    suspend fun deletePokemon(username: String, pokemonId: Int) {
        val account = getAccountWithDeletedIdsAndFavouriteIds(username) ?: throw RuntimeException("Account not found.")
        if(!account.delIds.map { it.delId }.contains(pokemonId))
            return
        insertDeletedIds(DeletedId(delId = pokemonId, containerUsername = username))
    }

    @Query("SELECT * FROM accounts WHERE username = :username LIMIT 1")
    suspend fun getAccountByUsername(username: String): Account?

}