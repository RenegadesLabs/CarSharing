package com.cardee.data_source.remote.api.profile.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.File


data class UploadPhotoRequest(@Expose @SerializedName("identity_front") val identityFront: File? = null,
                              @Expose @SerializedName("identity_back") val identityBack: File? = null,
                              @Expose @SerializedName("licence_front") val licenceFront: File? = null,
                              @Expose @SerializedName("licence_back") val licenceBack: File? = null,
                              @Expose @SerializedName("face_photo") val facePhoto: File? = null)