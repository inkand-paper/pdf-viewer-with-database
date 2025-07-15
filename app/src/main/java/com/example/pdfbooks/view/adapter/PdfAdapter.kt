package com.example.pdfbooks.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pdfbooks.databinding.ItemListBinding
import com.example.pdfbooks.model.PdfData

class PdfAdapter(
    private val pdfList: MutableList<PdfData>,
    private val onClick: (PdfData) -> Unit,
    private val onLongClick: (PdfData) -> Unit
) : RecyclerView.Adapter<PdfAdapter.PdfViewHolder>() {
    inner class PdfViewHolder(val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PdfViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PdfViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return pdfList.size
    }

    override fun onBindViewHolder(holder: PdfViewHolder, position: Int) {
        val singleData = pdfList[position]
        holder.binding.apply {
            holder.binding.Name.text = singleData.name
            holder.binding.Author.text = singleData.author
            holder.binding.Version.text = singleData.version
            holder.binding.Publish.text = singleData.publish

            root.setOnClickListener{
                onClick(singleData)
            }
            root.setOnClickListener {
                onLongClick(singleData)
                true
            }
        }
    }
}