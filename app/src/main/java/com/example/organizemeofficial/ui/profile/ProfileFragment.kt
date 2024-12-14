package com.example.organizemeofficial.ui.profile

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import android.widget.TextView
import com.example.organizemeofficial.R

class ProfileFragment : Fragment() {

    private lateinit var profilePicture: ImageView
    private lateinit var selectImageButton: Button
    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_IMAGE_SELECT = 2
    private val REQUEST_PERMISSION_READ_EXTERNAL_STORAGE = 3

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Referencias a los elementos de la UI
        val nombreUsuario: String = "Ana"
        val mensajeBienvenida: TextView = view.findViewById(R.id.welcome_message)
        val informacionUsuario: TextView = view.findViewById(R.id.user_info)
        val switchCambiarTema: SwitchCompat = view.findViewById(R.id.toggle_theme_switch)
        profilePicture = view.findViewById(R.id.profile_picture)
        val changeProfilePictureButton: Button = view.findViewById(R.id.change_profile_picture_button)
        selectImageButton = view.findViewById(R.id.select_image_button)

        // Mensaje de bienvenida
        mensajeBienvenida.text = "¡Bienvenida a tu perfil, $nombreUsuario!"

        // Información del usuario
        informacionUsuario.text = "Nombre: $nombreUsuario\nCorreo: $nombreUsuario@ejemplo.com"

        // Cambiar tema
        val sharedPreferences = requireActivity().getSharedPreferences("AppPreferences", 0)
        val esTemaOscuro = sharedPreferences.getBoolean("isDarkTheme", false)
        switchCambiarTema.isChecked = esTemaOscuro

        switchCambiarTema.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().apply {
                putBoolean("isDarkTheme", isChecked)
                apply()
            }

            AppCompatDelegate.setDefaultNightMode(
                if (isChecked) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            )
        }

        // Cambiar foto de perfil
        changeProfilePictureButton.setOnClickListener {
            selectImageButton.visibility = View.VISIBLE
        }

        // Seleccionar imagen desde la galería
        selectImageButton.setOnClickListener {
            if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(),
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_PERMISSION_READ_EXTERNAL_STORAGE)
            } else {
                openGallery()
            }
        }

        return view
    }

    private fun openGallery() {
        val selectPictureIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        if (selectPictureIntent.resolveActivity(requireActivity().packageManager) != null) {
            startActivityForResult(selectPictureIntent, REQUEST_IMAGE_SELECT)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_READ_EXTERNAL_STORAGE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                openGallery()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_IMAGE_CAPTURE -> {
                    val imageBitmap = data?.extras?.get("data") as Bitmap
                    profilePicture.setImageBitmap(imageBitmap)
                }
                REQUEST_IMAGE_SELECT -> {
                    val imageUri: Uri? = data?.data
                    profilePicture.setImageURI(imageUri)
                }
            }
        }
    }
}