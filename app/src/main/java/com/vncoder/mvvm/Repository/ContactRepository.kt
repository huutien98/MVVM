package com.vncoder.mvvm.Repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vncoder.mvvm.Network.RestApiService
import com.vncoder.mvvm.Network.RetrofitInstance
import com.vncoder.retrofit2_employee.Model.Contact
import com.vncoder.retrofit2_employee.Model.ContactCreate
import com.vncoder.retrofit2_employee.Model.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ContactRepository (application: Application) {

    private var listContact: ArrayList<Contact> = ArrayList()
    private val mutableLiveData: MutableLiveData<List<Contact>> = MutableLiveData<List<Contact>>()

    fun getMutableLiveData(): MutableLiveData<List<Contact>> {
        val apiService: RestApiService = RetrofitInstance.instance
        val call: Call<JsonObject> = apiService.getdata()
        call.enqueue(object : retrofit2.Callback<JsonObject?> {
            override fun onResponse(call: Call<JsonObject?>?, response: Response<JsonObject?>) {
                if (response!!.isSuccessful) {
                    val JsonObject: JsonObject = response.body()!!
                    listContact = JsonObject.contacts as ArrayList<Contact>
                    mutableLiveData.setValue(listContact)
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

    fun DeleteData(contactID : String) {
        val apiService: RestApiService = RetrofitInstance.instance
        val call: Call<Contact> = apiService.deleteContact(contactID)
        call.enqueue(object : retrofit2.Callback<Contact> {
            override fun onResponse(call: Call<Contact>, response: Response<Contact>) {
                if (response.isSuccessful) {
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

    fun CreateData(contactCreate: ContactCreate) {
        val apiService: RestApiService = RetrofitInstance.instance
        val call: Call<ContactCreate> = apiService.postContact(contactCreate)
        call.enqueue(object : Callback<ContactCreate> {
            override fun onResponse(call: Call<ContactCreate>, response: Response<ContactCreate>) {
                }
            override fun onFailure(call: Call<ContactCreate>, t: Throwable) {
                Log.d("thisxxx", t.message.toString())
            }
        })

        }



}
