package com.example.uf1_proyecto.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.uf1_proyecto.core.Constants
import com.example.uf1_proyecto.databinding.FragmentPeliculaInfoBinding


class PeliculaInfoFragment : Fragment() {

    var _binding: FragmentPeliculaInfoBinding? = null

    val binding: FragmentPeliculaInfoBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPeliculaInfoBinding.inflate(inflater, container, false)
        val view = binding.root


        // Obtener los argumentos pasados con SafeArgs
        val args = PeliculaInfoFragmentArgs.fromBundle(requireArguments())

        // Acceder a los valores de los argumentos
        val titulo = args.titulo
        val sinopsis = args.sinopsis
        val caratula = args.caratula

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

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}