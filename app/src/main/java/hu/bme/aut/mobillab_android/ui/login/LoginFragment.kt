package hu.bme.aut.mobillab_android.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.mobillab_android.databinding.FragmentLoginBinding

@AndroidEntryPoint
class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private val viewModel: LoginViewModel by viewModels()

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnNavigate.setOnClickListener {
            navigateToListFragment()
        }

        viewModel.state.observe(this.viewLifecycleOwner){ state->
            when{

            }
        }
    }

    private fun navigateToListFragment(){
        val action = LoginFragmentDirections.actionLoginFragmentToListFragment()
        view?.findNavController()?.navigate(action)
    }

}