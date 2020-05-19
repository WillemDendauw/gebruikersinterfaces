package com.example.trafficfeed

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.*
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.trafficfeed.databinding.FragmentMainBinding
import com.example.trafficfeed.ui.TrafficViewModel

/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment() {

    private val vm: TrafficViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentMainBinding =
            inflate(inflater, R.layout.fragment_main, container, false)
        binding.fragment = this
        return binding.getRoot()
    }

    fun click(v: View){
        vm.selectRandom()
        if(activity?.findViewById<View>(R.id.navhostfragment) != null) {
            Navigation.findNavController(v).navigate(R.id.action_mainFragment_to_detailFragment)
        }
    }
}
