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
import com.example.loginscreen.databinding.ToxicPlantsIdentificationBinding
import com.example.loginscreen.home.AskExpertFragment
import com.example.loginscreen.ml.FlowersQuantized
import com.example.loginscreen.ml.ToxicQuant
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.IOException

class ToxicIde: AppCompatActivity() {
    private lateinit var binding: ToxicPlantsIdentificationBinding
    private var imageSize = 224
    private var conf = ""
    private var modelResult = ""
    private val GALLERY_REQUEST_CODE = 123
    private lateinit var bitmap: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ToxicPlantsIdentificationBinding.inflate(layoutInflater)
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
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=${binding.tvOutput.text} ${binding.scientificName.text} Plant"))
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


        val labels = application.assets.open("toxics.txt").bufferedReader().readLines()

        var tensorImage = TensorImage(DataType.UINT8)
        tensorImage.load(bitmap)

        tensorImage = imageProcessor.process(tensorImage)

        val model = ToxicQuant.newInstance(this)

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
            "Bear Oak" -> {binding.infoPests.text = "The Bear Oak, or Scrub Oak (Quercus ilicifolia), is a tough and adaptable shrubby oak native to the eastern United States and parts of southeastern Canada. Despite its name, it's on the smaller side, typically reaching only 12-20 feet tall.\n\n• Size: Shrub or small tree, 1-8 meters tall.\n\n• Leaves: Simple, alternate, 2-4 inches long, with 3-7 shallow lobes and a hairy underside.\n\n• Habitat: Dry, rocky areas with well-drained soil, often on slopes or mountains."
                binding.harmfulUseful.text = "Mildly toxic"
                binding.usesTitle.text = "Ecological importance:"
                binding.usesDes.text = "• Provides food and shelter for wildlife like bears, deer, and turkeys.\n\n• Acorns, though bitter, are a valuable food source for animals, especially bears preparing for hibernation."
                binding.scientificName.text = "Quercus ilicifolia"
                binding.toxicityDes.text = "while not deadly, Bear Oak is considered mildly toxic.\n\n• The acorns contain tannins, which can cause stomach upset in humans and animals if consumed in large quantities.\n\n• The leaves and twigs have tiny hairs that can irritate the skin and eyes on contact."
                binding.img1.setImageResource(R.drawable.bearoak1)
                binding.img2.setImageResource(R.drawable.bearoak2)
                binding.img3.setImageResource(R.drawable.bearoak3)}

            "Fragrant Sumac" -> {binding.infoPests.text = "The Fragrant Sumac (Rhus aromatica) is a delightful, low-maintenance shrub native to North America. It's prized for its aromatic foliage, vibrant fall colors, and ability to thrive in various conditions.\n\n• Size: Deciduous shrub, reaching 6-12 feet tall with a sprawling habit.\n\n• Leaves: Trifoliate (three leaflets), glossy green with a blue tinge, turning orange, red, purple, and yellow in fall. Crushed leaves release a pleasant, lemony scent.\n\n• Flowers: Small, yellowish catkins appear in early spring before leaves.\n\n• Fruits: Red, hairy berries attractive to birds and persisting into winter."
                binding.harmfulUseful.text = "Not Toxic"
                binding.usesTitle.text = "Landscaping uses:"
                binding.usesDes.text = "• Excellent ground cover for slopes and banks due to its suckering habit.\n\n• Provides year-round interest with fragrant foliage, colorful berries, and stunning fall display.\n\n• Tolerates full sun to part shade and dry to moist soil conditions."
                binding.scientificName.text = "Rhus aromatica"
                binding.toxicityDes.text = "Unlike its close relative poison ivy, Fragrant Sumac is non-toxic. You can safely enjoy its fragrant leaves and attractive berries without any worries."
                binding.img1.setImageResource(R.drawable.fragrantsumac1)
                binding.img2.setImageResource(R.drawable.fragrantsumac2)
                binding.img3.setImageResource(R.drawable.fragrantsumac3)}

