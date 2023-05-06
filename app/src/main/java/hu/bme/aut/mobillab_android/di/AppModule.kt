package hu.bme.aut.mobillab_android.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hu.bme.aut.mobillab_android.database.PokemonDatabase
import hu.bme.aut.mobillab_android.network.PokeApi
import hu.bme.aut.mobillab_android.util.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providePokeDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        PokemonDatabase::class.java,
        "pokedex-db"
    ).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideAccountDao(db: PokemonDatabase) = db.accountDao()

    @IoDispatcher
    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    fun providePokeApi(): PokeApi {
        val baseUrl = "https://pokeapi.co/api/v2/"

        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(40, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder().baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PokeApi::class.java)
    }
}