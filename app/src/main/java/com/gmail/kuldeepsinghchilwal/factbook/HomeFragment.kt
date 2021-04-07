package com.gmail.kuldeepsinghchilwal.factbook

import android.app.*
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.renderscript.ScriptGroup
import android.view.*
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.gmail.kuldeepsinghchilwal.factbook.databinding.FragmentHomeBinding
import com.gmail.kuldeepsinghchilwal.factbook.viewmodels.HomeFragmentViewModel


class HomeFragment : Fragment() {
    private lateinit var viewModel: HomeFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        val binding: FragmentHomeBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home,
            container,
            false
        )
        //Creating and initializing view model
        viewModel = ViewModelProvider(this).get(HomeFragmentViewModel::class.java)
        //To enable menu
        setHasOptionsMenu(true)
        // Setting up LiveData observation relationship
        viewModel.resourceId.observe(viewLifecycleOwner, {newFact ->
                //checking if resourceID.value is null, if null, set resID to 0
            if (newFact !=0) {
                binding.Fact.setText(newFact)
            }

        })

        //setting text if fragment was recreated due to screen rotation or other factors
        if (viewModel.resourceId.value != 0) {
            binding.NextButton.isVisible = true
            binding.PreviousButton.isVisible = true
            binding.ShareButton.isVisible = true
            binding.startButton.isVisible = false
        }

        binding.startButton.setOnClickListener {
            binding.NextButton.isVisible = true
            binding.PreviousButton.isVisible = true
            binding.ShareButton.isVisible = true
            binding.startButton.isVisible = false
           viewModel.randomIdGenerator()
            viewModel.updatingFactsList()
        }
        //next button on click listener
        binding.NextButton.setOnClickListener {
            viewModel.randomIdGenerator()
            viewModel.updatingFactsList()
        }

        //setting onclick listener on previous button
        binding.PreviousButton.setOnClickListener {
           viewModel.showingPrevFact()
        }

        //share button on click listener
        binding.ShareButton.setOnClickListener {
            if (viewModel.resourceId.value != 0) {
                val shareString = viewModel.stringToBeShared()
                    //declaring our intent action
                startActivity(
                    Intent(Intent.ACTION_SEND).setType("text/plain")
                        .putExtra(Intent.EXTRA_TEXT, shareString)
                )
            }
            else{
                Toast.makeText(this.context, "No fact visible!", Toast.LENGTH_SHORT).show()
            }

        }

        createChannel(
            getString(R.string.daily_fact_notification_channel_id),
            getString(R.string.dailyFact_notification_channeName)
        )



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

private  fun createChannel(channelID: String, channelName: String){
//    stating a channel
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
    val notificationChannel = NotificationChannel(
        channelID,
        channelName,
//        setting notification importance
        NotificationManager.IMPORTANCE_DEFAULT
    )
notificationChannel.enableLights(true)
        notificationChannel.enableVibration(true)
        notificationChannel.description =  " Read Daily Facts."

//        getting an instance on NotificationManager
        val notificationManager = requireActivity().getSystemService(
            NotificationManager::class.java
        )
    notificationManager.createNotificationChannel(notificationChannel)
    }
}

}