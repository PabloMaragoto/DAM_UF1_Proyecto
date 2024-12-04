package com.example.uf1_proyecto.dao

import android.widget.EditText

interface IDao {
    fun getReview(uid: String, movieId: String, etReview: EditText, onSuccess: (String?) -> Unit, onFailure: (Exception) -> Unit)

    fun saveReview(uid: String, movieId: String, etReview: EditText,onSuccess: (String?) -> Unit, onFailure: (Exception) -> Unit)
}
