package com.vncoder.mvvm.ViewModel

import android.app.Activity
import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vncoder.mvvm.Repository.ContactRepository
import com.vncoder.mvvm.model.Contact
import com.vncoder.mvvm.network.RestApiService
import com.vncoder.mvvm.network.RetrofitInstance
import com.vncoder.retrofit2_employee.Model.ContactCreate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InfoViewModel(application: Application):ViewModel()  {

//    private val contactRepository2: ContactRepository = ContactRepository(application)

    fun CreateData(contactCreate: ContactCreate,activity: Activity)  {
        val apiService: RestApiService = RetrofitInstance.instance
        val call: Call<ContactCreate> = apiService.postContact(contactCreate)
        call.enqueue(object : Callback<ContactCreate> {
            override fun onResponse(call: Call<ContactCreate>, response: Response<ContactCreate>) {
                if (response.isSuccessful){
                    activity.finish()
                }
            }
            override fun onFailure(call: Call<ContactCreate>, t: Throwable) {
                Log.d("thisxxx", t.message.toString())
            }
        })
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