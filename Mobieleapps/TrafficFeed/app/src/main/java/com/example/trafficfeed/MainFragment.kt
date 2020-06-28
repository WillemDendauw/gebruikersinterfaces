package com.example.trafficfeed

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.trafficfeed.databinding.FragmentMainBinding
import com.example.trafficfeed.ui.MyViewModel

/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment() {

    // een object an de klasse MyViewModel aanmaken doe je niet via rechtstreeks oproepen van de
    // constructor (die vraagt trouwens de 'application' waar het object bij hoort)
    // val vm = MyViewModel(what_application_should_go_here)

    val vm: MyViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentMainBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        binding.fragment = this
        return binding.getRoot()

        // de layout inflaten voor dit fragment
        // Onderstaande (gegenereerde) code valt weg; is vervangen door de code voor databinding.
        // return inflater.inflate(R.layout.fragment_main, container, false)
    }

    fun showRandom(view: View){
        vm.chooseRandom()
        // rechtstreekse navigatie naar het gewenste fragment zou ook kunnen:
        // Navigation.findNavController(view).navigate(R.id.detailFragment)
        // maar sowieso mogen beide fragmenten (waartussen je wil navigeren) niet op hetzelfde scherm staan
        // anders is er geen sprake van navigatie!
        if(activity?.findViewById<View>(R.id.navhostfragment) != null) {
            Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_detailFragment)
        }
    }
}