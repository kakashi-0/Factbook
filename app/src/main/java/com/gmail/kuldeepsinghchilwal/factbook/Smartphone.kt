package com.gmail.kuldeepsinghchilwal.factbook

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.gmail.kuldeepsinghchilwal.factbook.databinding.FragmentSmartphoneBinding

class Smartphone : Fragment() {
    private lateinit var binding: FragmentSmartphoneBinding
    private lateinit var viewModel: FragmentViewModel

    //numbers used to determine which fact to fetch. Ex. we have R.string.smartphone1, R.string.smartphone2 etc. So we determine the last digit using this list
    private val numberOfFacts = listOf<Int>(1, 2, 3, 4, 5, 6, 7, 8, 9, 10,11,12,13,14,15,16,17,18,19,20)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //initializing viewModel
        viewModel = ViewModelProvider(this).get(FragmentViewModel::class.java)
        // initializing property binding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_smartphone, container, false)

        // if view is being recreated due to rotation if device or other factor. So that viewmodel is holding the
        // older data for resource id being displayed
        if (viewModel.resourceId != 0) {
            // Log.i("solar system.kt","solarsystemresid $solarSystemResId")
            binding.smartphoneTextview.text = getText(viewModel.resourceId)
        }

        //if its it's initial oncreate it'll generate new resource to be displayed at welcome screen
        else {
            viewModel.shuffledList = numberOfFacts.shuffled()
            viewModel.resourceId =
                resources.getIdentifier(smartphoneIdGenerator(), "string", context?.packageName)
            binding.smartphoneTextview.text = getText(viewModel.resourceId)
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //setting resource id variable that will be used to store generated ids for sharing fact
        var smartphoneResId = viewModel.resourceId

        //setting onclick listener for  next buton
        binding.smartphoneNext.setOnClickListener {
            //viewModel.shuffleInd <= n-2
            if (viewModel.shuffleInd <= 18) {
                viewModel.shuffleInd++
                smartphoneResId = resources.getIdentifier(smartphoneIdGenerator(), "string", context?.packageName)
                //saving resource id for viewModel
                viewModel.resourceId = smartphoneResId
            }
            //if its the last id(n-1)
            if (viewModel.shuffleInd == 19) {
                Toast.makeText(
                    this.context,
                    "CONGRATULATIONS! you have read all facts in our data!",
                    Toast.LENGTH_SHORT
                ).show()
            }
            binding.smartphoneTextview.text = getText(smartphoneResId)
        }

        //setting onclick listener on previous button
        binding.smartphonePrevious.setOnClickListener {
            if (viewModel.shuffleInd >= 1) {
                //getting previous index then current one
                viewModel.shuffleInd--
                smartphoneResId =
                    resources.getIdentifier(smartphoneIdGenerator(), "string", context?.packageName)
                viewModel.resourceId = smartphoneResId
                binding.smartphoneTextview.setText(smartphoneResId)
            } else {
                Toast.makeText(this.context, "No previous fact to display!", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        //setting onclick listener for send button
        binding.smartphoneShare.setOnClickListener {
            var shareString = getString(smartphoneResId) + "\n \n *\n *\n *\nFor more amazing facts Download FactBook:"+ "https://play.google.com/store/apps/details?id=com.gmail.kuldeepsinghchilwal.factbook"

            //declaring our intent action
                startActivity(Intent(Intent.ACTION_SEND).setType("text/plain").putExtra(Intent.EXTRA_TEXT, shareString))
        }
    }

    /**
     * This function is used to generate ids to fetch facts from string file
     */
    private fun smartphoneIdGenerator(): String {
        val factInCategory = viewModel.shuffledList[viewModel.shuffleInd]
                //returning int type resource id
        return "smartphone$factInCategory"
    }
}