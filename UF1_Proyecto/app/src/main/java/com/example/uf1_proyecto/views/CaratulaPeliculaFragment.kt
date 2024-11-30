package com.example.uf1_proyecto.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.uf1_proyecto.core.Constants
import com.example.uf1_proyecto.databinding.FragmentCaratulaPeliculaBinding


class CaratulaPeliculaFragment : Fragment() {

    var _binding : FragmentCaratulaPeliculaBinding? = null
    val binding: FragmentCaratulaPeliculaBinding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCaratulaPeliculaBinding.inflate(inflater,container, false)
        val view = binding.root

        val args = CaratulaPeliculaFragmentArgs.fromBundle(requireArguments());
        val caratula = args.caratula
        Glide.with(requireContext())
            .load("${Constants.API_URL_IMAGE}${caratula}")
            .apply(RequestOptions().override(450, 330))
            .into(binding.imgCaratulaPeli)
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}