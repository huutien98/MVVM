package com.vncoder.mvvm.ViewModel

import android.app.Application
import androidx.lifecycle.*
import com.vncoder.mvvm.Repository.ContactRepository
import com.vncoder.retrofit2_employee.Model.Contact
import com.vncoder.retrofit2_employee.Model.ContactCreate


class MainViewModel(application: Application) : ViewModel(){
    private val contactRepository: ContactRepository = ContactRepository(application)

    fun getData(): MutableLiveData<List<Contact>> = contactRepository.getMutableLiveData()

    fun delete(ContactID :String): Unit  =  contactRepository.DeleteData(ContactID)
    fun insertData(contactCreate: ContactCreate): Unit = contactRepository.CreateData(contactCreate)

    class NoteViewModelFactory(private val application: Application) :ViewModelProvider.Factory{

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)){
                return MainViewModel(application) as T
            }
            throw IllegalAccessException("Unable constructor ViewModel")
        }

    }

}