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
import com.gmail.kuldeepsinghchilwal.factbook.databinding.FragmentSolarSystemBinding
import java.lang.Boolean.FALSE
import java.lang.Boolean.TRUE

class SolarSystem : Fragment() {
    private lateinit var binding: FragmentSolarSystemBinding
    private lateinit var viewModel: SolarSystemViewModel
    //numbers used to determine which fact to fetch. Ex. we have R.string.solarsystem1, R.string.solarsystem2 etc. So we determine the last digit using this list
    private  val numberOfFacts = listOf<Int>(1,2,3,4,5,6,7,8,9,10)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        Log.i("solar system oncreate", "oncreate called")
        //initializing viewModel
        viewModel = ViewModelProvider(this).get(SolarSystemViewModel::class.java)
        // initializing property binding
        binding  = DataBindingUtil.inflate(inflater,R.layout.fragment_solar_system,container,false)

        // if view is being recreated due to rotation if device or other factor. So that viewmodel is holding the
        // older data for resource id being displayed
        if (viewModel.solarSystemResourceId != 0){
           // Log.i("solar system.kt","solarsystemresid $solarSystemResId")
            binding.solarSysTextview.setText(getText(viewModel.solarSystemResourceId))
        }

        //if its it's initial oncreate it'll generate new resource to be displayed at welcome screen
        else {
            viewModel.shuffledList = numberOfFacts.shuffled()
            viewModel.solarSystemResourceId = resources.getIdentifier(solarSystemIdGenerator(TRUE), "string", context?.packageName)
            binding.solarSysTextview.setText(getText(viewModel.solarSystemResourceId))
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //setting resource id variable that will be used to store generated ids for sharing fact
        var solarSystemResId = 0

        //setting onclick listener for  next buton
        binding.solarSysNext.setOnClickListener {
            solarSystemResId = resources.getIdentifier(solarSystemIdGenerator(TRUE),"string",context?.packageName)
            //saving resource id for viewModel
            viewModel.solarSystemResourceId = solarSystemResId
            binding.solarSysTextview.text = getText(solarSystemResId)
        }

        //setting onclick listener on previous button
        binding.solarSysPrevious.setOnClickListener {
            if (viewModel.shuffleInd >= 1 ) {
                //getting previous index then current one
                viewModel.shuffleInd--
                Log.i("car previous","shuffle Ind = ${viewModel.shuffleInd}")
                solarSystemResId = resources.getIdentifier(solarSystemIdGenerator(FALSE),"string",context?.packageName)
                binding.solarSysTextview.setText(solarSystemResId)
            }

            else
            {
                Toast.makeText(this.context, "No previous fact to display!", Toast.LENGTH_SHORT).show()
            }
        }

        //setting onclick listener for send button
        binding.solarSysShare.setOnClickListener {
            startActivity(Intent(Intent.ACTION_SEND).setType("text/plain").putExtra(Intent.EXTRA_TEXT,getString(solarSystemResId)))
        }
    }
    /**
     * This function is used to generate ids to fetch facts from string file
     *
     * @property incrementInd true or false depending on whether we want to  increment the shuffle index
     */
    fun solarSystemIdGenerator (incrementInd: Boolean): String {
        val factInCategory = viewModel.shuffledList[viewModel.shuffleInd]

        //if its the last id
        if (viewModel.shuffleInd==9 ){
            Toast.makeText(this.context, "CONGRATULATIONS! you have read all facts in our data!", Toast.LENGTH_SHORT).show()
        }
        //incrementing shuffle index when its 1 lower than last id and incremenentInd is true
        if (viewModel.shuffleInd <=8 && incrementInd) {
            viewModel.shuffleInd++
            Log.i("solar sys fact id generator", "shuffled list ${viewModel.shuffleInd}")

        }
        //returning int type resource id
        return "solarsystem$factInCategory"
    }
}