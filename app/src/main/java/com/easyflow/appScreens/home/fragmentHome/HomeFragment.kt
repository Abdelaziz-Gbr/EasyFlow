package com.easyflow.appScreens.home.fragmentHome

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.easyflow.R
import com.easyflow.activities.trips.TripsActivity
import com.easyflow.database.UserDatabase
import com.easyflow.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var viewModel: HomeFragmentViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val binding : FragmentHomeBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_home, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = UserDatabase.getDatabase(application).userDao()

        val viewModelFactory = HomeFragmentViewModelFactory(dataSource)
        viewModel = ViewModelProvider(this, viewModelFactory)[HomeFragmentViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.navigateToTripsScreen.observe(viewLifecycleOwner){ navigateToQrScreen ->
            navigateToQrScreen?.let {
                if(navigateToQrScreen){
                    val intent = Intent(requireActivity(), TripsActivity::class.java)
                    startActivity(intent)
                    viewModel.onTripsNavigated()
                }
            }
        }

        viewModel.navigateToHistoryFragment.observe(viewLifecycleOwner){ nav ->
            nav?.let {
                if(nav){
                    view?.findNavController()?.navigate(HomeFragmentDirections.actionHomeFragmentToHistoryFragment())
                    viewModel.onHistoryNavigated()
                }
            }
        }
        return binding.root
    }
}