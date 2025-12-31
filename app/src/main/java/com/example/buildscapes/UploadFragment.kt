package com.example.buildscapes

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment

class UploadFragment : Fragment() {

    private var selectedImageUri: Uri? = null
    private lateinit var imgPreview: ImageView

    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            selectedImageUri = uri
            imgPreview.setImageURI(uri)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_upload, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imgPreview = view.findViewById(R.id.imgPreview)
        val btnUpload = view.findViewById<Button>(R.id.btnUpload)
        val cardImage = view.findViewById<View>(R.id.cardImagePreview)
        val etTitle = view.findViewById<EditText>(R.id.etTitle)

        cardImage.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        btnUpload.setOnClickListener {
            val title = etTitle.text.toString()

            if (selectedImageUri == null) {
                Toast.makeText(requireContext(), "Pilih gambar dulu dong!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (title.isEmpty()) {
                Toast.makeText(requireContext(), "Judul tidak boleh kosong!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Toast.makeText(requireContext(), "Upload Berhasil!", Toast.LENGTH_LONG).show()

            imgPreview.setImageResource(R.drawable.ic_launcher_background)
            etTitle.text.clear()
            selectedImageUri = null
        }
    }
}