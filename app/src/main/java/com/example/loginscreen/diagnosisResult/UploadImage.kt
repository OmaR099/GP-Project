package com.example.loginscreen.diagnosisResult

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.loginscreen.ml.DiseaseDetection
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder

class UploadImage: AppCompatActivity() {

    private val GALLERY_REQUEST_CODE = 123
    private var conf = ""
    private lateinit var byteArray: ByteArray
    private var modelResult = ""
    private var imageSize = 224

    companion object {
        val IMAGE_REQUEST_CODE = 100;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    pickImageFromGallery()
    }

    private fun onResultReceived(requestCode: Int, result: ActivityResult?) {
        when (requestCode) {
            GALLERY_REQUEST_CODE -> {
                if (result?.resultCode == Activity.RESULT_OK) {
                    result.data?.data?.let { uri ->
                        Log.i("TAG", "onResultReceived: $uri")
                        val bitmap =
                            BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
                        classifyImage(bitmap)

                        val byteArrayOutputStream = ByteArrayOutputStream()
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
                        byteArray = byteArrayOutputStream.toByteArray()
                        intent = Intent(this, Result::class.java)
                        intent.putExtra("confidences", conf)
                        intent.putExtra("result", modelResult)
                        intent.putExtra("image", byteArray)
                        startActivity(intent)
                    }
                } else {
                    Log.e("TAG", "onActivityResult: error in image")
                }
            }
        }

    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {
            val bitmap = uriToBitmap(data?.data!!)
            if (bitmap != null) {
                classifyImage(bitmap)
                val byteArrayOutputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
                byteArray = byteArrayOutputStream.toByteArray()
            }
        }
        intent = Intent(this, Result::class.java)
        intent.putExtra("confidences", conf)
        intent.putExtra("result", modelResult)
        intent.putExtra("image", byteArray)
        startActivity(intent)
    }

    fun uriToBitmap(selectedImage: Uri): Bitmap? {
        return try {
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImage)
            bitmap
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    private fun classifyImage(image: Bitmap) {
        try {
            val model = DiseaseDetection.newInstance(applicationContext)

            // Creates inputs for reference.
            val inputFeature0 =
                TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
            val byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3)
            byteBuffer.order(ByteOrder.nativeOrder())
            val intValues = IntArray(imageSize * imageSize)
            image.getPixels(intValues, 0, image.width, 0, 0, image.width, image.height)
            var pixel = 0
            for (i in 0 until imageSize) {
                for (j in 0 until imageSize) {
                    val `val` = intValues[pixel++]
                    byteBuffer.putFloat((`val` shr 16 and 0xFF) * (1f / 255f))
                    byteBuffer.putFloat((`val` shr 8 and 0xFF) * (1f / 255f))
                    byteBuffer.putFloat((`val` and 0xFF) * (1f / 255f))
                }
            }
            inputFeature0.loadBuffer(byteBuffer)

            // Runs model inference and gets result.
            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer
            val confidences = outputFeature0.floatArray
            var maxPos = 0
            var maxConfidence = 0f
            for (i in confidences.indices) {
                if (confidences[i] > maxConfidence) {
                    maxConfidence = confidences[i]
                    maxPos = i
                }
            }
            val classes = arrayOf(
                "Pepper bell Bacterial spot",
                "Pepper bell healthy",
                "Potato Early blight",
                "Potato healthy",
                "Potato Late blight",
                "Tomato Target Spot",
                "Tomato Tomato mosaic virus",
                "Tomato Tomato YellowLeaf Curl_Virus",
                "Tomato healthy"
            )
            modelResult = classes[maxPos]
            var s = ""
            for (i in classes.indices) {
                s += String.format("%s: %.1f%%\n", classes[i], confidences[i] * 100)
            }
            conf = s

            // Releases model resources if no longer used.
            model.close()
        } catch (e: IOException) {
            // TODO Handle the exception
        }
    }
}