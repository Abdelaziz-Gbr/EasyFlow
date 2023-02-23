package com.easyflow.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.easyflow.R
import com.easyflow.activities.SignInActivity
import com.easyflow.cache.UserKey
import com.easyflow.databinding.FragmentHomeBinding
import com.easyflow.viewModel.UserViewModel
import kotlin.math.log

class HomeFragment : Fragment() {
    private lateinit var userViewModel: UserViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        // Inflate the layout for this fragment
        val binding : FragmentHomeBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_home, container, false)
        binding.rechargeButton.setOnClickListener {
            //navigate from home fragment to recharge fragment
        }
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    private fun logOut(){
        //remove user info from db
        userViewModel.removeUser()
        //clear the cached key
        UserKey.key = null
        //go back to sign in screen
        startActivity(Intent(activity, SignInActivity::class.java))
        activity?.finish()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_logout -> logOut()
            else -> Toast.makeText(requireContext(), "in progress", Toast.LENGTH_LONG).show()
        }
        return super.onOptionsItemSelected(item)
    }
}