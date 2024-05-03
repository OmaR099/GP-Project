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
import com.example.loginscreen.databinding.ToxicPlantsIdentificationBinding
import com.example.loginscreen.ml.PestsWithBackground
import com.example.loginscreen.ml.ToxicPlants
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder

class ToxicPlantsIdentification: AppCompatActivity() {
    private lateinit var binding: ToxicPlantsIdentificationBinding
    private var imageSize = 224
    private var conf = ""
    private var modelResult = ""

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

//        Callbacks
        close()


    }


    @SuppressLint("SetTextI18n")
    private fun classifyImage(image: Bitmap) {
        try {
            val model = ToxicPlants.newInstance(applicationContext)

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
                "Bear Oak",
                "Boxelder",
                "Fragrant Sumac",
                "Jack-in-the-pulpit",
                "Virginia creeper",
                "Eastern Poison Ivy",
                "Eastern Poison Oak",
                "Poison Sumac",
                "Western Poison Ivy",
                "Western Poison Oak",
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
                "Bear Oak" -> {binding.infoPests.text = "The Bear Oak, or Scrub Oak (Quercus ilicifolia), is a tough and adaptable shrubby oak native to the eastern United States and parts of southeastern Canada. Despite its name, it's on the smaller side, typically reaching only 12-20 feet tall.\n\n• Size: Shrub or small tree, 1-8 meters tall.\n\n• Leaves: Simple, alternate, 2-4 inches long, with 3-7 shallow lobes and a hairy underside.\n\n• Habitat: Dry, rocky areas with well-drained soil, often on slopes or mountains."
                    binding.harmfulUseful.text = "Mildly toxic"
                    binding.usesTitle.text = "Ecological importance:"
                    binding.usesDes.text = "• Provides food and shelter for wildlife like bears, deer, and turkeys.\n\n• Acorns, though bitter, are a valuable food source for animals, especially bears preparing for hibernation."
                    binding.scientificName.text = "Quercus ilicifolia"
                    binding.toxicityDes.text = "while not deadly, Bear Oak is considered mildly toxic.\n\n• The acorns contain tannins, which can cause stomach upset in humans and animals if consumed in large quantities.\n\n• The leaves and twigs have tiny hairs that can irritate the skin and eyes on contact."
                    binding.img1.setImageResource(R.drawable.bearoak1)
                    binding.img2.setImageResource(R.drawable.bearoak2)
                    binding.img3.setImageResource(R.drawable.bearoak3)}

                "Boxelder" -> {binding.infoPests.text = "The Boxelder, also known as Box Elder Maple or Manitoba Maple (Acer negundo L.), is a fast-growing deciduous tree native to North America. It's known for its rapid growth but can be a bit messy and short-lived in landscapes.\n\n• Growth habit: Fast-growing, short-lived tree with a spreading canopy and weak wood.\n\n• Leaves: Opposite, compound leaves with 3-7 leaflets that turn yellow in fall.\n\n• Flowers: Inconspicuous greenish flowers in spring.\n\n• Fruits: Winged seeds (samaras) that dangle from female trees."
                    binding.harmfulUseful.text = "Not Toxic"
                    binding.usesTitle.text = "Benefits and drawbacks:"
                    binding.usesDes.text = "• Fast growth and cold tolerance make it suitable for harsh environments.\n\n• Sap can be used to make syrup similar to sugar maple.\n\n• Detriments: Weak wood prone to breaking, messy dropping of leaves and seeds, attracts boxelder bugs, and susceptible to trunk decay."
                    binding.scientificName.text = "Acer negundo L."
                    binding.toxicityDes.text = "The Boxelder is generally considered non-toxic to humans and pets.\n\n• However, the sap can irritate skin, especially on sensitive individuals.\n\n• The winged seeds might cause minor stomach upset if ingested in large quantities, but this is unlikely due to their unappetizing nature."
                    binding.img1.setImageResource(R.drawable.boxelder1)
                    binding.img2.setImageResource(R.drawable.boxelder2)
                    binding.img3.setImageResource(R.drawable.boxelder3)}

                "Fragrant Sumac" -> {binding.infoPests.text = "The Fragrant Sumac (Rhus aromatica) is a delightful, low-maintenance shrub native to North America. It's prized for its aromatic foliage, vibrant fall colors, and ability to thrive in various conditions.\n\n• Size: Deciduous shrub, reaching 6-12 feet tall with a sprawling habit.\n\n• Leaves: Trifoliate (three leaflets), glossy green with a blue tinge, turning orange, red, purple, and yellow in fall. Crushed leaves release a pleasant, lemony scent.\n\n• Flowers: Small, yellowish catkins appear in early spring before leaves.\n\n• Fruits: Red, hairy berries attractive to birds and persisting into winter."
                    binding.harmfulUseful.text = "Not Toxic"
                    binding.usesTitle.text = "Landscaping uses:"
                    binding.usesDes.text = "• Excellent ground cover for slopes and banks due to its suckering habit.\n\n• Provides year-round interest with fragrant foliage, colorful berries, and stunning fall display.\n\n• Tolerates full sun to part shade and dry to moist soil conditions."
                    binding.scientificName.text = "Rhus aromatica"
                    binding.toxicityDes.text = "Unlike its close relative poison ivy, Fragrant Sumac is non-toxic. You can safely enjoy its fragrant leaves and attractive berries without any worries."
                    binding.img1.setImageResource(R.drawable.fragrantsumac1)
                    binding.img2.setImageResource(R.drawable.fragrantsumac2)
                    binding.img3.setImageResource(R.drawable.fragrantsumac3)}

                "Jack-in-the-pulpit" -> {binding.infoPests.text = "The Jack-in-the-Pulpit (Arisaema triphyllum) is a fascinating wildflower native to eastern North America. It's known for its unique flower structure, which resembles a pulpit enclosing a preacher (jack).\n\n• Size: 1-2 feet tall with 1 or 2 leaves each with three leaflets (triphyllum).\n\n• Flowers: Small, inconspicuous flowers clustered around a fleshy spike (spadix) enclosed by a large, colorful modified leaf (spathe). The spathe's colors can vary from green to striped green and maroon.\n\n• Bloom time: Spring (April to May)\n\n• Habitat: Moist woodlands with rich soil."
                    binding.harmfulUseful.text = "Toxic Plant"
                    binding.usesTitle.text = "Ecological importance:"
                    binding.usesDes.text = "• Provides food for insects and small mammals.\n\n• The bright red berries that appear in late summer are attractive to birds, helping disperse the seeds."
                    binding.scientificName.text = "Arisaema triphyllum"
                    binding.toxicityDes.text = "Beware! Jack-in-the-Pulpit is considered toxic to humans and pets. All parts of the plant contain calcium oxalate crystals, which can cause irritation, vomiting, and diarrhea if ingested. The root is the most toxic part. It's important to avoid ingesting any part of it, as it can be toxic."
                    binding.img1.setImageResource(R.drawable.jack1)
                    binding.img2.setImageResource(R.drawable.jack2)
                    binding.img3.setImageResource(R.drawable.jack3)}

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

                "Eastern Poison Oak" -> {binding.infoPests.text = "Eastern Poison Oak (Toxicodendron pubescens) is a close relative of poison ivy, sharing its infamous itch-inducing properties. While it offers some ecological benefits, it's best admired from a safe distance.\n\n• Shrub or vine: Grows as a low-lying shrub or a climbing vine, depending on its environment.\n\n• Leaves: Divided into three leaflets (like poison ivy) with smooth or slightly lobed edges. The upper surface is typically glossy green, while the underside may be hairy.\n\n• Habitat: Found in dry, open areas like woodlands, chaparral, and coastal scrublands, primarily in eastern North America."
                    binding.harmfulUseful.text = "Highly Toxic Plant"
                    binding.usesTitle.text = "Benefits and drawbacks:"
                    binding.usesDes.text = "• Wildlife food source: Provides berries for birds and browse for deer and rabbits.\n\n• Erosion control: Its dense root system helps stabilize soil on slopes and prevent erosion.\n\n• Highly toxic: Like poison ivy, all parts of Eastern Poison Oak contain urushiol oil, causing severe allergic skin reactions in most people. Contact leads to an itchy, blistering rash that can take weeks to heal.\n\n• Difficult to eradicate: Spreads easily through underground runners and seeds dispersed by animals."
                    binding.scientificName.text = "Toxicodendron pubescens"
                    binding.toxicityDes.text = "Eastern Poison Oak is highly toxic. Even brushing against its leaves, stems, or roots can transfer the urushiol oil and trigger an allergic reaction. The oil can also remain active on clothing, tools, pet fur, and even smoke from burning the plant, posing a risk for delayed reactions."
                    binding.img1.setImageResource(R.drawable.eastern_poison_oak1)
                    binding.img2.setImageResource(R.drawable.eastern_poison_oak2)
                    binding.img3.setImageResource(R.drawable.eastern_poison_oak3)}

                "Poison Sumac" -> {binding.infoPests.text = "Poison Sumac (Toxicodendron vernix) stands out even among its toxic relatives, poison ivy and oak.  This wetland dweller packs a powerful punch and should be avoided at all costs.\n\n• Habitat: Thrives in swamps, bogs, and other wet areas, unlike its drier-loving relatives.\n\n• Identification: Tall shrub or small tree with smooth, alternate leaves consisting of 7-13 leaflets, giving it a feathery appearance. Unlike poison ivy and oak, it has no central stalk for the leaflets.\n\n• Warning sign: Look for smooth, grey branches with a distinct lack of hairs or prickles."
                    binding.harmfulUseful.text = "Extremely Toxic Plant"
                    binding.usesTitle.text = "Benefits and drawbacks:"
                    binding.usesDes.text = "• Wildlife food source: The white berries provide sustenance for some birds in late summer and fall.\n\n• Highly Toxic: All parts of Poison Sumac contain urushiol, but it's present in much higher concentrations than in poison ivy or oak. Even minimal contact can cause a severe allergic reaction with painful blistering, swelling, and even respiratory issues from inhaling smoke from burning the plant.\n\n• Spreads easily: Reproduces through seeds and underground suckers, making eradication challenging."
                    binding.scientificName.text = "Toxicodendron vernix"
                    binding.toxicityDes.text = "Extremely Toxic.  Poison Sumac is considered the most toxic plant native to North America east of the Mississippi.  A single brush against its leaves, stems, or roots can trigger a severe allergic reaction.  The urushiol oil can also remain active on fur, clothing, and tools, posing a risk for delayed rashes."
                    binding.img1.setImageResource(R.drawable.poison_sumac1)
                    binding.img2.setImageResource(R.drawable.poison_sumac2)
                    binding.img3.setImageResource(R.drawable.poison_sumac3)}

                "Western Poison Ivy" -> {binding.infoPests.text = "While Eastern Poison Ivy (Toxicodendron radicans) may be the more notorious itch-inducer, Western Poison Ivy (Toxicodendron rydbergii) shouldn't be underestimated. This resident of western North America packs the same potent punch as its eastern cousin.\n\n• Western dweller: Found throughout western US, Great Plains, and some northeastern regions.\n\n• Variable appearance: Can be a low-growing shrub, vine (less common than Eastern Poison Ivy), or even have three leaflets like its eastern counterpart. Unlike the eastern variety, it may also produce small, white berries.\n\n• Identification tip: \"Leaves of three, let it be!\" applies here too. If unsure, it's always best to err on the side of caution."
                    binding.harmfulUseful.text = "Highly Toxic Plant"
                    binding.usesTitle.text = "Benefits and drawbacks:"
                    binding.usesDes.text = "• Limited wildlife food: White berries provide some sustenance for birds, but to a lesser extent than other native plants.\n\n• Highly Toxic: Like Eastern Poison Ivy, all parts of Western Poison Ivy contain urushiol oil, causing allergic dermatitis (rash) in most people. Even brushing against it can trigger an itchy, blistering rash that takes weeks to heal.\n\n• Spreads easily: Reproduces through seeds and underground runners, making eradication difficult."
                    binding.scientificName.text = "Toxicodendron rydbergii"
                    binding.toxicityDes.text = "Western Poison Ivy is highly toxic.  Just like its eastern relative, even minimal contact with any part of the plant can transfer the urushiol oil and cause a severe allergic reaction. The oil can also remain active on clothing, tools, pet fur, and even smoke from burning the plant."
                    binding.img1.setImageResource(R.drawable.western_poison_ivy1)
                    binding.img2.setImageResource(R.drawable.western_poison_ivy2)
                    binding.img3.setImageResource(R.drawable.western_poison_ivy3)}

                "Western Poison Oak" -> {binding.infoPests.text = "Western Poison Oak (Toxicodendron diversilobum) is aSneaky Scourge  found along the Pacific Coast of North America.  Just like its eastern counterpart, it  possesses the infamous urushiol oil, making it a plant to admire from afar.\n\n• Coastal dweller: Thrives in dry, open areas like woodlands, chaparral, and coastal scrublands, primarily in western North America.\n\n• Shape-shifter: Can appear as a dense shrub, a low-growing groundcover, or even a climbing vine, depending on its environment.\n\n• Leaves of Three: Similar to poison ivy, its leaves are divided into three leaflets. However, the edges can be smooth, lobed, or even wavy, making identification a bit trickier."
                    binding.harmfulUseful.text = "Toxic Plant"
                    binding.usesTitle.text = "Benefits and drawbacks:"
                    binding.usesDes.text = "• Limited wildlife food source: Provides some berries for birds and browse for deer in rare instances, but there are many safer alternatives.\n\n• Highly Toxic: All parts of Western Poison Oak contain urushiol oil, causing a severe allergic reaction (rash) in most people. Even brushing against it can trigger an itchy, blistering rash that can take weeks to heal.\n\n• Spreads easily: Reproduces through seeds and underground runners, making eradication difficult."
                    binding.scientificName.text = "Toxicodendron diversilobum"
                    binding.toxicityDes.text = "Western Poison Oak is highly toxic. Similar to Eastern Poison Oak, even minimal contact with its leaves, stems, or roots can transfer the urushiol oil and cause a severe allergic reaction. The oil can also remain active on clothing, tools, pet fur, and even smoke from burning the plant, posing a risk for delayed rashes."
                    binding.img1.setImageResource(R.drawable.western_poison_oak1)
                    binding.img2.setImageResource(R.drawable.western_poison_oak2)
                    binding.img3.setImageResource(R.drawable.western_poison_oak3)}

                "Image is empty" -> {binding.infoPests.text = "No plants were found in the image please capture the image again"
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
        val closeBtn: ImageView = findViewById(R.id.close_btn_toxic)
        closeBtn.setOnClickListener { finish() }
    }

}