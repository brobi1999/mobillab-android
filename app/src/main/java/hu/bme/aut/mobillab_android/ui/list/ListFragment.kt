package hu.bme.aut.mobillab_android.ui.list

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.mobillab_android.databinding.FragmentListBinding

@AndroidEntryPoint
class ListFragment : Fragment(){

    companion object {
        fun newInstance() = ListFragment()
    }

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
        viewModel.state.observe(this.viewLifecycleOwner){ state->
            when{

            }
        }
    }









}