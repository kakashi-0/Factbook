package com.gmail.kuldeepsinghchilwal.factbook

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.gmail.kuldeepsinghchilwal.factbook.databinding.FragmentDogBinding
import com.gmail.kuldeepsinghchilwal.factbook.databinding.FragmentSolarSystemBinding
import java.lang.Boolean.FALSE
import java.lang.Boolean.TRUE

class Dog : Fragment() {
    private lateinit var binding: FragmentDogBinding
    private lateinit var viewModel: DogViewModel
    //numbers used to determine which fact to fetch. Ex. we have R.string.dog1, R.string.dog2 etc. So we determine the last digit using this list
    private  val numberOfFacts = listOf<Int>(1,2,3,4,5,6,7,8,9,10)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        //initializing viewModel
        viewModel = ViewModelProvider(this).get(DogViewModel::class.java)
        // initializing property binding
        binding  = DataBindingUtil.inflate(inflater,R.layout.fragment_dog,container,false)

        // if view is being recreated due to rotation if device or other factor. So that viewmodel is holding the
        // older data for resource id being displayed
        if (viewModel.dogResourceId != 0){
            binding.dogTextview.setText(getText(viewModel.dogResourceId))
        }

        //if its it's initial oncreate it'll generate new resource to be displayed at welcome screen
        else {
            viewModel.shuffledList = numberOfFacts.shuffled()
            viewModel.dogResourceId = resources.getIdentifier(dogIdGenerator(), "string", context?.packageName)
            binding.dogTextview.setText(getText(viewModel.dogResourceId))
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //setting resource id variable that will be used to store generated ids for sharing fact
        var dogResId = 0

        //setting onclick listener for  next buton
        binding.dogNext.setOnClickListener {
            //incrementing shuffle index when its 1 lower than last id and incremenentInd is true
            if (viewModel.shuffleInd <=8 ) {
                viewModel.shuffleInd++
            }
            dogResId = resources.getIdentifier(dogIdGenerator(),"string",context?.packageName)
            //saving resource id for viewModel
            viewModel.dogResourceId = dogResId
            binding.dogTextview.text = getText(dogResId)
        }

        //setting onclick listener on previous button
        binding.dogPrevious.setOnClickListener {
            if (viewModel.shuffleInd >= 1 ) {
                //getting previous index then current one
                viewModel.shuffleInd--
                Log.i("solar previous","shuffle Ind = ${viewModel.shuffleInd}")
                dogResId = resources.getIdentifier(dogIdGenerator(),"string",context?.packageName)
                binding.dogTextview.setText(dogResId)
            }

            else
            {
                Toast.makeText(this.context, "No previous fact to display!", Toast.LENGTH_SHORT).show()
            }
        }

        //setting onclick listener for send button
        binding.dogShare.setOnClickListener {
            startActivity(Intent(Intent.ACTION_SEND).setType("text/plain").putExtra(Intent.EXTRA_TEXT,getString(dogResId)))
        }
    }
    /**
     * This function is used to generate ids to fetch facts from string file
     */
    fun dogIdGenerator (): String {
        val factInCategory = viewModel.shuffledList[viewModel.shuffleInd]

        //if its the last id
        if (viewModel.shuffleInd==9 ){
            Toast.makeText(this.context, "CONGRATULATIONS! you have read all facts in our data!", Toast.LENGTH_SHORT).show()
        }

        //returning int type resource id
        return "dog$factInCategory"
    }
}