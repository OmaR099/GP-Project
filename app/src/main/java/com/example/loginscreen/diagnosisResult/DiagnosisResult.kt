package com.example.loginscreen.diagnosisResult

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.ThumbnailUtils
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.loginscreen.R
import com.example.loginscreen.databinding.FragmentDiagnoseBinding
import com.example.loginscreen.home.SnapTips
import com.example.loginscreen.ml.Classes23
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder


class DiagnosisResult : AppCompatActivity() {
    private lateinit var binding: FragmentDiagnoseBinding
    private var conf = ""
    private lateinit var byteArray: ByteArray
    private var modelResult = ""
    private var imageSize = 224

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentDiagnoseBinding.inflate(layoutInflater)
        setContentView(binding.root)

//      callbacks
        openSnapTips()
        leaves()

        binding.uploadView.setOnClickListener { startActivity(Intent(this, UploadImage::class.java)) }

//        firebase

//        Start Camera
        binding.autoDiagnoseView.setOnClickListener(View.OnClickListener {
            // Launch camera if we have permission
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, 1)
            } else {
                //Request camera permission if we don't have it.
                requestPermissions(arrayOf(Manifest.permission.CAMERA), 100)
            }
        })

//        binding.confidencesText.setOnClickListener {
//            intent = Intent(this, Result::class.java)
//            intent.putExtra("confidences", conf)
//            intent.putExtra("result", modelResult)
//            intent.putExtra("image", byteArray)
//            startActivity(intent)
//        }
    }

    private fun classifyImage(image: Bitmap) {
        try {
            val model = Classes23.newInstance(applicationContext)

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
                "Apple scab",
                "Apple Black rot",
                "Apple healthy",
                "Background without leaves",
                "Cherry healthy",
                "Cherry Powdery mildew",
                "Corn Cercospora leaf spot Gray leaf spot",
                "Corn Common rust",
                "Corn healthy",
                "Grape Black rot",
                "Grape healthy",
                "Peach Bacterial spot",
                "Peach healthy",
                "Pepper bell Bacterial spot",
                "Pepper bell healthy",
                "Potato Early blight",
                "Potato healthy",
                "Potato Late blight",
                "Strawberry healthy",
                "Strawberry Leaf scorch",
                "Tomato Bacterial spot",
                "Tomato Early blight",
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

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            var image = data!!.extras!!["data"] as Bitmap?
            val dimension = Math.min(image!!.width, image.height)
            image = ThumbnailUtils.extractThumbnail(image, dimension, dimension)
            image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false)
            classifyImage(image)

            val byteArrayOutputStream = ByteArrayOutputStream()
            image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
            byteArray = byteArrayOutputStream.toByteArray()
        }

        intent = Intent(this, Result::class.java)
        intent.putExtra("confidences", conf)
        intent.putExtra("result", modelResult)
        intent.putExtra("image", byteArray)
        startActivity(intent)
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun openSnapTips() {

        binding.tipsBtn.setOnClickListener {
            val snapTipsFragment = SnapTips()
            supportFragmentManager.beginTransaction()
                .replace(R.id.snapTips_container, snapTipsFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    private fun leaves(){binding.leavesCv.setOnClickListener {
        startActivity(Intent(this, LeavesCategory::class.java)) }}

}