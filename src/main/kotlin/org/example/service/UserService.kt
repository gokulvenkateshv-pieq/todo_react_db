package org.example.service

import org.example.dao.UserDao
import org.example.dto.LoginResponse

class UserService(private val userDao: UserDao) {

    fun login(username: String, password: String): LoginResponse {
        if (username.isBlank() || password.isBlank()) {
            return LoginResponse(false, "Username or password cannot be empty")
        }

        val user = userDao.findByUsername(username)
        return if (user != null && user.password == password) {
            LoginResponse(true, "Login successful", user.username)
        } else {
            LoginResponse(false, "Invalid username or password")
        }
    }
}
