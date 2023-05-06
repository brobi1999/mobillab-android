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
import hu.bme.aut.mobillab_android.util.Error
import hu.bme.aut.mobillab_android.util.Finished
import hu.bme.aut.mobillab_android.util.Initial
import hu.bme.aut.mobillab_android.util.Ongoing

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
        binding.btnLogin.setOnClickListener {
            viewModel.login(binding.etName.text.toString())
        }
        binding.btnSaveAccount.setOnClickListener {
            viewModel.register(binding.etName.text.toString())
        }
        render()
    }

    private fun render() {
        viewModel.state.observe(viewLifecycleOwner){ state ->
            if(state.authSuccessEvent != null){
                viewModel.authSuccessEventReceived()
                navigateToListFragment()
            }
            if(state.loginState == Ongoing || state.registerState == Ongoing){
                binding.progressBarCyclic.visibility = View.VISIBLE
            }
            else{
                binding.progressBarCyclic.visibility = View.GONE
            }
            if(state.loginState is Error || state.registerState is Error){
                val loginError = if(state.loginState is Error) (state.loginState as Error).msg ?: "" else ""
                val registerError = if(state.registerState is Error) (state.registerState as Error).msg ?: "" else ""
                binding.tvError.text = loginError + "\n" + registerError
                binding.tvError.visibility = View.VISIBLE
            }
            else{
                binding.tvError.visibility = View.GONE
            }
        }
    }

    private fun navigateToListFragment(){
        val action = LoginFragmentDirections.actionLoginFragmentToListFragment()
        view?.findNavController()?.navigate(action)
    }

}