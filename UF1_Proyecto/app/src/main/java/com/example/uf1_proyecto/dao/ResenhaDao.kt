package com.example.uf1_proyecto.dao

import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ResenhaDao : IDao {

    private lateinit var mDbRef: DatabaseReference

    constructor(mDbRef: DatabaseReference) {
        this.mDbRef =
            FirebaseDatabase.getInstance("https://mispeliculasapp-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference()

    }


    override fun getReview(
        uid: String,
        movieId: String,
        onSuccess: (String?) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        mDbRef.child("reviews")
            .child(uid)
            .child(movieId)
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
    }

    override fun saveReview(
        uid: String,
        movieId: String,
        review: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        TODO("Not yet implemented")
    }
}