package com.gmail.kuldeepsinghchilwal.factbook

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.gmail.kuldeepsinghchilwal.factbook.databinding.FragmentSolarSystemBinding

class SolarSystem : Fragment() {
    private lateinit var binding: FragmentSolarSystemBinding
    private lateinit var viewModel: SolarSystemViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
       binding  = DataBindingUtil.inflate(inflater,R.layout.fragment_solar_system,container,false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SolarSystemViewModel::class.java)

        var solarSystemResId = viewModel.solarSystemResourceId
        // if view is being recreated due to rotation if device or other factor. So that viewmodel is holding the
        // older data for resource id being displayed
        if (solarSystemResId != 0){
            binding.solarSysTextview.setText(getText(solarSystemResId))
        }
    //setting onclick listener for  next buton
        binding.solarSysNext.setOnClickListener {
            solarSystemResId = resources.getIdentifier(viewModel.randomSolarSystemIdGenerator(),"string",context?.packageName)
            viewModel.solarSystemResourceId = solarSystemResId
            binding.solarSysTextview.setText(getText(solarSystemResId))
        }

        //setting onclick listener for send button
        binding.solarSysShare.setOnClickListener {
            startActivity(Intent(Intent.ACTION_SEND).setType("Text/Plain").putExtra(Intent.EXTRA_TEXT,getString(solarSystemResId)))
        }
    }

}