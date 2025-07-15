package com.example.pdfbooks.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.pdfbooks.R
import com.example.pdfbooks.database.PdfDatabase
import com.example.pdfbooks.databinding.FragmentAddBinding
import com.example.pdfbooks.model.PdfData
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Add : Fragment() {
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    private val pdfDao by lazy {
        PdfDatabase.INSTANCE?.pdfDao() ?: error("Database not initialized")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.SaveBtn.setOnClickListener {
            saveBook()
        }
        binding.BackBtn.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.FrameLayout, Home()).addToBackStack(null).commit()

            val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.BottomNavigationView)
            bottomNav.selectedItemId = R.id.Home
        }
    }

    private fun saveBook() {
        val name = binding.TextInputEditText1.text.toString().trim()
        val author = binding.TextInputEditText2.text.toString().trim()
        val version = binding.TextInputEditText3.text.toString().trim()
        val publish = binding.TextInputEditText4.text.toString().trim()

        if (name.isEmpty() || author.isEmpty()) {
            Toast.makeText(
                requireContext(),
                "Book Name and Author are required",
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        val newBook = PdfData(
            id = 0,
            name = name,
            author = author,
            version = version,
            publish = publish
        )

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                pdfDao.insertData(newBook)
            }
            Toast.makeText(requireContext(), "Book saved successfully", Toast.LENGTH_SHORT).show()
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}