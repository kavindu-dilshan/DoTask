package com.example.dotask

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.dotask.databinding.ActivityUpdateTaskBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UpdateTask : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateTaskBinding
    private lateinit var database: Database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = Room.databaseBuilder(
            applicationContext, Database::class.java, "DoTask"
        ).build()

        val pos = intent.getIntExtra("id", -1)
        if (pos != -1) {
            val title = DataObject.getData(pos).title
            val priority = DataObject.getData(pos).priority
            val description = DataObject.getData(pos).description
            binding.createTitle.setText(title)
            binding.createDescription.setText(description)

            when (priority) {
                "High" -> binding.priorityHigh.isChecked = true
                "Medium" -> binding.priorityMedium.isChecked = true
                "Low" -> binding.priorityLow.isChecked = true
            }

            binding.deleteButton.setOnClickListener {
                DataObject.deleteData(pos)
                GlobalScope.launch {
                    database.dao().deleteTask(
                        Entity(
                            pos + 1,
                            binding.createTitle.text.toString(),
                            priority,
                            binding.createDescription.text.toString()
                        )
                    )
                    runOnUiThread {
                        Toast.makeText(this@UpdateTask, "Task deleted successfully", Toast.LENGTH_SHORT).show()
                    }
                }
                navigateToMain()
            }

            binding.updateButton.setOnClickListener {
                val priority = when {
                    binding.priorityHigh.isChecked -> "High"
                    binding.priorityMedium.isChecked -> "Medium"
                    binding.priorityLow.isChecked -> "Low"
                    else -> ""
                }

                DataObject.updateData(
                    pos,
                    binding.createTitle.text.toString(),
                    priority,
                    binding.createDescription.text.toString()

                )
                GlobalScope.launch {
                    database.dao().updateTask(
                        Entity(
                            pos + 1,
                            binding.createTitle.text.toString(),
                            priority,
                            binding.createDescription.text.toString(),
                        )
                    )
                    runOnUiThread {
                        Toast.makeText(this@UpdateTask, "Task updated successfully", Toast.LENGTH_SHORT).show()
                    }
                }
                navigateToMain()
            }
        }
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}