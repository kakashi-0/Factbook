package com.gmail.kuldeepsinghchilwal.factbook

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.ViewModelProviders.*
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.gmail.kuldeepsinghchilwal.factbook.databinding.FragmentHomeBinding



class HomeFragment : Fragment() {
    private lateinit var viewModel: HomeFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        // Inflate the layout for this fragment
        val binding: FragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        //Creating and initializing view model
        viewModel = ViewModelProvider(this).get(HomeFragmentViewModel::class.java)
        //To enable menu
        setHasOptionsMenu(true)
        //syncing resource id from HomeFragmentViewModel
        var resID = viewModel.resourceId
        //setting text if fragment was recreated due to screen rotation or other factors
        if (resID != 0) {
            binding.Fact.setText(resID)
            binding.NextButton.isVisible = true
            binding.PreviousButton.isVisible = true
            binding.ShareButton.isVisible = true
            binding.startButton.isVisible = false
        }

        binding.startButton?.setOnClickListener {
            binding.NextButton.isVisible = true
            binding.PreviousButton.isVisible = true
            binding.ShareButton.isVisible = true
            binding.startButton.isVisible = false
        }
        //next button on click listener
        binding.NextButton.setOnClickListener {
            resID = resources.getIdentifier(viewModel.randomIdGenerator(), "string", context?.packageName)
            //saving resource id for viewModel
            viewModel.resourceId = resID
            //incrementing list index
            viewModel.ind++
            //adding the element created to mutable list
            viewModel.resIdList.add(viewModel.ind,resID)
            //Displaying resource text
            binding.Fact.setText(resID)
        }

        //setting onclick listener on previous button
        binding.PreviousButton.setOnClickListener {
            if (viewModel.ind >=2) {
                //getting previous index then current one
                viewModel.ind--
                //saving resource id for viewModel
                viewModel.resourceId = viewModel.resIdList[viewModel.ind]
                binding.Fact.setText(viewModel.resourceId)
            }
            else{
                Toast.makeText(this.context, "No previous fact to display!", Toast.LENGTH_SHORT).show()
            }
        }

        //share button on click listener
        binding.ShareButton.setOnClickListener {
            if (resID != 0) {
                //declaring our intent action
                startActivity(
                    Intent(Intent.ACTION_SEND).setType("text/plain")
                        .putExtra(Intent.EXTRA_TEXT, getString(viewModel.resIdList[viewModel.ind]))
                )
            }
            else{
                Toast.makeText(this.context,"No fact visible!", Toast.LENGTH_SHORT).show()
            }
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