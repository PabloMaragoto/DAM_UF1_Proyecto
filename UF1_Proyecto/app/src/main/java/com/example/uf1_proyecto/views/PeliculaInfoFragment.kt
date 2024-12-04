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
import com.example.uf1_proyecto.dao.ResenhaDao
import com.example.uf1_proyecto.databinding.FragmentPeliculaInfoBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class PeliculaInfoFragment : Fragment() {

    var _binding: FragmentPeliculaInfoBinding? = null

    val binding: FragmentPeliculaInfoBinding get() = _binding!!

    private lateinit var mDbRef: DatabaseReference

    private lateinit var resenhaDao : ResenhaDao


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPeliculaInfoBinding.inflate(inflater, container, false)
        val view = binding.root

        resenhaDao = ResenhaDao(requireContext())

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
            resenhaDao.getReview(currentUid, id, binding.etReview, onSuccess = { review -> // Handle success case if needed
            },
                onFailure = { exception ->
                    Toast.makeText(context, "Error al cargar la reseña", Toast.LENGTH_SHORT).show() })
        } else {//
            Toast.makeText(context, "Usuario no autenticado", Toast.LENGTH_SHORT).show()
        }

        val btnSend = binding.btnSend

        btnSend.setOnClickListener {

            if (currentUid != null) {
                resenhaDao.saveReview(currentUid, id, binding.etReview, onSuccess = { review -> // Handle success case if needed
                },
                    onFailure = { exception ->
                        Toast.makeText(context, "Error al guardar la reseña", Toast.LENGTH_SHORT).show() })
            } else {//
                Toast.makeText(context, "Usuario no autenticado", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}