package com.vncoder.mvvm.View.Activity

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.vncoder.mvvm.R
import com.vncoder.retrofit2_employee.Model.Contact
import kotlinx.android.synthetic.main.activity_info.*

class infoActivity : AppCompatActivity() {
    private var REQUEST_SELECT_IMAGE = 200
    val KITKAT_VALUE = 1002
    var contact: Contact = Contact()
    var imageUri: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        val bundle = intent.extras
        contact = bundle?.getSerializable("detailEmployee")!! as Contact

        detail_FirstName.setText(contact.FirstName.toString())
        detail_LastName.setText(contact.LastName.toString())
        detail_Email.text = contact.Email
        detail_btn_avatar.setImageURI(Uri.parse(contact.custom_fields?.get(0)?.value.toString()))
        detail_contact_id.text = contact.contact_id.toString()

        imageUri = contact.custom_fields?.get(0)?.value.toString()

        detail_update.setOnClickListener {

        }
        detail_cancell.setOnClickListener {
            finish()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == REQUEST_SELECT_IMAGE && resultCode == RESULT_OK) {
//            imageUri = data?.data.toString()
//            detail_btn_avatar.setImageURI(Uri.parse(imageUri))
//        }

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