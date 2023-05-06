package hu.bme.aut.mobillab_android.network

import hu.bme.aut.mobillab_android.model.domain.PokeDetail
import hu.bme.aut.mobillab_android.model.domain.PokeList
import retrofit2.http.*

interface PokeApi {

    /**
     * Adds a Pokemon to the list of favourites
     *
     * Responses:
     *  - 200: successful operation
     *  - 404: The Pokemon with the specified id does not exist.
     *
     * @param pokemonId The id of the pokemon. (optional)
     * @param accountName The name of the account. (optional)
     * @return [Unit]
     */
    @POST("addToFavourites")
    suspend fun addToFavourites(@Query("pokemonId") pokemonId: kotlin.Int, @Query("accountName") accountName: kotlin.String)

    /**
     * Returns details for a Pokemon.
     *
     * Responses:
     *  - 200: successful operation
     *  - 404: The Pokemon with the specified name does not exist.
     *
     * @param pokemonName The name of the pokemon.
     * @return [PokeDetail]
     */
    @GET("pokemon/{pokemonName}")
    suspend fun getPokemonDetail(@Path("pokemonName") pokemonName: kotlin.String): PokeDetail

    /**
     * Returns a list of Pokemons
     *
     * Responses:
     *  - 200: successful operation
     *
     * @return [PokeList]
     */
    @GET("pokemon")
    suspend fun getPokemons(): PokeList

    /**
     * Removes a Pokemon from the list of favourites
     *
     * Responses:
     *  - 200: successful operation
     *  - 404: The Pokemon with the specified id does not exist.
     *
     * @param pokemonId The id of the pokemon. (optional)
     * @param accountName The name of the account. (optional)
     * @return [Unit]
     */
    @DELETE("removeFromFavourites")
    suspend fun removeFromFavourites(@Query("pokemonId") pokemonId: kotlin.Int, @Query("accountName") accountName: kotlin.String)

    /**
     * Creates a new account
     *
     * Responses:
     *  - 200: successful operation
     *  - 400: The account already exists.
     *
     * @param accountName The name of the account. (optional)
     * @return [Unit]
     */
    @PUT("addAccount")
    suspend fun addAccount(@Query("accountName") accountName: kotlin.String)

    /**
     * Removes a Pokemon
     *
     * Responses:
     *  - 200: successful operation
     *  - 404: The Pokemon with the specified id does not exist.
     *
     * @param pokemonId The id of the pokemon.
     * @return [Unit]
     */
    @DELETE("removePokemon")
    suspend fun removePokemon(@Query("pokemonId") pokemonId: kotlin.Int)

}