package com.easyflow.utils

import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import com.easyflow.R

class LoadingDialog(private val activity: FragmentActivity) {
    private lateinit var dialog: AlertDialog

    fun startLoadingAnimation(){
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        with(builder){
            setView(inflater.inflate(R.layout.custom_loading_layout, null))
            setCancelable(false)
            dialog = show()
        }
    }
    fun endLoadingAnimation(){
        dialog.dismiss()
    }
}