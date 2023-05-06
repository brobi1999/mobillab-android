package hu.bme.aut.mobillab_android.ui.list

import hu.bme.aut.mobillab_android.model.ui.Poke
import hu.bme.aut.mobillab_android.util.*

data class ListViewState(
    var pokemonList: List<Poke>,
    var loadPokemonsState: RequestState,
    var failedToAddPokeToFavouritesEvent: FailedToAddPokeToFavouritesEvent? = null,
    var failedToRemovePokeFromFavourites: FailedToRemovePokeFromFavourites? = null,
    var failedToDeletePokeEvent: FailedToDeletePokeEvent? = null,
)
