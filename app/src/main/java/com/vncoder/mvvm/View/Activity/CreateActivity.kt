package com.vncoder.mvvm.View.Activity

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.vncoder.mvvm.R
import com.vncoder.mvvm.Repository.ContactRepository
import com.vncoder.mvvm.ViewModel.MainViewModel
import com.vncoder.retrofit2_employee.Model.ContactCreate
import com.vncoder.retrofit2_employee.Model.PostContact
import com.vncoder.retrofit2_employee.Model.custom
import kotlinx.android.synthetic.main.activity_create.*
import java.util.regex.Pattern


class CreateActivity : AppCompatActivity() {
    private val mainViewModel : MainViewModel by lazy {
        ViewModelProvider(
            this,
            MainViewModel.NoteViewModelFactory(this.application)
        )[MainViewModel::class.java]
    }

    val KITKAT_VALUE = 1002
    private var REQUEST_SELECT_IMAGE =1
    private var uriImage :String? = null
    val EMAIL_ADDRESS: Pattern = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" +
                "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        btn_avatar.setOnClickListener {
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

        edt_FirstName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
        edt_LastName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
        edt_Email.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

        btn_create.setOnClickListener {
            if (edt_FirstName.text.toString().trim().isEmpty()) {
                edt_FirstName.error = "FirstName not null"
            } else if (edt_LastName.text.toString().trim().isEmpty()) {
                edt_LastName.error = "LastName not null"
            } else if (edt_Email.text.toString().trim().isEmpty()
                || !EMAIL_ADDRESS.matcher(edt_Email.text.toString()).matches()
            ) {
                edt_Email.error = "Email invalid"
            } else {

                var custom: custom = custom()
                custom.string_Test_Field = uriImage.toString()

                var ContactCreate: ContactCreate = ContactCreate()
                val postContact: PostContact = PostContact()
                postContact.FirstName = edt_FirstName.text.toString().trim()
                postContact.LastName = edt_LastName.text.toString().trim()
                postContact.Email = edt_Email.text.toString().trim()
                postContact.custom = custom
                ContactCreate.PostContact = postContact

                mainViewModel.insertData(ContactCreate)
                finish()
            }
        }

        btn_cancel.setOnClickListener {
            finish()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == KITKAT_VALUE ) {
            if (resultCode == Activity.RESULT_OK) {
                // do something here
                uriImage =data?.data.toString()
                btn_avatar.setImageURI(Uri.parse(uriImage))
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
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


}