package com.example.loginscreen.categories

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.example.loginscreen.R
import com.example.loginscreen.adapter.MyAdapter
import com.example.loginscreen.databinding.FruitsCategoryBinding
import com.example.loginscreen.home.User2

class Fruits: AppCompatActivity() {
    private lateinit var binding: FruitsCategoryBinding
    private lateinit var userArrayList: ArrayList<User2>
    private lateinit var myAdapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FruitsCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        myAdapter = MyAdapter(this, userArrayList)
//        binding.listView.adapter = myAdapter
//        callbacks
//        search()
        cancelBtn()


        val imageId = intArrayOf(

            R.drawable.apple_fruit,
            R.drawable.avocado,
            R.drawable.banana,
            R.drawable.blackberry,
            R.drawable.blueberry,
            R.drawable.cherry,
            R.drawable.cloudberry,
            R.drawable.coconut,
            R.drawable.crab_apple,
            R.drawable.grape,
            R.drawable.honeyberry,
            R.drawable.kiwi,
            R.drawable.lemon,
            R.drawable.loganberry,
            R.drawable.lychee,
            R.drawable.mango,
            R.drawable.orange,
            R.drawable.peach,
            R.drawable.raspberry,
            R.drawable.strawberry_fruit,
            R.drawable.watermelons
        )

        val name = arrayOf(
            "Apple",
            "Avocado",
            "Banana",
            "Blackberry",
            "Blueberry",
            "Cherry",
            "Cloudberry",
            "Coconut",
            "Crab apple",
            "Grape",
            "Honeyberry",
            "Kiwi",
            "Lemon",
            "Loganberry",
            "Lychee",
            "Mango",
            "Orange",
            "Peach",
            "Raspberry",
            "Strawberry",
            "Watermelon"

        )

        val desc = arrayOf(
            "Malus domestica",
            "Persea americana",
            "Musa spp.",
            "Rubus fruticosus",
            "accinium corymbosum",
            "Prunus avium",
            "Rubus chamaemorus",
            "Cocos nucifera",
            "Malus sylvestris",
            "Vitis vinifera",
            "Lonicera caerulea",
            "Actinidia deliciosa",
            "Citrus limon",
            "Rubus loganobaccus",
            "Litchi chinensis",
            "Mangifera indica",
            "Citrus sinensis",
            "Prunus persica",
            "Rubus idaeus",
            "Fragaria x ananassa",
            "Citrullus lanatus"

        )

        val overview = arrayOf(
            "Apples come in two main forms:\n\nThe fruit:\n\nPopular: One of the most widely cultivated tree fruits globally.\n\nVariety: Thousands of varieties exist, varying in size, shape, color (red, green, yellow), and taste (sweet, tart).\n\nUses: Eaten fresh, cooked in various dishes, used for juice, cider, vinegar, and various other products.\n\nNutrition: Good source of fiber and vitamin C.\n\nThe tree:\n\nOrigin: Belonging to the rose family, domesticated from wild ancestors in Central Asia.\n\nClimate: Prefers cool climates with mild winters and warm summers.\n\nGrowth: Can reach heights of 25-40 feet.\n\nSite: Needs well-drained soil and full to partial sun for optimal growth and fruit production.",
            "Persea americana",
            "Musa spp.",
            "Rubus fruticosus",
            "Blueberries are a delicious and nutritious fruit native to North America. They are known for their sweet and tart flavor, and are packed with antioxidants and vitamins.\n\nVarieties: Many varieties exist, with fruits ranging in size, color (blue, pink, white), and ripening time.\n\nUses: Eaten fresh, frozen, dried, used in jams, pies, muffins, and various other products.\n\nHealth benefits: Excellent source of antioxidants, vitamin C, and fiber.",
            "Cherries are a vibrant and juicy fruit enjoyed worldwide for their sweet and slightly tart flavor. They come in two main types: sweet cherries and tart cherries, each with their unique culinary uses.\n\nVarieties: Numerous varieties exist, varying in color (red, yellow, black), sweetness, and ripening time.\n\nUses: Eat fresh, frozen, dried, canned, and used in pies, jams, juices, and various other products.\n\nHealth benefits: Good source of vitamins A and C, fiber, and antioxidants.",
            "Rubus chamaemorus",
            "Cocos nucifera",
            "Malus sylvestris",
            "Vitis vinifera",
            "Lonicera caerulea",
            "Actinidia deliciosa",
            "Citrus limon",
            "Rubus loganobaccus",
            "Litchi chinensis",
            "Mangifera indica",
            "Citrus sinensis",
            "Prunus persica",
            "Rubus idaeus",
            "Fragaria x ananassa",
            "Citrullus lanatus"

        )

