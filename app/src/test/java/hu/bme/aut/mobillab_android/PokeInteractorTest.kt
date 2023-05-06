package hu.bme.aut.mobillab_android

import hu.bme.aut.mobillab_android.interactor.PokeInteractor
import hu.bme.aut.mobillab_android.mock.MockAccountDao
import hu.bme.aut.mobillab_android.mock.MockPokeApi
import hu.bme.aut.mobillab_android.model.ui.Poke
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.Before

class PokeInteractorTest {

    private lateinit var accountDaoMock: MockAccountDao
    private lateinit var pokeApiMock: MockPokeApi
    private lateinit var pokeInteractor: PokeInteractor

    @Before
    fun setup() {
        accountDaoMock = MockAccountDao()
        pokeApiMock = MockPokeApi()
        val app = PokeApplication()
        app.currentUsername = "user1"
        pokeInteractor = PokeInteractor(
            Dispatchers.IO,
            pokeApiMock,
            accountDaoMock,
            app
        )
    }


    @Test
    fun testAddToFavouritesCanAddNewPokemonsToFavourites() {
        var contains42 = accountDaoMock.accounts.find { it.account.username == "user1" }!!.favIds.map { it.favId }.contains(42)
        assert(!contains42)
        runBlocking {
            pokeInteractor.addToFavourites(42, "user1")
        }
        contains42 = accountDaoMock.accounts.find { it.account.username == "user1" }!!.favIds.map { it.favId }.contains(42)
        assert(contains42)
    }

    @Test
    fun testGetPokemons() {
        var pokemonResult: List<Poke>
        runBlocking {
            pokemonResult = pokeInteractor.getPokemons()
        }
        assert(pokemonResult[0].id == 4)
        assert(pokemonResult[0].name == "charmander")
        assert(pokemonResult[0].isFavourite)
        assert(pokemonResult[1].id == 1)
        assert(pokemonResult[1].name == "bulbasaur")
        assert(pokemonResult[1].isFavourite)
        assert(pokemonResult[2].id == 19)
        assert(pokemonResult[2].name == "rattata")
        assert(!pokemonResult[2].isFavourite)

    }

    @Test
    fun testRemoveFromFavourites() {
        var contains4 = accountDaoMock.accounts.find { it.account.username == "user1" }!!.favIds.map { it.favId }.contains(4)
        assert(contains4)
        runBlocking {
            pokeInteractor.removeFromFavourites(4, "user1")
        }
        contains4 = accountDaoMock.accounts.find { it.account.username == "user1" }!!.favIds.map { it.favId }.contains(4)
        assert(!contains4)

    }

    @Test
    fun testAddAccount() {
        val user2 = accountDaoMock.accounts.find { it.account.username == "user2" }
        assert(user2 == null)
        runBlocking {
            pokeInteractor.addAccount("user2")
        }
        val newAccount = accountDaoMock.accounts.find { it.account.username == "user2" }
        assert(newAccount != null)

    }

    @Test(expected = RuntimeException::class)
    fun testAddAccountExceptionWhenAccountAlreadyExists() {
        runBlocking {
            pokeInteractor.addAccount("user1")
        }

    }

    @Test
    fun testRemovePokemon() {
        var delIds = accountDaoMock.accounts.find { it.account.username == "user1" }!!.delIds.map { it.delId }
        assert(!delIds.contains(4))
        runBlocking {
            pokeInteractor.removePokemon(4)
        }
        delIds = accountDaoMock.accounts.find { it.account.username == "user1" }!!.delIds.map { it.delId }
        assert(delIds.contains(4))

    }

    @Test(expected = RuntimeException::class)
    fun testLoginErrorWhenNoSuchAccount() {
        runBlocking {
            pokeInteractor.login("user42")
        }
    }




}