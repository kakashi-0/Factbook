package com.gmail.kuldeepsinghchilwal.factbook

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.gmail.kuldeepsinghchilwal.factbook.databinding.FragmentDonateBinding


class onateFragment : Fragment() {
    lateinit var binding: FragmentDonateBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding  = DataBindingUtil.inflate(inflater,R.layout.fragment_donate,container,false)
        // Inflate the layout for this fragment
        binding.DonateUSDButton.setOnClickListener{
            Toast.makeText(context, "Thank you but option not available yet!", Toast.LENGTH_SHORT).show()
        }
        binding.DonateINRButton.setOnClickListener{
            Toast.makeText(context, "Thank you but option not available yet!", Toast.LENGTH_SHORT).show()
        }

    return binding.root}
}