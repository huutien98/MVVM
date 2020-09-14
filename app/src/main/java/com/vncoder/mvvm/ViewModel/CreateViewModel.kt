package com.vncoder.mvvm.ViewModel

import android.app.Activity
import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vncoder.mvvm.model.Contact
import com.vncoder.mvvm.network.RestApiService
import com.vncoder.mvvm.network.RetrofitInstance
import com.vncoder.retrofit2_employee.Model.ContactCreate
import com.vncoder.retrofit2_employee.Model.JsonObject
import kotlinx.android.synthetic.main.activity_create.*

import kotlinx.android.synthetic.main.processbar.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateViewModel(application: Application):ViewModel() {
    private var listContact: ArrayList<Contact> = ArrayList()
    private val mutableLiveData: MutableLiveData<List<Contact>> = MutableLiveData<List<Contact>>()

    fun createData(contactCreate: ContactCreate,activity: Activity )  {
        activity.llProgressBarCreate.visibility = View.VISIBLE
        val apiService: RestApiService = RetrofitInstance.instance
        val call: Call<ContactCreate> = apiService.postContact(contactCreate)
        call.enqueue(object : Callback<ContactCreate> {
            override fun onResponse(call: Call<ContactCreate>, response: Response<ContactCreate>) {
                if (response.isSuccessful){

                     activity.finish()
                     activity.llProgressBarCreate.visibility = View.GONE
                }
            }
            override fun onFailure(call: Call<ContactCreate>, t: Throwable) {
                Log.d("thisxxx",t.message.toString())
            }
        })
        activity.llProgressBarCreate.btn_cancel.setOnClickListener {
            call.cancel()
        }

    }

    fun getMutableLiveData(activity: Activity): MutableLiveData<List<Contact>> {
        val apiService: RestApiService = RetrofitInstance.instance
        val call: Call<JsonObject> = apiService.getdata()
        call.enqueue(object : retrofit2.Callback<JsonObject?> {
            override fun onResponse(call: Call<JsonObject?>?, response: Response<JsonObject?>) {
                if (response.isSuccessful) {
                    val JsonObject: JsonObject = response.body()!!
                    listContact = JsonObject.contacts as ArrayList<Contact>
                    mutableLiveData.value = listContact
                } else {
                    Log.e("error", response.message().toString())
                }
            }
            override fun onFailure(call: Call<JsonObject?>?, t: Throwable) {
                Log.d("ListSize", " - > Error    " + t.message.toString());
            }
        })
        return mutableLiveData
    }

    class CreateViewModelFactory(private val application: Application) : ViewModelProvider.Factory{

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CreateViewModel::class.java)){
                return CreateViewModel(application) as T
            }
            throw IllegalAccessException("Unable constructor ViewModel")
        }

    }

}