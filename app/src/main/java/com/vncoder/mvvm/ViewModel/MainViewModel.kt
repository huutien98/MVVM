package com.vncoder.mvvm.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.vncoder.mvvm.Repository.ContactRepository
import com.vncoder.retrofit2_employee.Model.Contact


class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val userRepository: ContactRepository = ContactRepository(application)
    val allUsers: MutableLiveData<List<Contact>>
        get() = userRepository.getMutableLiveData()

}