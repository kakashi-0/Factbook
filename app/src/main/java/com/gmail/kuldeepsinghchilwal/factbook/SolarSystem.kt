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

class SolarSystem : Fragment() {
    private lateinit var binding: FragmentSolarSystemBinding
    private lateinit var viewModel: SolarSystemViewModel

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
            viewModel.solarSystemResourceId = resources.getIdentifier(viewModel.randomSolarSystemIdGenerator(), "string", context?.packageName)
            //adding first created resource in 0th index
            viewModel.resIdList.add(viewModel.ind,viewModel.solarSystemResourceId)
            binding.solarSysTextview.setText(getText(viewModel.solarSystemResourceId))
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //setting resource id variable that will be used to store generated ids for sharing fact
        var solarSystemResId: Int

        //setting onclick listener for  next buton
        binding.solarSysNext.setOnClickListener {
            solarSystemResId = resources.getIdentifier(viewModel.randomSolarSystemIdGenerator(),"string",context?.packageName)
            //saving resource id for viewModel
            viewModel.solarSystemResourceId = solarSystemResId
            Log.i("solar system","index = ${viewModel.ind}")
            //incrementing list index
            viewModel.ind++
            //adding the element created to mutable list
            viewModel.resIdList.add(viewModel.ind,solarSystemResId)
            viewModel.solarSystemResourceId = solarSystemResId
            binding.solarSysTextview.text = getText(solarSystemResId)
        }

        //setting onclick listener on previous button
        binding.solarSysPrevious.setOnClickListener {
            if (viewModel.ind >=1 ) {
                //getting previous index then current one
                viewModel.ind--
                binding.solarSysTextview.setText(viewModel.resIdList[viewModel.ind])
            }

            else{
                Toast.makeText(this.context, "No previous fact to display!", Toast.LENGTH_SHORT).show()
            }
        }

        //setting onclick listener for send button
        binding.solarSysShare.setOnClickListener {
            startActivity(Intent(Intent.ACTION_SEND).setType("text/plain").putExtra(Intent.EXTRA_TEXT,getString(viewModel.resIdList[viewModel.ind])))
        }
    }

}