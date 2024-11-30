package com.example.pruebapeliculasapp.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uf1_proyecto.databinding.FragmentHomeBinding
import com.example.uf1_proyecto.viewmodels.PeliculasViewModel

class HomeFragment : Fragment() {

    var _binding: FragmentHomeBinding? = null

    val binding: FragmentHomeBinding get() = _binding!!

    private lateinit var viewModel: PeliculasViewModel

    private lateinit var adapterPelicula: AdapterPelicula


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        setUpRecyclerView()

        viewModel = ViewModelProvider(this)[PeliculasViewModel::class.java]
        viewModel.getNowPlaying()

        /*viewModel.getParasite()*/

        viewModel.peliculasList.observe(viewLifecycleOwner){
            adapterPelicula.peliculasList = it
            adapterPelicula.notifyDataSetChanged()
        }

        return view

    }
    private fun setUpRecyclerView(){

        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        /*Podemos añadir separadores*/
        val decoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.recyclerPeliculas.layoutManager = layoutManager
        adapterPelicula = AdapterPelicula(requireContext(), arrayListOf())
        binding.recyclerPeliculas.adapter = adapterPelicula
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}