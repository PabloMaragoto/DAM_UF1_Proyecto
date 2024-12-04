package com.example.uf1_proyecto.dao

import android.content.Context
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ResenhaDao (private val context: Context): IDao {

    private var mDbRef: DatabaseReference = FirebaseDatabase.getInstance("https://mispeliculasapp-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference()

    override fun getReview(uid: String,
                           movieId: String,
                           etReview: EditText,
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
                        etReview.setText(textoGuardado)
                    } else {
                        etReview.setText("No hay texto disponible")
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
        etReview: EditText,
        onSuccess: (String?) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val texto =etReview.text.toString()

        val datosTexto = mapOf("textoGuardado" to texto)

        mDbRef.child("reviews").child(uid).child(movieId).setValue(datosTexto).addOnSuccessListener {
            Toast.makeText(context, "Texto guardado", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(context, "Error al guardar el texto", Toast.LENGTH_SHORT).show()
        }
    }
}