package com.vncoder.mvvm.View.Activity

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.vncoder.mvvm.R
import com.vncoder.mvvm.ViewModel.MainViewModel
import com.vncoder.retrofit2_employee.Model.Contact
import com.vncoder.retrofit2_employee.Model.ContactCreate
import com.vncoder.retrofit2_employee.Model.PostContact
import com.vncoder.retrofit2_employee.Model.custom
import kotlinx.android.synthetic.main.activity_info.*

class infoActivity : AppCompatActivity() {
    private val mainViewModel : MainViewModel by lazy {
        ViewModelProvider(
            this,
            MainViewModel.NoteViewModelFactory(this.application)
        )[MainViewModel::class.java]
    }


    private var REQUEST_SELECT_IMAGE = 200
    val KITKAT_VALUE = 1002
    var contact: Contact = Contact()
    var imageUri: String? = null
    var ContactCreate: ContactCreate = ContactCreate()
    var postContact: PostContact = PostContact()
    var custom: custom = custom()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        val bundle = intent.extras
        contact = bundle?.getSerializable("detailEmployee")!! as Contact

        detail_btn_avatar.setOnClickListener {
            val intent: Intent

            if (Build.VERSION.SDK_INT < 19) {
                intent = Intent()
                intent.action = Intent.ACTION_GET_CONTENT
                intent.type = "image/*"
                startActivityForResult(intent, KITKAT_VALUE)
            } else {
                intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
                intent.addCategory(Intent.CATEGORY_OPENABLE)
                intent.type = "image/*"
                startActivityForResult(intent, KITKAT_VALUE)
            }
        }

        detail_FirstName.setText(contact.FirstName.toString())
        detail_LastName.setText(contact.LastName.toString())
        detail_Email.text = contact.Email
        detail_btn_avatar.setImageURI(Uri.parse(contact.custom_fields?.get(0)?.value.toString()))
        detail_contact_id.text = contact.contact_id.toString()



            detail_update.setOnClickListener {
                custom.string_Test_Field = imageUri.toString()
                postContact.FirstName = detail_FirstName.text.toString()
                postContact.LastName = detail_LastName.text.toString()
                postContact.Email = detail_Email.text.toString()
                postContact.custom = custom
                ContactCreate.PostContact = postContact
                mainViewModel?.insertData(ContactCreate)
                finish()
            }



        detail_cancell.setOnClickListener {
            finish()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == KITKAT_VALUE ) {
            if (resultCode == Activity.RESULT_OK) {
                // do something here
                imageUri = data?.data.toString()
                detail_btn_avatar.setImageURI(Uri.parse(imageUri))
            }
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ){
        if (requestCode == REQUEST_SELECT_IMAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
                intent.addCategory(Intent.CATEGORY_OPENABLE)
                intent.type = "*/*"
                startActivityForResult(intent, REQUEST_SELECT_IMAGE)
            } else {
                // Permission Denied
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}