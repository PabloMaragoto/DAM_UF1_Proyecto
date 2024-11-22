package com.example.uf1_proyecto

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uf1_proyecto.adapter.PeliculaAdapter
import com.example.uf1_proyecto.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initRecycleView()
    }

    /*
    * Está función se encargará de recuperar el recycle view del activity_main*/
    fun initRecycleView() {

        //val manager = GridLayoutManager)
        val manager = LinearLayoutManager(this)
        /*Podemos añadir separadores*/
        val decoration = DividerItemDecoration(this, manager.orientation)

        binding.recyclerPeliculas.layoutManager = manager
        /*{onItemSelected(it)} = {pelicula -> onItemSelected(pelicula)} */
        binding.recyclerPeliculas.adapter =
            PeliculaAdapter(PeliculasProvider.peliculasLista, { onItemSelected(it) })

        binding.recyclerPeliculas.addItemDecoration(decoration)
    }

    fun onItemSelected(pelicula: Pelicula) {
        Toast.makeText(this, pelicula.titulo, Toast.LENGTH_SHORT).show()
    }
}