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
    private lateinit var book: PdfData
    private var isEditMode = false

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
        editBook()
    }

    private fun editBook() {
        val bundle = arguments
        if (bundle?.getString("code") == "y"){
            isEditMode = true

            binding.TextInputEditText1.setText(bundle.getString("name"))
            binding.TextInputEditText2.setText(bundle.getString("author"))
            binding.TextInputEditText3.setText(bundle.getString("version"))
            binding.TextInputEditText4.setText(bundle.getString("publish"))
            binding.SaveBtn.text = "Update"
        }
    }

    private fun saveBook() {
        val name = binding.TextInputEditText1.text.toString().trim()
        val author = binding.TextInputEditText2.text.toString().trim()
        val version = binding.TextInputEditText3.text.toString().trim()
        val publish = binding.TextInputEditText4.text.toString().trim()

        if (name.isEmpty() || author.isEmpty()) {
            Toast.makeText(requireContext(), "Book Name and Author are required", Toast.LENGTH_SHORT).show()
            return
        }

        val bookId = if (isEditMode) arguments?.getInt("id") ?: 0 else 0

        val newBook = PdfData(
            id = bookId,
            name = name,
            author = author,
            version = version,
            publish = publish
        )

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                if (isEditMode) {
                    pdfDao.updateData(newBook)
                } else {
                    pdfDao.insertData(newBook)
                }
            }
            val msg = if (isEditMode) "Book updated successfully" else "Book saved successfully"
            Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
            requireActivity().supportFragmentManager.popBackStack()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}