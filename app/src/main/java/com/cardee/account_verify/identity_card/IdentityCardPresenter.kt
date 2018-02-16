package com.cardee.account_verify.identity_card

import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import com.cardee.CardeeApp
import com.cardee.data_source.Error
import com.cardee.domain.RxUseCase
import com.cardee.domain.profile.usecase.UploadIdentityFrontPhoto
import io.reactivex.disposables.Disposable
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class IdentityCardPresenter(var view: IdentityCardView?) {

    private val uploadFrontUseCase = UploadIdentityFrontPhoto()
    private var frontDisposable: Disposable? = null

    companion object {
        const val MAX_IMAGE_DIMENSION = 720
    }

    fun setFrontImage(frontUri: Uri) {
        var bitmap = MediaStore.Images.Media.getBitmap(CardeeApp.context.contentResolver, frontUri)
        var width = bitmap.width
        if (width > MAX_IMAGE_DIMENSION) {
            val ratio = MAX_IMAGE_DIMENSION.toFloat() / width.toFloat()

            width = MAX_IMAGE_DIMENSION
            val height = (bitmap.height.toFloat() * ratio).toInt()
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height)
        }

        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream)
        val pictureByteArray = stream.toByteArray()
        val frontPhoto = convertByteArrayToFile(pictureByteArray)

        if (frontDisposable?.isDisposed == false) {
            frontDisposable?.dispose()
        }

        frontDisposable = uploadFrontUseCase.execute(UploadIdentityFrontPhoto.RequestValues(frontPhoto
                ?: return), object : RxUseCase.Callback<UploadIdentityFrontPhoto.ResponseValues> {
            override fun onSuccess(response: UploadIdentityFrontPhoto.ResponseValues) {
                view?.setFrontPhoto(pictureByteArray)
            }

            override fun onError(error: Error) {
                view?.showMessage(error.message)
            }
        })
    }

    private fun convertByteArrayToFile(byteArray: ByteArray): File? {
        var f: File? = null
        try {
            f = File(CardeeApp.context.cacheDir, "identity_photo")
            val fos = FileOutputStream(f)
            fos.write(byteArray)
            fos.flush()
            fos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return f
    }

    fun onDestroy() {
        view = null
    }
}
