package com.vncoder.retrofit2_employee.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class custom :Serializable {
    @SerializedName("string--Test--Field")
    @Expose
    var string_Test_Field : String = ""
}