        val pruning = arrayOf(
            "The best time to prune apple trees is late winter (around February/March in most regions) while the tree is still dormant. This allows for quicker healing of the cuts before spring growth begins.\n\nGeneral Tips:\n\n• Always use sharp, clean pruning tools to make clean cuts.\n\n• Make cuts just above an outward-facing bud to encourage new growth in the desired direction.\n\n• Avoid excessive pruning, as it can stress the tree and reduce fruit production.\n\n• For detailed pruning diagrams and specific instructions based on your apple tree variety, consult your local gardening resources or extension service.",
            "Avocado",
            "Banana",
            "Blackberry",
            "Timing: Prune your blueberry bushes in late winter (around February/March in most regions) while the plant is still dormant.\n\nMethod: Focus on removing dead, diseased, or unproductive branches to improve air circulation and light penetration. You can also thin out crowded branches to prevent overcrowding and encourage fruit production.",
            "Timing: Prune your cherry tree in late winter (around February/March in most regions) while the tree is still dormant.\n\nMethod: Focus on removing dead, diseased, or unproductive branches to improve air circulation and light penetration. You can also thin out crowded branches to prevent overcrowding and encourage fruit production.\n\nPruning for size and shape: Prune strategically to maintain the desired size and shape of your tree, especially if it becomes overgrown.",
            "Cloudberry",
            "Coconut",
            "Crab apple",
            "Grape",
            "Honeyberry",
            "Kiwi",
            "Lemon",
            "Loganberry",
            "Lychee",
            "Mango",
            "Orange",
            "Peach",
            "Raspberry",
            "Strawberry",
            "Watermelon"

        )

        val watering = arrayOf(
            "• Regularly water your apple tree, especially during dry periods. Aim for 1-2 inches of water per week, delivered slowly to allow for deep root soaking.\n\n• Newly planted trees will need more frequent watering than established trees.\n\n• During the fruit formation stage, consistent moisture is crucial for good fruit development.",
            "Avocado",
            "Banana",
            "Blackberry",
            "Frequency: Water your blueberry bushes regularly, especially during dry periods, aiming to keep the soil consistently moist but not soggy.\n\nAmount: Provide 1-2 inches of water per week, delivered slowly to allow for deep root soaking.\n\nNewly planted bushes: Water more frequently, around every other day, during the first year or two.",
            "Frequency: Water your cherry tree regularly, especially during dry periods. Aim for 1-2 inches of water per week, delivered slowly to soak the soil deeply.\n\nNewly planted trees: Water more frequently, around every other day, during the first year or two.\n\nFruit development: Ensure consistent moisture during fruit development, especially when the cherries are about the size of a pea.",
            "Cloudberry",
            "Coconut",
            "Crab apple",
            "Grape",
            "Honeyberry",
            "Kiwi",
            "Lemon",
            "Loganberry",
            "Lychee",
            "Mango",
            "Orange",
            "Peach",
            "Raspberry",
            "Strawberry",
            "Watermelon"

        )

        val fertilizing = arrayOf(
            "• Fertilize your apple tree in early spring with a balanced fertilizer, following the instructions on the package. Avoid over-fertilizing, as this can harm the tree.\n\n• A soil test can help determine the specific nutrient needs of your tree.\n\n• Established trees may not need fertilizing every year, especially if they are growing well.",
            "Avocado",
            "Banana",
            "Blackberry",
            "Time: Fertilize your blueberry bushes once in early spring (around March or April in most regions) with a fertilizer formulated for acid-loving plants like azaleas and rhododendrons.\n\nAmount: Follow the instructions on the fertilizer package for the recommended application rate based on the age and size of your bushes.\n\nEstablished bushes: Established bushes may not need fertilizing every year, especially if they are showing good growth. A soil test can help determine if fertilization is necessary.",
            "Time: Fertilize your cherry tree once in early spring (around March or April in most regions) with a balanced fertilizer formulated for fruit trees.\n\nAmount: Follow the instructions on the fertilizer package for the recommended application rate based on the age and size of your tree.\n\nEstablished trees: Established trees may not need fertilizing every year, especially if they are showing good growth. A soil test can help determine if fertilization is necessary.",
            "Cloudberry",
            "Coconut",
            "Crab apple",
            "Grape",
            "Honeyberry",
            "Kiwi",
            "Lemon",
            "Loganberry",
            "Lychee",
            "Mango",
            "Orange",
            "Peach",
            "Raspberry",
            "Strawberry",
            "Watermelon"

        )

