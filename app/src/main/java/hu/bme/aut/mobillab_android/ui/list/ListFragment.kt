package hu.bme.aut.mobillab_android.ui.list

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import hu.bme.aut.mobillab_android.databinding.FragmentListBinding

class ListFragment : Fragment(){

    companion object {
        fun newInstance() = ListFragment()
    }

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

    }









}