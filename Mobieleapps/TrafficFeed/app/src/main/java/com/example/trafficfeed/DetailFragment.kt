package com.example.trafficfeed

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.trafficfeed.databinding.FragmentDetailBinding
import com.example.trafficfeed.ui.MyViewModel

/**
 * A simple [Fragment] subclass.
 */
class DetailFragment : Fragment() {

    val vm : MyViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding : FragmentDetailBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_detail,container,false)
        binding.viewmodel = vm

        //+/ stelt de lifecycleOwner in voor het LiveData-object
        //+/ als je dit niet instelt, dan werkt de 'next'-knop niet na draaien van de device
        binding.lifecycleOwner = this
        return binding.getRoot()
    }
}