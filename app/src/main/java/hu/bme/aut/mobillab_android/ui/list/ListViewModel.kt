package hu.bme.aut.mobillab_android.ui.list

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.mobillab_android.interactor.PokeInteractor
import hu.bme.aut.mobillab_android.util.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val pokeInteractor: PokeInteractor
) : ViewModel() {

    private var _state: MutableLiveData<ListViewState>
    var state: LiveData<ListViewState>

    init {
        _state = MutableLiveData(ListViewState(
            pokemonList = listOf(),
            loadPokemonsState = Initial
        ))
        state = _state
        loadPokemons()
    }

    fun loadPokemons() = viewModelScope.launch{
        try {
            _state.value = _state.value?.copy(loadPokemonsState = Ongoing)
            val pokemons = pokeInteractor.getPokemons()
            _state.value = _state.value?.copy(loadPokemonsState = Finished, pokemonList = pokemons)
        }
        catch (e: Exception){
            e.printStackTrace()
            _state.value = _state.value?.copy(loadPokemonsState = Error(e.message))
        }
    }

    fun addPokemonToFavourites(pokemonId: Int) = viewModelScope.launch{
        try {
            pokeInteractor.addToFavourites(pokemonId, "")
        }
        catch (e: Exception){
            e.printStackTrace()
            _state.value = _state.value?.copy(failedToAddPokeToFavouritesEvent = FailedToAddPokeToFavouritesEvent(e.message))
        }
    }

    fun failedToAddPokeToFavouritesEventReceived(){
        _state.value?.failedToAddPokeToFavouritesEvent = null
    }

    fun removePokemonFromFavourites(pokemonId: Int) = viewModelScope.launch{
        try {
            pokeInteractor.removeFromFavourites(pokemonId, "")
        }
        catch (e: Exception){
            e.printStackTrace()
            _state.value = _state.value?.copy(failedToRemovePokeFromFavourites = FailedToRemovePokeFromFavourites(e.message))
        }
    }

    fun removePokemonFromFavouritesEventReceived(){
        _state.value?.failedToRemovePokeFromFavourites = null
    }

    fun removePokemon(pokemonId: Int) = viewModelScope.launch{
        try {
            pokeInteractor.removePokemon(pokemonId)
        }
        catch (e: Exception){
            e.printStackTrace()
            _state.value = _state.value?.copy(failedToDeletePokeEvent = FailedToDeletePokeEvent(e.message))
        }
    }

    fun failedToDeletePokeEventReceived(){
        _state.value?.failedToRemovePokeFromFavourites = null
    }


}