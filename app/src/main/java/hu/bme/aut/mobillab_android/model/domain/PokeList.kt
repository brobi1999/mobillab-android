package hu.bme.aut.mobillab_android.model.domain

data class PokeList (
    val results: List<PokeResult>
)

data class PokeResult (
    val name: String? = null,
    val url: String? = null
)