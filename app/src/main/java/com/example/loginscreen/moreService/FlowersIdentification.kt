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
import com.example.loginscreen.databinding.FlowersIdentificationBinding
import com.example.loginscreen.home.AskExpertFragment
import com.example.loginscreen.ml.FlowersQuantized
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.IOException

class FlowersIdentification: AppCompatActivity() {
    private lateinit var binding: FlowersIdentificationBinding
    private var imageSize = 224
    private var conf = ""
    private var modelResult = ""
    private val GALLERY_REQUEST_CODE = 123
    private lateinit var bitmap: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FlowersIdentificationBinding.inflate(layoutInflater)
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
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=${binding.tvOutput.text} ${binding.scientificName.text} Flower"))
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


        val labels = application.assets.open("flowers.txt").bufferedReader().readLines()

        var tensorImage = TensorImage(DataType.UINT8)
        tensorImage.load(bitmap)

        tensorImage = imageProcessor.process(tensorImage)

        val model = FlowersQuantized.newInstance(this)

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
            "Daisy" -> {binding.infoPests.text = "Bellis perennis the daisy, is a European species of the family Asteraceae, often considered the archetypal species of the name daisy. To distinguish this species from other plants known as daisies, it is sometimes qualified or known as common daisy, lawn daisy, or English daisy.\n\n• Distribution and Habitat: Native to Europe and western Asia, daisies have spread worldwide. They thrive in meadows, grasslands, and disturbed areas, often popping up in lawns.\n\n• Cultivation: Easy to grow from seeds or transplants, daisies prefer full sun and well-drained soil."
                binding.harmfulUseful.text = "A Sunny Disposition"
                binding.usesDes.text = "Daisies are a staple in gardens, adding charm to borders, beds, and containers. They also attract pollinators and are perfect for cut flower arrangements."
                binding.scientificName.text = "Bellis perennis"
                binding.toxicityDes.text = "Daisies prefer full sun and well-drained soil. They are drought-tolerant but appreciate occasional watering during dry spells. Deadheading (removing spent flowers) encourages continued blooming."
                binding.img1.setImageResource(R.drawable.daisy1)
                binding.img2.setImageResource(R.drawable.daisy2)
                binding.img3.setImageResource(R.drawable.daisy3)}

            "Dandelion" -> {binding.infoPests.text = "Taraxacum is a large genus of flowering plants in the family Asteraceae, which consists of species commonly known as dandelions. The scientific and hobby study of the genus is known as taraxacology. The genus is native to Eurasia and North America, but the two most commonplace species worldwide, T. officinale (the common dandelion) and T. erythrospermum (the red-seeded dandelion), were introduced from Europe into North America, where they now propagate as wildflowers.\n\n• Distribution and Habitat: Native to Eurasia, dandelions are now found worldwide in temperate regions. They thrive in disturbed areas, lawns, roadsides, and wastelands, tolerating poor soil conditions.\n\n• Cultivation: While most people try to eradicate them, dandelions are surprisingly easy to cultivate! They readily grow from seeds scattered by the wind, needing minimal care."
                binding.harmfulUseful.text = "More Than Just Weeds"
                binding.usesDes.text = "Dandelions are surprisingly versatile. The leaves are edible and can be added to salads, while the roots can be roasted for a coffee substitute. The flowers can be used to make wine or jelly."
                binding.scientificName.text = "Taraxacum officinale"
                binding.toxicityDes.text = "Dandelions require little to no care. They are drought-tolerant and don't need fertilizer. In fact, the best care you can give them is to leave them alone... unless you want them to take over your lawn!"
                binding.img1.setImageResource(R.drawable.dandelion1)
                binding.img2.setImageResource(R.drawable.dandelion2)
                binding.img3.setImageResource(R.drawable.dandelion3)}

