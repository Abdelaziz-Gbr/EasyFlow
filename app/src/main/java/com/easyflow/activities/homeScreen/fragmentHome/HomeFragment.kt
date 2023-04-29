package com.easyflow.activities.homeScreen.fragmentHome

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
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
        //todo get and cache user info
        binding.helloText.append(" ${UserCache.firstName}")
        binding.navigateToRechargeFragmentButton.setOnClickListener{  recharge(view)  }
        binding.nfcButton.setOnClickListener{  nfc(view)  }
        binding.qrButton.setOnClickListener{  qr(view)  }

        setHasOptionsMenu(true)

        return binding.root
    }

    private fun nfc(view: View?) {
        Toast.makeText(requireContext(), "work in progress", Toast.LENGTH_SHORT).show()
    }

    private fun qr(view: View?) {
        view?.findNavController()?.navigate(HomeFragmentDirections.actionHomeFragmentToQRCodeFragment())
    }

    private fun recharge(view: View?) {
        view?.findNavController()?.navigate(HomeFragmentDirections.actionHomeFragmentToRechargeFragment())

    }


    private fun logOut(){
        //remove user info from db
        viewModel.logOut()
        //clear the cached key
        UserCache.freeAll()
        //go back to sign in screen
        startActivity(Intent(activity, SignInActivity::class.java))
        activity?.finish()
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_logout -> logOut()
        }
        return NavigationUI.onNavDestinationSelected(item!!,
        requireView().findNavController()) || super.onOptionsItemSelected(item)
    }
}