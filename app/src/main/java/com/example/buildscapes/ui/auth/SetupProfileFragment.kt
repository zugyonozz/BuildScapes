package com.example.buildscapes.ui.auth

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.buildscapes.R

class SetupProfileFragment : Fragment(R.layout.fragment_setup_profile) {

    private lateinit var ivProfile: ImageView
    private var imageUri: Uri? = null

    // Cara modern ambil gambar dari galeri (tanpa onActivityResult jadul)
    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            imageUri = it
            ivProfile.setImageURI(it) // Tampilkan gambar yang dipilih
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ivProfile = view.findViewById(R.id.ivProfileImage)
        val btnBack = view.findViewById<ImageView>(R.id.btnBack)
        val btnContinue = view.findViewById<Button>(R.id.btnContinue)

        // Klik foto -> Buka Galeri
        view.findViewById<View>(R.id.layoutAvatar).setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        // Back -> Balik ke halaman sebelumnya (kalau user berubah pikiran/mau logout)
        btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        btnContinue.setOnClickListener {
            val fullName = view.findViewById<EditText>(R.id.etFullName).text.toString()
            val phone = view.findViewById<EditText>(R.id.etPhone).text.toString()

            if (fullName.isEmpty()) {
                Toast.makeText(context, "Nama asli wajib diisi, Bos!", Toast.LENGTH_SHORT).show()
            } else {
                // Di sini nanti logika SIMPAN DATA ke database/server
                // ...

                // Kalau sukses simpan, baru masuk Home
                Toast.makeText(context, "Profile Saved!", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_setup_to_home)
            }
        }
    }
}