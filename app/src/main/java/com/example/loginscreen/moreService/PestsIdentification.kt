package com.example.loginscreen.moreService

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.loginscreen.R
import com.example.loginscreen.databinding.PestsIdentificationBinding
import com.example.loginscreen.ml.PestsWithBackground
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder

class PestsIdentification: AppCompatActivity() {
    private lateinit var binding: PestsIdentificationBinding
    private var imageSize = 224
    private var conf = ""
    private var modelResult = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PestsIdentificationBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //        Start Camera
        binding.btnCaptureImage.setOnClickListener(View.OnClickListener {
            // Launch camera if we have permission
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, 1)
            } else {
                //Request camera permission if we don't have it.
                requestPermissions(arrayOf(Manifest.permission.CAMERA), 100)
            }
        })

        binding.tvOutput.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=${binding.tvOutput.text} insects With Plants"))
            startActivity(intent)
        }

//        Callbacks
        close()


    }


    @SuppressLint("SetTextI18n")
    private fun classifyImage(image: Bitmap) {
        try {
            val model = PestsWithBackground.newInstance(applicationContext)

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
                "Ants",
                "Bees",
                "Beetle",
                "Catterpillar",
                "Earthworms",
                "Earwig",
                "Grasshopper",
                "Moth",
                "Slug",
                "Snail",
                "Wasp",
                "Weevil",
                "No Insects or Pests"
            )
            modelResult = classes[maxPos]
            binding.tvOutput.text = classes[maxPos]
            var s = ""
            for (i in classes.indices) {
                s += String.format("%s: %.1f%%\n", classes[i], confidences[i] * 100)
            }
            conf = s

            when(classes[maxPos]){
                "Ants" -> {binding.infoPests.text = "While some species of ants can be beneficial to plants by aerating the soil and controlling other pest populations, most ants are attracted to the sweet honeydew produced by aphids and other sap-sucking insects. They will protect these pests from predators in exchange for the honeydew, which can lead to an increase in pest problems."
                    binding.harmfulUseful.text = "Harmful Pest"
                    binding.note.text = ""}

                "Bees" -> {binding.infoPests.text = "Bees are important pollinators that help plants reproduce by transferring pollen between flowers."
                    binding.harmfulUseful.text = "Useful insects"
                    binding.note.text = "It's important to note that even beneficial insects can become pests if their populations get out of control.  The key is to maintain a balance in your garden."}

                "Beetle" -> {binding.infoPests.text = "There are many different species of beetles, and some can be destructive to plants. For example, Japanese beetles and cucumber beetles feed on the leaves and flowers of many plants."
                    binding.harmfulUseful.text = "Harmful Pest"
                    binding.note.text = ""}

                "Catterpillar" -> {binding.infoPests.text = "Caterpillars are the larval stage of butterflies and moths. They can be very destructive to plants, as they eat large quantities of leaves."
                    binding.harmfulUseful.text = "Harmful Pest"
                    binding.note.text = ""}

                "Earthworms" -> {binding.infoPests.text = "Earthworms improve soil aeration and drainage by burrowing through the soil. Their castings (poop) also add nutrients to the soil."
                    binding.harmfulUseful.text = "Useful insects"
                    binding.note.text = "It's important to note that even beneficial insects can become pests if their populations get out of control.  The key is to maintain a balance in your garden."}

                "Earwig" -> {binding.infoPests.text = "Earwigs can damage leaves and flowers, but they are also beneficial predators of aphids and other small insects. Whether the benefit outweighs the harm depends on the severity of the earwig infestation.\n"
                    binding.harmfulUseful.text = "Harmful Pest"
                    binding.note.text = ""}

                "Grasshopper" -> {binding.infoPests.text = "Grasshoppers are chewing insects that can eat large quantities of leaves. They can be a serious pest in gardens and fields."
                    binding.harmfulUseful.text = "Harmful Pest"
                    binding.note.text = ""}

                "Moth" -> {binding.infoPests.text = "Like caterpillars, moths can be destructive to plants, as they also eat leaves. Some moth species, like the codling moth, also damage fruits."
                    binding.harmfulUseful.text = "Harmful Pest"
                    binding.note.text = ""}

                "Slug" -> {binding.infoPests.text = "Slugs and snails are both gastropods that can damage leaves and flowers. They are most active in moist conditions."
                    binding.harmfulUseful.text = "Harmful Pest"
                    binding.note.text = ""}

                "Snail" -> {binding.infoPests.text = "Snails are similar to slugs, but they have a shell that they can retreat into. They can be a nuisance in gardens, but they are not usually as destructive as slugs."
                    binding.harmfulUseful.text = "Harmful Pest"
                    binding.note.text = ""}

                "Wasp" -> {binding.infoPests.text = "Some wasps can be beneficial predators of garden pests, but others can damage plants. For example, wasps in the genus Cynips form galls on oak trees. Galls are growths on plants caused by the feeding activity of insects or mites."
                    binding.harmfulUseful.text = "Harmful Pest"
                    binding.note.text = ""}

                "Weevil" -> {binding.infoPests.text = "Weevils are beetles with a long snout. Many species of weevils are pests of agricultural crops. For example, the boll weevil is a major pest of cotton."
                    binding.harmfulUseful.text = "Harmful Pest"
                    binding.note.text = ""}

                "No Insects or Pests" -> {binding.infoPests.text = "No Insects or Pests found in the image please capture the pest or insect again"
                    binding.harmfulUseful.text = "N/A"
                    binding.note.text = ""}


            }

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
            binding.imgPests.setImageBitmap(image)
            classifyImage(image)
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun close() {
        val closeBtn: ImageView = findViewById(R.id.close_btn_pests)
        closeBtn.setOnClickListener { finish() }
    }

}