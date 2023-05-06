package hu.bme.aut.mobillab_android.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import hu.bme.aut.mobillab_android.model.db.Account
import hu.bme.aut.mobillab_android.model.db.DeletedId
import hu.bme.aut.mobillab_android.model.db.FavouriteId

@Database(
    entities = [Account::class, DeletedId::class, FavouriteId::class], // Tell the database the entries will hold data of this type
    version = 1
)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun accountDao(): AccountDao
}