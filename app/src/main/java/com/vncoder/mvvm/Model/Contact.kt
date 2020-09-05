package com.vncoder.retrofit2_employee.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Contact : Serializable {
    @SerializedName("Email")
    @Expose
    var Email: String? = null

    @SerializedName("created_at")
    @Expose
    var created_at: String? = null

    @SerializedName("updated_at")
    @Expose
    var updated_at: String? = null

    @SerializedName("api_originated")
    @Expose
    var api_originated: Boolean? = false

    @SerializedName("custom_fields")
    @Expose
    var custom_fields: List<custom_fields?>? = null

    @SerializedName("Name")
    @Expose
    var Name: String? = null

    @SerializedName("LastName")
    @Expose
    var LastName: String? = null

    @SerializedName("FirstName")
    @Expose
    var FirstName: String? = null

    @SerializedName("contact_id")
    @Expose
    var contact_id: String? = null
}