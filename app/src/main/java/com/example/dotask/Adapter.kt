package com.example.dotask

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dotask.databinding.ViewBinding

class Adapter(private val data: List<TaskInfo>) : RecyclerView.Adapter<Adapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
        val title = binding.title
        val priority = binding.priority
        val layout = binding.mylayout
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = data[position]
        val context = holder.itemView.context

        holder.title.text = currentItem.title
        holder.priority.text = currentItem.priority

        holder.itemView.setOnClickListener {
            val intent = Intent(context, UpdateTask::class.java)
            intent.putExtra("id", position)
            context.startActivity(intent)
        }
    }
}
