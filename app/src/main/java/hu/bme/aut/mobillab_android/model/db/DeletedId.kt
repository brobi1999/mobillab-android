package hu.bme.aut.mobillab_android.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DeletedId (
    @PrimaryKey val delId: Int,
    val containerUsername: String,
)
