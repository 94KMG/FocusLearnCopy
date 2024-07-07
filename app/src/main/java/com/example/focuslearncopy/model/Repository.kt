package com.example.focuslearn.model

import com.google.firebase.firestore.auth.User

interface Repository {
    suspend fun getUserByUsername(username: String): com.example.focuslearn.model.User?
    suspend fun signIn(email: String, password: String): Boolean
    suspend fun fetchEmployees(): List<TrainingEmployee>
    suspend fun addEmployee(employee: TrainingEmployee): Boolean
}
