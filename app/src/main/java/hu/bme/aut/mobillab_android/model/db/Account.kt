package hu.bme.aut.mobillab_android.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accounts")
data class Account(
    @PrimaryKey val username : String,

)
