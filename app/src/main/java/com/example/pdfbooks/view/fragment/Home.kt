package com.example.pdfbooks.view.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.pdfbooks.DetailsActivity
import com.example.pdfbooks.R
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
        adapter = PdfAdapter(
            list,
            onClick = {
                Toast.makeText(requireContext(), "Clicked ${it.name}", Toast.LENGTH_SHORT).show()
                startActivity(Intent(requireContext(), DetailsActivity::class.java))
            },
            onLongClick = { book ->
                AlertDialog.Builder(requireContext()).setMessage("Edit or Delete")
                    .setPositiveButton("Edit") { _, _ -> editFun(book) }
                    .setNegativeButton("Delete") { _, _ ->
                        lifecycleScope.launch(Dispatchers.IO) {
                            pdfDao.deleteData(book)
                            withContext(Dispatchers.Main){
                                loadData()
                            }
                        }
                    }.show()
            }
        )
        binding.RecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.RecyclerView.adapter = adapter

        loadData()
    }

    private fun editFun(data: PdfData) {
        val fragment = Add()
        val bundle = Bundle()
        bundle.putInt("id", data.id)
        bundle.putString("name", data.name)
        bundle.putString("author", data.author)
        bundle.putString("version", data.version)
        bundle.putString("publish", data.publish)
        bundle.putString("code", "y")

        fragment.arguments = bundle

        parentFragmentManager.beginTransaction()
            .replace(R.id.FrameLayout, fragment)
            .addToBackStack(null)
            .commit()
    }


    private fun loadData() {
        lifecycleScope.launch {
            val books = withContext(Dispatchers.IO) {
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}