package com.example.pdfbooks.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pdfbooks.database.PdfDatabase
import com.example.pdfbooks.databinding.FragmentHomeBinding
import com.example.pdfbooks.model.PdfData
import com.example.pdfbooks.view.adapter.PdfAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Home : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: PdfAdapter
    private val list = mutableListOf<PdfData>()
    private val pdfDao by lazy {
        PdfDatabase.INSTANCE?.pdfDao() ?: error("Database not initialized")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = PdfAdapter(list, onClick = {}, onLongClick = {})
        binding.RecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.RecyclerView.adapter = adapter

        loadData()
    }

    private fun loadData() {
        lifecycleScope.launch {
            val books = withContext(Dispatchers.IO){
                pdfDao.getAllBooks()
            }
            list.clear()
            list.addAll(books)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}