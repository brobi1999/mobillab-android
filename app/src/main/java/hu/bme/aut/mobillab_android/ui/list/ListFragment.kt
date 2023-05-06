package hu.bme.aut.mobillab_android.ui.list

import android.graphics.Bitmap
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.mobillab_android.PokeApplication
import hu.bme.aut.mobillab_android.databinding.FragmentListBinding
import hu.bme.aut.mobillab_android.model.ui.Poke
import hu.bme.aut.mobillab_android.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class ListFragment : Fragment(), PokeAdapter.PokeListListener, DetailDialogFragment.DetailDialogListener {

    companion object {
        fun newInstance() = ListFragment()
    }

    private lateinit var pokeAdapter: PokeAdapter

    private val viewModel: ListViewModel by viewModels()

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvWelcomeUser.text = "Welcome " + (requireActivity().application as PokeApplication).currentUsername
        initPokePagerAdapter()
        render()
    }

    private fun render() {
        viewModel.state.observe(this.viewLifecycleOwner){ state ->
            if(state.failedToDeletePokeEvent != null){
                Toast.makeText(requireContext(), state.failedToDeletePokeEvent?.msg ?: "Failed to permanently delete pokemon", Toast.LENGTH_LONG).show()
                viewModel.failedToDeletePokeEventReceived()
            }
            if(state.failedToAddPokeToFavouritesEvent != null){
                Toast.makeText(requireContext(), state.failedToAddPokeToFavouritesEvent?.msg ?: "Failed to permanently add pokemon to favourites", Toast.LENGTH_LONG).show()
                viewModel.failedToAddPokeToFavouritesEventReceived()
            }
            if(state.failedToRemovePokeFromFavourites != null){
                Toast.makeText(requireContext(), state.failedToRemovePokeFromFavourites?.msg ?: "Failed to permanently remove pokemon from favourites", Toast.LENGTH_LONG).show()
                viewModel.removePokemonFromFavouritesEventReceived()
            }
            when(state.loadPokemonsState){
                Initial -> {

                }
                Ongoing -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                Finished -> {
                    binding.progressBar.visibility = View.GONE
                    pokeAdapter.submitList(state.pokemonList)
                }
                is Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.tvError.text = (state.loadPokemonsState as Error).msg ?: "Error while loading pokemon list."
                }
                else -> {}
            }

        }
    }

    private fun initPokePagerAdapter() {
        pokeAdapter = PokeAdapter()
        binding.rvPoke.adapter = pokeAdapter
        binding.rvPoke.layoutManager = GridLayoutManager(requireContext(), 2)
        pokeAdapter.setListener(this)
        (binding.rvPoke.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.loadPokemons()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    override fun deletePoke(id: Int) {
        viewModel.removePokemon(id)
        val mutableList = pokeAdapter.currentList.toMutableList()
        pokeAdapter.submitList(mutableList.filter { it.id != id })
    }

    override fun addPokeToFavourites(id: Int) {
        viewModel.addPokemonToFavourites(id)
        pokeAdapter.submitList(pokeAdapter.currentList.toMutableList().map {
            if (it.id == id) {
                it.copy(isFavourite = true) //toMutableList() is shallow copy only!!
            } else {
                it
            }
        })

    }

    override fun removePokeFromFavourites(id: Int) {
        viewModel.removePokemonFromFavourites(id)
        pokeAdapter.submitList(pokeAdapter.currentList.toMutableList().map {
            if (it.id == id) {
                it.copy(isFavourite = false) //toMutableList() is shallow copy only!!
            } else {
                it
            }
        })
    }

    override fun onPokeClicked(poke: Poke) {
        val dialogFragment = DetailDialogFragment.newInstance(poke)
        lifecycleScope.launch {
            withContext(Dispatchers.Default){
                MyUtil.screenShot(requireActivity()){ bitmap ->
                    val blurBitmap : Bitmap? = BlurView(requireActivity().application).blurBackground(bitmap, 18)
                    lifecycleScope.launch{
                        withContext(Dispatchers.Main){
                            binding.ivBlur.setImageBitmap(blurBitmap)
                            binding.ivBlur.visibility = View.VISIBLE
                            dialogFragment.show(childFragmentManager, DetailDialogFragment.TAG)
                        }
                    }
                }
            }
        }
    }

    override fun onDismissCalled(favStateOnDismiss: Boolean, pokemonId: Int) {
        binding.ivBlur.visibility = View.GONE
        val list = pokeAdapter.currentList.toMutableList().map {
            if (it.id == pokemonId) {
                it.copy(isFavourite = favStateOnDismiss) //toMutableList() is shallow copy only!!
            } else {
                it
            }
        }
        pokeAdapter.submitList(list)
    }


}