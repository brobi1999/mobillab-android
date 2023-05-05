package hu.bme.aut.mobillab_android.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemons")
data class DbPokemon(
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
)