        val temperature = arrayOf(
            "• Apple trees prefer cool climates with mild winters and warm summers. They can tolerate a wide range of temperatures, but prolonged periods of extreme heat or cold can stress the tree and affect fruit production\n\nSpecifics: Ideally, apple trees need at least 800 hours of chilling temperatures (below 7°C or 45°F) during the winter months to bloom and set fruit properly.",
            "Avocado",
            "Banana",
            "Blackberry",
            "Ideal: Blueberries prefer cool climates with mild summers and cold winters.\n\nSpecifics: They thrive in temperatures between 18°C to 24°C (64°F to 75°F) during the growing season. However, they can tolerate a wide range of temperatures, including freezing winter temperatures.",
            "Ideal: Cherry trees prefer cool climates with mild winters and warm summers.\n\nSpecifics: They require a period of chilling temperatures (below 7°C or 45°F) during the winter months to bloom and set fruit properly. The exact chilling requirement varies depending on the variety.",
            "Cloudberry",
            "Coconut",
            "Crab apple",
            "Grape",
            "Honeyberry",
            "Kiwi",
            "Lemon",
            "Loganberry",
            "Lychee",
            "Mango",
            "Orange",
            "Peach",
            "Raspberry",
            "Strawberry",
            "Watermelon"

        )

        val site = arrayOf(
            "• Full sun (at least 6 hours of direct sunlight per day) is ideal for apple trees. They will also tolerate partial sun (3-6 hours of direct sunlight per day), but fruit production may be reduced.\n\n• Apple trees will not fruit well in full shade or indirect sunlight.\n\n• The site should also have well-drained soil. Apple trees do not tolerate waterlogged conditions.",
            "Avocado",
            "Banana",
            "Blackberry",
            "Sunlight: Blueberries require full sun, at least 6 hours of direct sunlight per day. They will not fruit well in partial shade or full shade.\n\nSoil: Blueberries thrive in acidic, well-drained soil with a pH between 4.0 and 5.5. If your soil is not naturally acidic, you can amend it with peat moss, pine needles, or sulfur.",
            "Sunlight: Cherry trees require full sun, at least 6 hours of direct sunlight per day. They will not fruit well in partial shade or full shade.\n\nSoil: Cherry trees prefer well-drained soil with a pH between 6.0 and 7.0. Sandy loam or loam soil is ideal.",
            "Cloudberry",
            "Coconut",
            "Crab apple",
            "Grape",
            "Honeyberry",
            "Kiwi",
            "Lemon",
            "Loganberry",
            "Lychee",
            "Mango",
            "Orange",
            "Peach",
            "Raspberry",
            "Strawberry",
            "Watermelon"

        )

        userArrayList = ArrayList()

        for (i in name.indices){
            val user = User2(name[i],desc[i],overview[i],pruning[i],watering[i],imageId[i],fertilizing[i],temperature[i],site[i])
            userArrayList.add(user)
        }
        binding.listView.isClickable = true
        binding.listView.adapter = MyAdapter(this, userArrayList)
        binding.listView.setOnItemClickListener{ parent, view, position, id ->
            val name = name[position]
            val desc = desc[position]
            val overview = overview[position]
            val pruning = pruning[position]
            val watering = watering[position]
            val imageId = imageId[position]
            val temperature = temperature[position]
            val fertilizing = fertilizing[position]
            val site = site[position]

            val i = Intent(this, CategoryResult::class.java  )

            i.putExtra("name",name)
            i.putExtra("desc",desc)
            i.putExtra("overview",overview)
            i.putExtra("pruning",pruning)
            i.putExtra("watering",watering)
            i.putExtra("imageId",imageId)
            i.putExtra("temperature",temperature)
            i.putExtra("fertilizing",fertilizing)
            i.putExtra("site",site)
            startActivity(i)

        }

    }

    private fun cancelBtn(){

        binding.cancelTv.setOnClickListener { finish() }

    }

    private fun search() {

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                myAdapter.getFilter().filter(newText)
                return false
            }
        })

    }

}