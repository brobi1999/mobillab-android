package hu.bme.aut.mobillab_android.database

import androidx.room.Database
import androidx.room.RoomDatabase
import hu.bme.aut.mobillab_android.model.db.DbPokemon

@Database(
    entities = [DbPokemon::class], // Tell the database the entries will hold data of this type
    version = 1
)
abstract class PokemonDatabase : RoomDatabase() {

    abstract fun pokemonDao(): PokemonDao
}