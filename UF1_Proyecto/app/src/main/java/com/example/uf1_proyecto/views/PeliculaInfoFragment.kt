package com.example.uf1_proyecto.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.uf1_proyecto.core.Constants
import com.example.uf1_proyecto.databinding.FragmentPeliculaInfoBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class PeliculaInfoFragment : Fragment() {

    var _binding: FragmentPeliculaInfoBinding? = null

    val binding: FragmentPeliculaInfoBinding get() = _binding!!

    private lateinit var mDbRef: DatabaseReference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPeliculaInfoBinding.inflate(inflater, container, false)
        val view = binding.root

        mDbRef = FirebaseDatabase.getInstance("https://mispeliculasapp-default-rtdb.europe-west1.firebasedatabase.app/").getReference()
        val currentUid = FirebaseAuth.getInstance().currentUser?.uid

        // Obtener los argumentos pasados con SafeArgs
        val args = PeliculaInfoFragmentArgs.fromBundle(requireArguments())

        // Acceder a los valores de los argumentos
        val titulo = args.titulo
        val sinopsis = args.sinopsis
        val caratula = args.caratula
        var id = args.idPelicula

        Glide.with(requireContext())
            .load("${Constants.API_URL_IMAGE}${caratula}")
            .apply(RequestOptions().override(450, 330))
            .into(binding.caratulaPelicula)

        // Aquí puedes configurar tu UI con los valores recuperados
        binding.txtTituloInfoPelicula.text = titulo
        binding.txtSinopsisInfoPelicula.text = sinopsis

        binding.caratulaPelicula.setOnClickListener{
            val action = PeliculaInfoFragmentDirections.actionPeliculaInfoFragmentToCaratulaPeliculaFragment(caratula)
            view.findNavController().navigate(action)
        }

        if (currentUid != null) {
            mDbRef.child("reviews")
                .child(currentUid)
                .child(id)
                .get()
                .addOnSuccessListener { snapshot ->
                    if (snapshot.exists()) {
                        val textoGuardado = snapshot.child("textoGuardado").getValue(String::class.java)
                        if (textoGuardado != null) {
                            binding.etReview.setText(textoGuardado)
                        } else {
                            binding.etReview.setText("No hay texto disponible")
                        }
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Error al cargar el texto", Toast.LENGTH_SHORT).show()
                }
        } else {//
            Toast.makeText(context, "Usuario no autenticado", Toast.LENGTH_SHORT).show()
        }

        val btnSend = binding.btnSend

        btnSend.setOnClickListener {
            val texto = binding.etReview.text.toString()  // Obtener el texto del TextView

            val datosTexto = mapOf("textoGuardado" to texto)

            mDbRef.child("reviews").child(currentUid.toString()).child(id).setValue(datosTexto).addOnSuccessListener {
                // Acción cuando se guarda exitosamente
                Toast.makeText(context, "Texto guardado", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                // Acción en caso de error
                Toast.makeText(context, "Error al guardar el texto", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}