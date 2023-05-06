package hu.bme.aut.mobillab_android.model.db

import androidx.room.Entity

@Entity(primaryKeys = ["delId","containerUsername"])
data class DeletedId (
    val delId: Int,
    val containerUsername: String,
)
