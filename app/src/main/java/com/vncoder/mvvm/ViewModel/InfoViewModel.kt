package com.vncoder.mvvm.ViewModel

import android.app.Activity
import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vncoder.mvvm.network.RestApiService
import com.vncoder.mvvm.network.RetrofitInstance
import com.vncoder.retrofit2_employee.Model.ContactCreate
import kotlinx.android.synthetic.main.activity_info.*
import kotlinx.android.synthetic.main.processbar.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InfoViewModel(application: Application):ViewModel()  {


    fun createData(contactCreate: ContactCreate,activity: Activity)  {
        activity.llProgressBarInfo.visibility = View.VISIBLE
        val apiService: RestApiService = RetrofitInstance.instance
        val call: Call<ContactCreate> = apiService.postContact(contactCreate)
        call.enqueue(object : Callback<ContactCreate> {
            override fun onResponse(call: Call<ContactCreate>, response: Response<ContactCreate>) {
                if (response.isSuccessful){
                    activity.finish()
                    activity.llProgressBarInfo.visibility = View.GONE
                }
            }
            override fun onFailure(call: Call<ContactCreate>, t: Throwable) {
                Log.d("thisxxx", t.message.toString())
            }
        })

        activity.llProgressBarInfo.btn_cancel.setOnClickListener {
            call.cancel()
            activity.llProgressBarInfo.visibility = View.GONE
        }
    }




    class InfoViewModelFactory(private val application: Application) : ViewModelProvider.Factory{

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(InfoViewModel::class.java)){
                return InfoViewModel(application) as T
            }
            throw IllegalAccessException("Unable constructor ViewModel")
        }

    }
}