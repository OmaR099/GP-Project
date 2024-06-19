package com.example.loginscreen.moreService

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.loginscreen.R
import com.example.loginscreen.chatBot.ChatBot
import com.example.loginscreen.databinding.PestsIdentificationBinding
import com.example.loginscreen.home.AskExpertFragment
import com.example.loginscreen.ml.PestsQuant
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.IOException

class PestsIde: AppCompatActivity() {
    private lateinit var binding: PestsIdentificationBinding
    private var imageSize = 224
    private val GALLERY_REQUEST_CODE = 123
    private lateinit var bitmap: Bitmap

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

        binding.imgPests.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES)
                == PackageManager.PERMISSION_GRANTED
            ) {
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                intent.type = "image/*"
                val mimeTypes = arrayOf("image/jpeg", "image/png", "image/jpg")
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
                intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                onresult.launch(intent)
            } else {
                requestPermission.launch(Manifest.permission.READ_MEDIA_IMAGES)
            }
        }

        // download image with long press on it
        binding.imgPests.setOnLongClickListener {
            requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            return@setOnLongClickListener true
        }

//        Callbacks
        close()
        chatBot()
        expert()


    }

    //request camera permission
    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                takePicturePreview.launch(null)
            } else {
                Toast.makeText(this, "Permission Denied !! Try again", Toast.LENGTH_LONG).show()
            }
        }
    // launch camera
    private val takePicturePreview =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
            if (bitmap != null) {
                binding.imgPests.setImageBitmap(bitmap)
                outputGenerator(bitmap)
            }
        }

    // to get image from gallery
    private val onresult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            Log.i("TAG", "This is the result: ${result.data} ${result.resultCode}")
            onResultReceived(GALLERY_REQUEST_CODE, result)
        }


    private fun onResultReceived(requestCode: Int, result: ActivityResult?) {
        when (requestCode) {
            GALLERY_REQUEST_CODE -> {
                if (result?.resultCode == Activity.RESULT_OK) {
                    result.data?.data?.let { uri ->
                        Log.i("TAG", "onResultReceived: $uri")
                        bitmap =
                            BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
                        binding.imgPests.setImageBitmap(bitmap)
                        outputGenerator(bitmap)
                    }
                } else {
                    Log.e("TAG", "onActivityResult: error in image")
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun outputGenerator(bitmap: Bitmap) {

        val imageProcessor = ImageProcessor.Builder()
            .add(ResizeOp(224, 224, ResizeOp.ResizeMethod.NEAREST_NEIGHBOR))
            .build()


        val labels = application.assets.open("pests_quant.txt").bufferedReader().readLines()

        var tensorImage = TensorImage(DataType.UINT8)
        tensorImage.load(bitmap)

        tensorImage = imageProcessor.process(tensorImage)

        val model = PestsQuant.newInstance(this)

// Creates inputs for reference.
        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.UINT8)
        inputFeature0.loadBuffer(tensorImage.buffer)

// Runs model inference and gets result.
        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer.floatArray

        var maxIdx = 0
        outputFeature0.forEachIndexed { index, fl ->
            if (outputFeature0[maxIdx] < fl){
                maxIdx = index
            }
        }

        binding.tvOutput.text = labels[maxIdx]
        when(labels[maxIdx]){

            "Catterpillar" -> {binding.infoPests.text = "Caterpillars are the larval stage of butterflies and moths. They can be very destructive to plants, as they eat large quantities of leaves."
                binding.harmfulUseful.text = "Harmful Pest"
                binding.note.text = ""
                binding.img1.setImageResource(R.drawable.catterpillar1)
                binding.img2.setImageResource(R.drawable.catterpilla2)
                binding.img3.setImageResource(R.drawable.catterpilla3)}

            "Earthworms" -> {binding.infoPests.text = "Earthworms improve soil aeration and drainage by burrowing through the soil. Their castings (poop) also add nutrients to the soil."
                binding.harmfulUseful.text = "Useful insects"
                binding.note.text = "It's important to note that even beneficial insects can become pests if their populations get out of control.  The key is to maintain a balance in your garden."
                binding.img1.setImageResource(R.drawable.earthworms1)
                binding.img2.setImageResource(R.drawable.earthworms2)
                binding.img3.setImageResource(R.drawable.earthworms3)}

            "Earwig" -> {binding.infoPests.text = "Earwigs can damage leaves and flowers, but they are also beneficial predators of aphids and other small insects. Whether the benefit outweighs the harm depends on the severity of the earwig infestation.\n"
                binding.harmfulUseful.text = "Harmful Pest"
                binding.note.text = ""
                binding.img1.setImageResource(R.drawable.earwig1)
                binding.img2.setImageResource(R.drawable.earwig2)
                binding.img3.setImageResource(R.drawable.earwig3)}

            "Grasshopper" -> {binding.infoPests.text = "Grasshoppers are chewing insects that can eat large quantities of leaves. They can be a serious pest in gardens and fields."
                binding.harmfulUseful.text = "Harmful Pest"
                binding.note.text = ""
                binding.img1.setImageResource(R.drawable.grasshopper1)
                binding.img2.setImageResource(R.drawable.grasshopper2)
                binding.img3.setImageResource(R.drawable.grasshopper3)}

            "Slug" -> {binding.infoPests.text = "Slugs and snails are both gastropods that can damage leaves and flowers. They are most active in moist conditions."
                binding.harmfulUseful.text = "Harmful Pest"
                binding.note.text = ""
                binding.img1.setImageResource(R.drawable.slug1)
                binding.img2.setImageResource(R.drawable.slug2)
                binding.img3.setImageResource(R.drawable.slug3)}

        }

// Releases model resources if no longer used.
        model.close()

    }
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            var image = data!!.extras!!["data"] as Bitmap?
            val dimension = Math.min(image!!.width, image.height)
            image = ThumbnailUtils.extractThumbnail(image, dimension, dimension)
            image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false)
            binding.imgPests.setImageBitmap(image)
            outputGenerator(image)
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun close() {
        val closeBtn: ImageView = findViewById(R.id.close_btn_pests)
        closeBtn.setOnClickListener { finish() }
    }

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){
            isGranted: Boolean ->
        if (isGranted){
            AlertDialog.Builder(this).setTitle("Download Image?")
                .setMessage("Do you want to download this image to your device?")
                .setPositiveButton("Yes"){_, _ ->
                    val drawable: BitmapDrawable = binding.imgPests.drawable as BitmapDrawable
                    val bitmap = drawable.bitmap
                    downloadImage(bitmap)
                }
                .setNegativeButton("No"){dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }else{
            Toast.makeText(this, "Please allow permission to download image", Toast.LENGTH_LONG).show()
        }
    }

    private fun downloadImage(mBitmap: Bitmap): Uri? {
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "Birds_Images"+ System.currentTimeMillis()/1000)
            put(MediaStore.Images.Media.MIME_TYPE, "image/png")
        }
        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        if (uri != null){
            contentResolver.insert(uri, contentValues)?.also {
                contentResolver.openOutputStream(it).use { outputStream ->
                    if (!mBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream!!)){
                        throw IOException("Couldn't Save the bitmap")
                    }
                    else{
                        Toast.makeText(applicationContext, "Image Saved", Toast.LENGTH_SHORT).show()
                    }
                }
                return it
            }
        }
        return null
    }

    private fun chatBot() {
        binding.askChatBotTv.setOnClickListener {
            startActivity(Intent(this, ChatBot::class.java)) }
    }

    private fun expert() {
        binding.askExpertsTv.setOnClickListener {
            startActivity(Intent(this, AskExpertFragment::class.java)) }
    }

}