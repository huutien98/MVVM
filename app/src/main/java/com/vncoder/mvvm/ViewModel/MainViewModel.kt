package com.vncoder.mvvm.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.vncoder.mvvm.Repository.ContactRepository
import com.vncoder.retrofit2_employee.Model.Contact
import com.vncoder.retrofit2_employee.Model.ContactCreate

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val contactRepository: ContactRepository = ContactRepository(application)
    val allUsers: MutableLiveData<List<Contact>>
        get() = contactRepository.getMutableLiveData()

    val deleteUser: Unit
        get() = contactRepository.getDeleteLiveData()

    val createUser: MutableLiveData<ContactCreate>
        get() = contactRepository.CreateLiveData()
}