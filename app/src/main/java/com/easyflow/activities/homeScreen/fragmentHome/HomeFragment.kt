package com.easyflow.activities.homeScreen.fragmentHome

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.easyflow.R
import com.easyflow.activities.signIn.SignInActivity
import com.easyflow.cache.UserCache
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

        viewModel.navigateToRechargeScreen.observe(viewLifecycleOwner){navigateToRecharge ->
            navigateToRecharge?.let {
                if(navigateToRecharge){
                    view?.findNavController()?.navigate(HomeFragmentDirections.actionHomeFragmentToRechargeFragment())
                    viewModel.onRechargeNavigated()
                }
            }
        }
        viewModel.navigateToQrScreen.observe(viewLifecycleOwner){ navigateToQrScreen ->
            navigateToQrScreen?.let {
                if(navigateToQrScreen){
                    view?.findNavController()?.navigate(HomeFragmentDirections.actionHomeFragmentToQRCodeFragment())
                    viewModel.onQrNavigated()
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

        //setHasOptionsMenu(true)

        return binding.root
    }
/*    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_logout -> viewModel.onLogoutClicked()
        }
        return NavigationUI.onNavDestinationSelected(item!!,
        requireView().findNavController()) || super.onOptionsItemSelected(item)
    }*/
}