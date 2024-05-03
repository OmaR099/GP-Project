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
import com.example.loginscreen.databinding.FlowersIdentificationBinding
import com.example.loginscreen.databinding.PestsIdentificationBinding
import com.example.loginscreen.databinding.ToxicPlantsIdentificationBinding
import com.example.loginscreen.ml.Flowers
import com.example.loginscreen.ml.PestsWithBackground
import com.example.loginscreen.ml.ToxicPlants
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder

class FlowersIdentification: AppCompatActivity() {
    private lateinit var binding: FlowersIdentificationBinding
    private var imageSize = 224
    private var conf = ""
    private var modelResult = ""

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

//        Callbacks
        close()


    }


    @SuppressLint("SetTextI18n")
    private fun classifyImage(image: Bitmap) {
        try {
            val model = Flowers.newInstance(applicationContext)

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
                "Daisy",
                "Dandelion",
                "Rose",
                "Sunflower",
                "Tulip",
                "Image is empty"
            )
            modelResult = classes[maxPos]
            binding.tvOutput.text = classes[maxPos]
            var s = ""
            for (i in classes.indices) {
                s += String.format("%s: %.1f%%\n", classes[i], confidences[i] * 100)
            }
            conf = s

            when(classes[maxPos]){
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
        val closeBtn: ImageView = findViewById(R.id.close_btn_flowers)
        closeBtn.setOnClickListener { finish() }
    }

}