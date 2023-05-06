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
        insertFavouriteId(pokemonId, username)
    }

    @Query("INSERT INTO favouriteid (favId, containerUsername) VALUES (:favId, :containerUsername)")
    suspend fun insertFavouriteId(favId: Int, containerUsername: String)

    @Query("DELETE FROM favouriteid WHERE favId = :favId AND containerUsername = :containerUsername")
    suspend fun deleteFavouriteId(favId: Int, containerUsername: String)

    @Query("INSERT INTO deletedid (delId, containerUsername) VALUES (:delId, :containerUsername)")
    suspend fun insertDeletedIds(delId: Int, containerUsername: String)

    @Transaction
    @Query("SELECT * FROM accounts WHERE username = :username")
    suspend fun getAccountWithDeletedIdsAndFavouriteIds(username: String): AccountWithDeletedIdsAndFavouriteIds?

    @Transaction
    suspend fun removePokemonFromFavourites(username: String, pokemonId: Int) {
        val account = getAccountWithDeletedIdsAndFavouriteIds(username) ?: throw RuntimeException("Account not found.")
        if(!account.favIds.map { it.favId }.contains(pokemonId))
            return
        deleteFavouriteId(pokemonId, username)
    }

    @Transaction
    suspend fun deletePokemon(username: String, pokemonId: Int) {
        val account = getAccountWithDeletedIdsAndFavouriteIds(username) ?: throw RuntimeException("Account not found.")
        if(account.delIds.map { it.delId }.contains(pokemonId))
            return
        insertDeletedIds(pokemonId, username)
    }

    @Query("SELECT * FROM accounts WHERE username = :username LIMIT 1")
    suspend fun getAccountByUsername(username: String): Account?

}