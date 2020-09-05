package com.vncoder.retrofit2_employee.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class JsonObject :Serializable{
    @SerializedName("contacts")
    @Expose
    var contacts : List<Contact>? = null

    @SerializedName("total_contacts")
    @Expose
    var total_contacts : String? = null

}