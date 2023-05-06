package hu.bme.aut.mobillab_android.ui.login

import hu.bme.aut.mobillab_android.util.AuthSuccessEvent
import hu.bme.aut.mobillab_android.util.RequestState

data class LoginViewState(
    var loginState: RequestState,
    var registerState: RequestState,
    var authSuccessEvent: AuthSuccessEvent? = null,
)
