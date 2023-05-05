package hu.bme.aut.mobillab_android.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.mobillab_android.interactor.PokeInteractor
import hu.bme.aut.mobillab_android.util.Initial
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val pokeInteractor: PokeInteractor
) : ViewModel() {

    private val _state = MutableLiveData(ListViewState(status = Initial))
    val state: LiveData<ListViewState> = _state
}