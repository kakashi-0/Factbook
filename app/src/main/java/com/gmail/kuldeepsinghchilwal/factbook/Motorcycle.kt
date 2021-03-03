package com.gmail.kuldeepsinghchilwal.factbook

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.gmail.kuldeepsinghchilwal.factbook.databinding.FragmentMotorcycleBinding

class Motorcycle : Fragment() {
    private lateinit var binding: FragmentMotorcycleBinding
    private lateinit var viewModel: FragmentViewModel
    //numbers used to determine which fact to fetch. Ex. we have R.string.motorcycle1, R.string.motorcycle2 etc. So we determine the last digit using this list
    private  val numberOfFacts = listOf<Int>(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        //initializing viewModel
        viewModel = ViewModelProvider(this).get(FragmentViewModel::class.java)

        // initializing property binding
        binding  = DataBindingUtil.inflate(inflater,R.layout.fragment_motorcycle,container,false)

        // if view is being recreated due to rotation if device or other factor. So that viewmodel is holding the
        // older data for resource id being displayed
        if (viewModel.resourceId != 0){
            binding.motorcycleTextview.setText(getText(viewModel.resourceId))
        }

        //if its it's initial oncreate it'll generate new resource to be displayed at welcome screen
        else {
            viewModel.shuffledList = numberOfFacts.shuffled()
            viewModel.resourceId = resources.getIdentifier(motorcycleIdGenerator(), "string", context?.packageName)
            binding.motorcycleTextview.setText(getText(viewModel.resourceId))
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //setting resource id variable that will be used to store generated ids for sharing fact
        var motorcycleResId = 0

        //setting onclick listener for  next buton
        binding.motorcycleNext.setOnClickListener {
            if (viewModel.shuffleInd <=8 ) {
                viewModel.shuffleInd++
                motorcycleResId = resources.getIdentifier(motorcycleIdGenerator(),"string",context?.packageName)
                //saving resource id for viewModel
                viewModel.resourceId = motorcycleResId
            }
            //if its the last id
            if (viewModel.shuffleInd==9 ){
                Toast.makeText(this.context, "CONGRATULATIONS! you have read all facts in our data!", Toast.LENGTH_SHORT).show()
            }

            binding.motorcycleTextview.text = getText(motorcycleResId)
        }

        //setting onclick listener on previous button
        binding.motorcyclePrevious.setOnClickListener {
            if (viewModel.shuffleInd >= 1 ) {
                //getting previous index then current one
                viewModel.shuffleInd--
                motorcycleResId = resources.getIdentifier(motorcycleIdGenerator(),"string",context?.packageName)
                binding.motorcycleTextview.setText(motorcycleResId)
            }

            else
            {
                Toast.makeText(this.context, "No previous fact to display!", Toast.LENGTH_SHORT).show()
            }
        }

        //setting onclick listener for send button
        binding.motorcycleShare.setOnClickListener {
            startActivity(Intent(Intent.ACTION_SEND).setType("text/plain").putExtra(Intent.EXTRA_TEXT,getString(motorcycleResId)))
        }
    }
    /**
     * This function is used to generate ids to fetch facts from string file
     */
    private fun motorcycleIdGenerator (): String {
        val factInCategory = viewModel.shuffledList[viewModel.shuffleInd]

        //returning int type resource id
        return "motorcycle$factInCategory"
    }
}