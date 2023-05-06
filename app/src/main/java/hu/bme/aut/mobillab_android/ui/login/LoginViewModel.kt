package hu.bme.aut.mobillab_android.ui.login

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.mobillab_android.interactor.PokeInteractor
import hu.bme.aut.mobillab_android.util.AuthSuccessEvent
import hu.bme.aut.mobillab_android.util.Error
import hu.bme.aut.mobillab_android.util.Finished
import hu.bme.aut.mobillab_android.util.Initial
import hu.bme.aut.mobillab_android.util.Ongoing
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val pokeInteractor: PokeInteractor
) : ViewModel() {

    fun login(accountName: String) = viewModelScope.launch{
        try {
            _state.value = _state.value?.copy(loginState = Ongoing)
            pokeInteractor.login(accountName)
            _state.value = _state.value?.copy(loginState = Finished, authSuccessEvent = AuthSuccessEvent())
        }
        catch (e: Exception){
            e.printStackTrace()
            _state.value = _state.value?.copy(loginState = Error(e.message))
        }

    }

    fun authSuccessEventReceived(){
        _state.value = _state.value?.copy(loginState = Initial, registerState = Initial)
        _state.value?.authSuccessEvent = null
    }

    fun register(accountName: String) = viewModelScope.launch{
        try {
            _state.value = _state.value?.copy(registerState = Ongoing)
            pokeInteractor.addAccount(accountName)
            _state.value = _state.value?.copy(registerState = Finished, authSuccessEvent = AuthSuccessEvent())
        }
        catch (e: Exception){
            e.printStackTrace()
            _state.value = _state.value?.copy(registerState = Error(e.message))
        }

    }

    private val _state = MutableLiveData(LoginViewState(loginState = Initial, registerState = Initial))
    val state: LiveData<LoginViewState> = _state
}