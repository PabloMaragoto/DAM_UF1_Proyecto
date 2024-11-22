package com.example.uf1_proyecto

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uf1_proyecto.adapter.PeliculaAdapter
import com.example.uf1_proyecto.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    var _binding: FragmentHomeBinding? = null

    val binding: FragmentHomeBinding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        // Inflate the layout for this fragment
        initRecycleView()
        return view

        /*
    * Está función se encargará de recuperar el recycle view del activity_main*/

    }

    fun initRecycleView() {

        //val manager = GridLayoutManager)
        val manager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        /*Podemos añadir separadores*/
        val decoration = DividerItemDecoration(requireContext(), manager.orientation)

        binding.recyclerPeliculas.layoutManager = manager
        /*{onItemSelected(it)} = {pelicula -> onItemSelected(pelicula)} */
        binding.recyclerPeliculas.adapter =
            PeliculaAdapter(PeliculasProvider.peliculasLista, { onItemSelected(it) })

        binding.recyclerPeliculas.addItemDecoration(decoration)
    }

    fun onItemSelected(pelicula: Pelicula) {
        Toast.makeText(requireContext(), pelicula.titulo, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}