            "Virginia creeper" -> {binding.infoPests.text = "Virginia creeper is a fast-growing deciduous vine native to eastern North America and eastern Asia. It's a popular choice for covering walls, fences, and other structures due to its dense foliage and attractive fall color.\n\n• Size: Vigorous vine reaching up to 30-50 feet tall with clinging tendrils.\n\n• Leaves: Deciduous, palmately compound with 5 leaflets that turn vibrant shades of red, orange, and purple in fall.\n\n• Flowers: Inconspicuous greenish-yellow flowers in late spring, followed by small dark blue berries.\n\n• Habit: Climbing vine that attaches to surfaces using adhesive disks on its tendrils."
                binding.harmfulUseful.text = "Not Highly Toxic"
                binding.usesTitle.text = "Benefits:"
                binding.usesDes.text = "• Provides excellent erosion control on slopes and banks.\n\n• Creates a dense cover that shades buildings and helps cool them in summer.\n\n• Attracts birds that feed on the berries."
                binding.scientificName.text = "Parthenocissus quinquefolia"
                binding.toxicityDes.text = "Virginia creeper is not considered highly toxic, but it's not entirely harmless either.\n\n• Leaves and berries: Contain small calcium oxalate crystals that can irritate the mouth, throat, and stomach if ingested. This typically causes mild symptoms like nausea, vomiting, and diarrhea.\n\n• Sap: May irritate the skin for some people, causing an itchy rash."
                binding.img1.setImageResource(R.drawable.virginiacreeper1)
                binding.img2.setImageResource(R.drawable.virginiacreeper2)
                binding.img3.setImageResource(R.drawable.virginiacreeper3)}

            "Eastern Poison Ivy" -> {binding.infoPests.text = "Eastern Poison Ivy (Toxicodendron radicans) is a common and notorious plant native to most of North America. While it can take on various forms, it's best known for causing an itchy, blistering rash upon contact.\n\n• Appearance: Can be a groundcover, shrub, or climbing vine, with three leaflets per leaf (always!). Leaf edges can be smooth, serrated, or lobed, and leaflets vary in size and shape.\n\n• Habitat: Found in diverse habitats like forests, fields, roadsides, and even your own backyard.\n\n• Identification tip: Remember the saying, \"leaves of three, let it be!\"."
                binding.harmfulUseful.text = "Highly Toxic Plant"
                binding.usesTitle.text = "Benefits and drawbacks:"
                binding.usesDes.text = "• Wildlife food source: Produces berries that some birds can eat, and its leaves are browsed by deer in rare instances.\n\n• Erosion control: Dense root systems can help stabilize soil on slopes, though planting it for this purpose is not recommended due to its toxicity.\n\n• Highly Toxic: All parts of poison ivy contain urushiol, a potent oil that causes allergic dermatitis (rash) in most people. Contact with even a tiny amount can trigger an itchy, blistering rash that can take weeks to heal.\n\n• Spreads easily: Reproduces through seeds and underground runners, making eradication difficult."
                binding.scientificName.text = "Toxicodendron radicans"
                binding.toxicityDes.text = "Highly Toxic! All parts of poison ivy contain urushiol, a potent oil that causes allergic dermatitis (rash) in most people. Contact with even a tiny amount can trigger an itchy, blistering rash that can take weeks to heal."
                binding.img1.setImageResource(R.drawable.eastern_poison_ivy1)
                binding.img2.setImageResource(R.drawable.eastern_poison_ivy2)
                binding.img3.setImageResource(R.drawable.eastern_poison_ivy3)}

            "Poison Sumac" -> {binding.infoPests.text = "Poison Sumac (Toxicodendron vernix) stands out even among its toxic relatives, poison ivy and oak.  This wetland dweller packs a powerful punch and should be avoided at all costs.\n\n• Habitat: Thrives in swamps, bogs, and other wet areas, unlike its drier-loving relatives.\n\n• Identification: Tall shrub or small tree with smooth, alternate leaves consisting of 7-13 leaflets, giving it a feathery appearance. Unlike poison ivy and oak, it has no central stalk for the leaflets.\n\n• Warning sign: Look for smooth, grey branches with a distinct lack of hairs or prickles."
                binding.harmfulUseful.text = "Extremely Toxic Plant"
                binding.usesTitle.text = "Benefits and drawbacks:"
                binding.usesDes.text = "• Wildlife food source: The white berries provide sustenance for some birds in late summer and fall.\n\n• Highly Toxic: All parts of Poison Sumac contain urushiol, but it's present in much higher concentrations than in poison ivy or oak. Even minimal contact can cause a severe allergic reaction with painful blistering, swelling, and even respiratory issues from inhaling smoke from burning the plant.\n\n• Spreads easily: Reproduces through seeds and underground suckers, making eradication challenging."
                binding.scientificName.text = "Toxicodendron vernix"
                binding.toxicityDes.text = "Extremely Toxic.  Poison Sumac is considered the most toxic plant native to North America east of the Mississippi.  A single brush against its leaves, stems, or roots can trigger a severe allergic reaction.  The urushiol oil can also remain active on fur, clothing, and tools, posing a risk for delayed rashes."
                binding.img1.setImageResource(R.drawable.poison_sumac1)
                binding.img2.setImageResource(R.drawable.poison_sumac2)
                binding.img3.setImageResource(R.drawable.poison_sumac3)}


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
        val closeBtn: ImageView = findViewById(R.id.close_btn_toxic)
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