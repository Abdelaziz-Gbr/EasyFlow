package com.easyflow.fragments.homeActivity

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
import com.easyflow.databinding.FragmentHomeBinding
import com.easyflow.viewModel.UserDatabaseViewModel

class HomeFragment : Fragment() {
    private lateinit var userDatabaseViewModel: UserDatabaseViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        userDatabaseViewModel = ViewModelProvider(this)[UserDatabaseViewModel::class.java]

        val binding : FragmentHomeBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_home, container, false)
        binding.helloText.append(" ${UserCache.firstName}")
        binding.rechargeButton.setOnClickListener{  recharge(view)  }
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
        Toast.makeText(requireContext(), "work in progress", Toast.LENGTH_SHORT).show()

    }


    private fun logOut(){
        //remove user info from db
        userDatabaseViewModel.removeUser()
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