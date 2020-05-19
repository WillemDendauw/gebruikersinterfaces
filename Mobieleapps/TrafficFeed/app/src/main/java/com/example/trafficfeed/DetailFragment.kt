package com.example.trafficfeed

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.inflate
import androidx.fragment.app.activityViewModels
import com.example.trafficfeed.databinding.FragmentDetailBinding
import com.example.trafficfeed.ui.TrafficViewModel

/**
 * A simple [Fragment] subclass.
 */
class DetailFragment : Fragment() {

    private val vm: TrafficViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentDetailBinding =
            inflate(inflater, R.layout.fragment_detail, container, false)
        binding.viewmodel = vm
        binding.setLifecycleOwner(this)

        return binding.getRoot()
    }
}
