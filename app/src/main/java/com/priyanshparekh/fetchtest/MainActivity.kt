package com.priyanshparekh.fetchtest

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.priyanshparekh.fetchtest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val dataViewModel: DataViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val itemList = ArrayList<Item>()
        val adapter = ItemAdapter(itemList)

        binding.rvItemList.adapter = adapter
        binding.rvItemList.layoutManager = LinearLayoutManager(this)
        binding.rvItemList.setHasFixedSize(true)

        binding.circularProgress.visibility = View.VISIBLE
        dataViewModel.getData()

        dataViewModel.dataStatus.observe(this) { items ->
            // Response Unsuccessful
            if (items == null) {
                Snackbar.make(binding.main, "Error fetching data", Snackbar.LENGTH_SHORT).show()
                binding.circularProgress.visibility = View.GONE
                return@observe
            }


            // Response Successful
            itemList.clear()

            itemList.addAll(
                items
                    .filter {  item: Item -> return@filter !item.name.isNullOrEmpty() }             // Filters null and empty names
                    .sortedWith(compareBy(Item::listId, Item::name))                                // Sorts by List Id and then by Name
            )

            adapter.notifyDataSetChanged()
            binding.circularProgress.visibility = View.GONE
        }
    }
}