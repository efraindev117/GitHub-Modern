package com.android.domain.repository


interface IUsersRepository {
    suspend fun getUsers()
}