            "Rose" -> {binding.infoPests.text = "A rose is either a woody perennial flowering plant of the genus Rosa in the family Rosaceae or the flower it bears. There are over three hundred species and tens of thousands of cultivars. They form a group of plants that can be erect shrubs, climbing, or trailing, with stems that are often armed with sharp prickles. Their flowers vary in size and shape and are usually large and showy, in colors ranging from white through yellows and reds. Most species are native to Asia, with smaller numbers native to Europe, North America, and northwestern Africa.\n\n• Distribution and Habitat: Most rose species originated in Asia, with some native to Europe, North America, and Africa. Wild roses grow in diverse habitats, from mountainsides to woodlands. Cultivated roses thrive in well-maintained gardens.\n\n• Cultivation: Roses come in a variety of shapes and sizes, from miniature bushes to sprawling climbers. They generally prefer full sun, well-drained soil, and good air circulation. Different varieties may have specific pruning requirements."
                binding.harmfulUseful.text = "A Timeless Beauty"
                binding.usesDes.text = "Roses are prized for their beauty. They are popular in gardens, used for cut flowers, and play a significant role in the perfume industry. Rose hips (the fruit) are also a source of vitamin C."
                binding.scientificName.text = "Rosa"
                binding.toxicityDes.text = "Care Guide: Roses require regular watering, especially during their first year. Deadheading (removing spent flowers) encourages continued bloom. Feeding with fertilizer formulated for roses can promote healthy growth. Be mindful of potential pests and diseases and take appropriate action if needed."
                binding.img1.setImageResource(R.drawable.rose1)
                binding.img2.setImageResource(R.drawable.rose2)
                binding.img3.setImageResource(R.drawable.rose3)}

            "Sunflower" -> {binding.infoPests.text = "The common sunflower (Helianthus annuus) is a species of large annual forb of the daisy family Asteraceae. The common sunflower is harvested for its edible oily seeds which are used in the production of cooking oil.\n\n• Distribution and Habitat: Native to North America, particularly the central United States and northern Mexico, sunflowers have spread worldwide. They thrive in open areas with plenty of sunlight, like prairies and plains.\n\n• Sunflowers are easy to grow from seeds. They prefer full sun, well-drained soil, and ample space to reach their full height."
                binding.harmfulUseful.text = "Reaching for the Sun"
                binding.usesDes.text = "Sunflowers are versatile. Their seeds are a popular snack and source of cooking oil. The stalks can be used for biofuel or construction. Sunflowers also attract pollinators and make stunning cut flowers."
                binding.scientificName.text = "Helianthus annuus"
                binding.toxicityDes.text = "Sunflowers are relatively low-maintenance. Water them regularly during dry periods, especially when young. Staking may be necessary for tall varieties to prevent them from toppling over. Deadheading encourages continued flowering, but leave some flower heads to develop seeds for birds to enjoy."
                binding.img1.setImageResource(R.drawable.sunflower1)
                binding.img2.setImageResource(R.drawable.sunflower2)
                binding.img3.setImageResource(R.drawable.sunflower3)}

            "Tulip" -> {binding.infoPests.text = "Tulips are spring-blooming perennial herbaceous bulbiferous geophytes in the Tulipa genus. Their flowers are usually large, showy, and brightly colored, generally red, orange, pink, yellow, or white. They often have a different colored blotch at the base of the tepals, internally. Because of a degree of variability within the populations and a long history of cultivation, classification has been complex and controversial. The tulip is a member of the lily family, Liliaceae, along with 14 other genera, where it is most closely related to Amana, Erythronium, and Gagea in the tribe Lilieae.\n\n• Distribution and Habitat: Wild tulips originated in Central Asia, particularly in areas around the Himalayas, Iran, and Afghanistan. Today, cultivated tulips flourish in temperate regions worldwide.\n\n• Cultivation: Tulips are typically planted in the fall as bulbs. They prefer cool winters and mild springs with full sun and well-drained soil. Different varieties have varying heights and bloom times."
                binding.harmfulUseful.text = "Springtime Showstoppers"
                binding.usesDes.text = "Tulips are primarily grown for their ornamental value. They are a mainstay in spring gardens, popular as cut flowers, and sought after for vibrant potted displays."
                binding.scientificName.text = "Tulipa"
                binding.toxicityDes.text = "Water tulip bulbs regularly in the fall and early spring until shoots emerge. Once established, they are moderately drought-tolerant. Deadheading spent flowers prevents seed formation and encourages the plant to focus on bulb development for the next season. After flowering, allow the foliage to die back naturally before removing it."
                binding.img1.setImageResource(R.drawable.tulip1)
                binding.img2.setImageResource(R.drawable.tulip2)
                binding.img3.setImageResource(R.drawable.tulip3)}

            "Image is empty" -> {binding.infoPests.text = "No flowers were found in the image please capture the image again"
                binding.harmfulUseful.text = "Retake the image Please !!"
                binding.usesTitle.text = ""
                binding.usesDes.text = ""
                binding.scientificName.text = ""
                binding.toxicityDes.text = ""}


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
        val closeBtn: ImageView = findViewById(R.id.close_btn_flowers)
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