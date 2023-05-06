package hu.bme.aut.mobillab_android.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavouriteId (
    @PrimaryKey val favId: Int,
    val containerUsername: String,
)