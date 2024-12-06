package com.example.uf1_proyecto.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uf1_proyecto.adapters.AdapterPelicula
import com.example.uf1_proyecto.databinding.FragmentHomeBinding
import com.example.uf1_proyecto.viewmodels.PeliculasViewModel
import com.example.uf1_proyecto.viewmodels.SharedViewModel

class HomeFragment : Fragment() {

    var _binding: FragmentHomeBinding? = null

    val binding: FragmentHomeBinding get() = _binding!!

    private lateinit var viewModel: PeliculasViewModel

    private val sharedViewModel: SharedViewModel by activityViewModels()

    private lateinit var adapterPelicula: AdapterPelicula

    private lateinit var adapterPeliculaPopular: AdapterPelicula


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        setUpRecyclerView()

        sharedViewModel.setBottomBarVisibility(true)

        viewModel = ViewModelProvider(this)[PeliculasViewModel::class.java]
        viewModel.getNowPlaying()

        viewModel.peliculasList.observe(viewLifecycleOwner){
            adapterPelicula.peliculasList = it
            adapterPelicula.notifyDataSetChanged()
        }

        viewModel.getPopular()

        viewModel.peliculasListPopular.observe(viewLifecycleOwner){
            adapterPeliculaPopular.peliculasList = it
            adapterPelicula.notifyDataSetChanged()
        }

        return view //@

    }
    private fun setUpRecyclerView(){

        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        /*Podemos añadir separadores*/
        val decoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.recyclerPeliculas.layoutManager = layoutManager
        adapterPelicula = AdapterPelicula(requireContext(), arrayListOf())
        binding.recyclerPeliculas.adapter = adapterPelicula

        val layoutManagerPopular = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerPeliculasPopular.layoutManager = layoutManagerPopular
        adapterPeliculaPopular = AdapterPelicula(requireContext(), arrayListOf())
        binding.recyclerPeliculasPopular.adapter = adapterPeliculaPopular
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}