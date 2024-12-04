package com.example.uf1_proyecto.dao

interface IDao {
    fun getReview(uid: String, movieId: String, onSuccess: (String?) -> Unit, onFailure: (Exception) -> Unit)

    fun saveReview(uid: String, movieId: String, review: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit)
}
