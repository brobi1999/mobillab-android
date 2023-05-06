package hu.bme.aut.mobillab_android.util

sealed class RequestState

object Initial : RequestState()

object Ongoing : RequestState()

object Finished : RequestState()

data class Error(val msg: String?) : RequestState()
