package com.vncoder.retrofit2_employee.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class PostContact : Serializable {
    @SerializedName("FirstName")
    @Expose
    var FirstName: String? = null

    @SerializedName("LastName")
    @Expose
    var LastName: String? = null

    @SerializedName("Email")
    @Expose
    var Email: String? = null

    @SerializedName("custom")
    @Expose
    var custom: custom? = null

    @SerializedName("contact_id")
    @Expose
    var contact_id: String? = null

}




