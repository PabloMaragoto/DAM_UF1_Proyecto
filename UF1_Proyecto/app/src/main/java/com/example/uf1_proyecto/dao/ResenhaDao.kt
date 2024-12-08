package com.example.uf1_proyecto.dao

import android.content.Context
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import com.example.uf1_proyecto.R
import com.example.uf1_proyecto.models.Review
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ResenhaDao (private val context: Context): IDao {

    private var mDbRef: DatabaseReference = FirebaseDatabase.getInstance("https://mispeliculasapp-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference()

    override fun getReview(uid: String,
                           movieId: String,
                           etReview: EditText,
                           rating: RatingBar,
                           onSuccess: (String?) -> Unit,
                           onFailure: (Exception) -> Unit
    ) {
        mDbRef.child("reviews")
            .child(uid)
            .child(movieId)
            .get()
            .addOnSuccessListener { snapshot ->
                if (snapshot.exists()) {
                    val resenhaGuardada = snapshot.getValue(Review::class.java)
                    if (resenhaGuardada != null) {
                        etReview.setText(resenhaGuardada.texto)
                        rating.rating = resenhaGuardada.rating
                    } else {
                        etReview.setText("")
                    }
                }
            }
            .addOnFailureListener {
                Toast.makeText(context, R.string.errorLoadReview, Toast.LENGTH_SHORT).show()
            }

    }

    override fun saveReview(
        uid: String,
        movieId: String,
        etReview: EditText,
        rating: RatingBar,
        onSuccess: (String?) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val texto =etReview.text.toString()

        val resenha =  Review(texto, rating.rating)

        mDbRef.child("reviews").child(uid).child(movieId).setValue(resenha).addOnSuccessListener {
            Toast.makeText(context, R.string.saveReview, Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(context, R.string.errorSaveReview, Toast.LENGTH_SHORT).show()
        }
    }
}