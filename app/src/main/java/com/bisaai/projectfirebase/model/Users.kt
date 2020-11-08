package com.bisaai.projectfirebase.model

import android.os.Parcelable
import com.google.firebase.database.Exclude
import com.google.firebase.database.PropertyName
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class Users(
    var alamat: String = "",
    val kelas: String = "",
    val key: String = "",
    val nama: String = "",
    var nisn: String = ""
): Parcelable
