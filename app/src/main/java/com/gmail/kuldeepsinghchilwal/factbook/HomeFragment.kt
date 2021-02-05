package com.gmail.kuldeepsinghchilwal.factbook

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.ViewModelProviders.*
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.gmail.kuldeepsinghchilwal.factbook.databinding.FragmentHomeBinding



class HomeFragment : Fragment() {
    private lateinit var viewModel: HomeFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val binding: FragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        //Creating and initializing view model
        viewModel = ViewModelProviders.of(this).get(HomeFragmentViewModel::class.java)
        //To enable menu
        setHasOptionsMenu(true)
        //syncing resource id from HomeFragmentViewModel
        var resID = viewModel.resourceId
        //setting text if fragment was recreated due to screen rotation or other factors
        if (resID != 0) {
            binding.Fact.setText(resID)
        }
        //next button on click listener
        binding.NextButton.setOnClickListener {
            resID = resources.getIdentifier(viewModel.randomIdGenerator(), "string", context?.packageName)
            //saving resource id for viewModel
            viewModel.resourceId = resID
            //Displaying resource text
            binding.Fact.setText(resID)
        }

        //share button on click listener
        binding.ShareButton.setOnClickListener {
            //declaring our intent action
            startActivity(Intent(Intent.ACTION_SEND).setType("text/plain").putExtra(Intent.EXTRA_TEXT, getString(resID)))
        }

        return binding.root
    }

    //inflating overflow menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow_menu, menu)
    }

    //what needs to be done when a  menu item is selected
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, requireView().findNavController())
                ||super.onOptionsItemSelected(item)
    }


}