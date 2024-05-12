package com.example.dotask

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.dotask.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: Database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database= Room.databaseBuilder(
            applicationContext,Database::class.java,"DoTask"
        ).build()

        binding.add.setOnClickListener{
            val intent= Intent(this, CreateTask::class.java)
            startActivity(intent)
        }
        setRecycler()
    }
    private fun setRecycler() {
        val tasks = DataObject.getAllData()
        if (tasks.isNotEmpty()) {
            binding.recyclerView.adapter = Adapter(tasks)
            binding.recyclerView.layoutManager = LinearLayoutManager(this)
            binding.noDataTextView.visibility = View.GONE // Hide the message if there are tasks
        } else {
            binding.recyclerView.visibility = View.GONE // Hide the RecyclerView
            binding.noDataTextView.visibility = View.VISIBLE // Show the message
        }
    }
}