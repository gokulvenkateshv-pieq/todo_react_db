package org.example.dao

import org.example.model.User
import org.jdbi.v3.core.Jdbi

class UserDao(private val jdbi: Jdbi) {

    fun findByUsername(username: String): User? {
        return jdbi.withHandle<User?, Exception> { handle ->
            handle.createQuery(
                "SELECT id, username, password FROM users WHERE username = :username"
            )
                .bind("username", username)
                .mapToBean(User::class.java)
                .findOne()
                .orElse(null)
        }
    }
}

















//package org.example.dao
//
//import org.example.model.User
//import org.jdbi.v3.core.Jdbi
//
//class UserDao(private val jdbi: Jdbi) {
//
//    fun findByUsername(username: String): User? {
//        return jdbi.withHandle<User?, Exception> { handle ->
//            handle.createQuery("SELECT * FROM users WHERE username = :username")
//                .bind("username", username)
//                .mapToBean(User::class.java)
//                .findOne()
//                .orElse(null)
//        }
//    }
//}

