package com.example.uf1_proyecto.dao

import android.widget.EditText
import android.widget.RatingBar

interface IDao {
    fun getReview(uid: String, movieId: String, etReview: EditText, rating: RatingBar, onSuccess: (String?) -> Unit, onFailure: (Exception) -> Unit)

    fun saveReview(uid: String, movieId: String, etReview: EditText, rating: RatingBar, onSuccess: (String?) -> Unit, onFailure: (Exception) -> Unit)
}
