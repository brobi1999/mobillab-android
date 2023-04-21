package hu.bme.aut.mobillab_android.interactor

import hu.bme.aut.mobillab_android.network.PokeApi
import hu.bme.aut.mobillab_android.util.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class PokeInteractor @Inject constructor(
    @IoDispatcher private val defaultDispatcher: CoroutineDispatcher,
    private val pokeApi: PokeApi
) {
}