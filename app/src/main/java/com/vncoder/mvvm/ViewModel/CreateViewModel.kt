package com.vncoder.mvvm.ViewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vncoder.mvvm.Repository.ContactRepository
import com.vncoder.retrofit2_employee.Model.Contact
import com.vncoder.retrofit2_employee.Model.ContactCreate

class CreateViewModel(application: Application):ViewModel() {
    private val contactRepository: ContactRepository = ContactRepository(application)

    fun insertData(contactCreate: ContactCreate): MutableLiveData<List<Contact>> = contactRepository.CreateData(contactCreate)

    class CreateViewModelFactory(private val application: Application) : ViewModelProvider.Factory{

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CreateViewModel::class.java)){
                return CreateViewModel(application) as T
            }
            throw IllegalAccessException("Unable constructor ViewModel")
        }

    }

}