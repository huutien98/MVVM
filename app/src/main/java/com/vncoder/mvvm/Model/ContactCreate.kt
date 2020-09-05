package com.vncoder.retrofit2_employee.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ContactCreate :Serializable {
    @SerializedName("contact")
    @Expose
    var PostContact :PostContact = PostContact()

}