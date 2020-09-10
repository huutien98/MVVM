package com.vncoder.mvvm.ViewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vncoder.mvvm.Repository.ContactRepository
import com.vncoder.retrofit2_employee.Model.Contact
import com.vncoder.retrofit2_employee.Model.ContactCreate

class InfoViewModel(application: Application):ViewModel()  {
    private val contactRepository: ContactRepository = ContactRepository(application)

    fun insertData(contactCreate: ContactCreate): MutableLiveData<List<Contact>> = contactRepository.CreateData(contactCreate)

    class InfoViewModelFactory(private val application: Application) : ViewModelProvider.Factory{

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(InfoViewModel::class.java)){
                return InfoViewModel(application) as T
            }
            throw IllegalAccessException("Unable constructor ViewModel")
        }

    }
}