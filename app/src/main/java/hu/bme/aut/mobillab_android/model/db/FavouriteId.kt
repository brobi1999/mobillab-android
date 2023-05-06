package hu.bme.aut.mobillab_android.model.db

import androidx.room.Entity

@Entity(primaryKeys = ["favId","containerUsername"])
data class FavouriteId (
    val favId: Int,
    val containerUsername: String,
)