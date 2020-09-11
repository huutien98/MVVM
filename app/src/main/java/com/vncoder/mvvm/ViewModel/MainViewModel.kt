package com.vncoder.mvvm.ViewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vncoder.mvvm.Repository.ContactRepository
import com.vncoder.mvvm.network.RestApiService
import com.vncoder.mvvm.network.RetrofitInstance
import com.vncoder.mvvm.model.Contact
import com.vncoder.retrofit2_employee.Model.JsonObject
import retrofit2.Call
import retrofit2.Response


class MainViewModel(application: Application) : ViewModel(){
//    private val contactRepository: ContactRepository = ContactRepository(application)
    private var listContact: ArrayList<Contact> = ArrayList()

//    fun getData(): MutableLiveData<List<Contact>> = contactRepository.getMutableLiveData()

    fun DeleteData(contactID : String)  {
        val apiService: RestApiService = RetrofitInstance.instance
        val call: Call<Contact> = apiService.deleteContact(contactID)
        call.enqueue(object : retrofit2.Callback<Contact> {
            override fun onResponse(call: Call<Contact>, response: Response<Contact>) {
                if (response.isSuccessful) {
                    getMutableLiveData()
                    Log.d("this1", "error")
                } else {
                    Log.d("this2", "error")
                }
            }
            override fun onFailure(call: Call<Contact>, t: Throwable) {
                Log.d("this3", "error")
            }
        })

    }

    private val mutableLiveData: MutableLiveData<List<Contact>> = MutableLiveData<List<Contact>>()

    fun getMutableLiveData(): MutableLiveData<List<Contact>> {
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

    class NoteViewModelFactory(private val application: Application) :ViewModelProvider.Factory{

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)){
                return MainViewModel(application) as T
            }
            throw IllegalAccessException("Unable constructor ViewModel")
        }

    }

}