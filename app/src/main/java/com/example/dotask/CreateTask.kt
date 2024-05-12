package com.example.dotask

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.dotask.databinding.ActivityCreateTaskBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CreateTask : AppCompatActivity() {
    private lateinit var binding: ActivityCreateTaskBinding
    private lateinit var database: Database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database= Room.databaseBuilder(
            applicationContext,Database::class.java,"DoTask"
        ).build()

        binding.saveButton.setOnClickListener {
            val title = binding.createTitle.text.toString().trim()
            val description = binding.createDescription.text.toString().trim()
            val priority = when (binding.createPriorityRadioGroup.checkedRadioButtonId) {
                R.id.priority_high -> "High"
                R.id.priority_medium -> "Medium"
                R.id.priority_low -> "Low"
                else -> ""
            }

            if (title.isNotEmpty() && priority.isNotEmpty() && description.isNotEmpty()) {
                DataObject.setData(title,priority,description)
                GlobalScope.launch {
                    database.dao().insertTask(Entity(0,title,priority,description))
                    runOnUiThread {
                        Toast.makeText(this@CreateTask, "Task created successfully", Toast.LENGTH_SHORT).show()
                    }
                }
                GlobalScope.launch{
                    Log.i("database",database.dao().getTasks().toString())
                }

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
