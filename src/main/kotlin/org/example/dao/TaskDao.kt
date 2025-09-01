package org.example.dao

import org.example.model.Task
import org.jdbi.v3.core.Jdbi

class TaskDao(private val jdbi: Jdbi) {

    fun createTable() {
        jdbi.useHandle<Exception> { handle ->
            handle.execute(
                """
                CREATE TABLE IF NOT EXISTS tasks (
                    id SERIAL PRIMARY KEY,
                    text VARCHAR NOT NULL,
                    description VARCHAR,
                    completed BOOLEAN DEFAULT false
                )
                """
            )
        }
    }

    fun getAll(): List<Task> {
        return jdbi.withHandle<List<Task>, Exception> { handle ->
            handle.createQuery("SELECT * FROM tasks")
                .mapTo(Task::class.java)
                .list()
        }
    }

    fun findById(id: Int): Task? {
        return jdbi.withHandle<Task?, Exception> { handle ->
            handle.createQuery("SELECT * FROM tasks WHERE id = :id")
                .bind("id", id)
                .mapTo(Task::class.java)
                .findOne()
                .orElse(null)
        }
    }

    fun insert(task: Task): Int {
        return jdbi.withHandle<Int, Exception> { handle ->
            handle.createUpdate(
                "INSERT INTO tasks (text, description, completed) VALUES (:text, :description, :completed)"
            )
                .bind("text", task.text)
                .bind("description", task.description)
                .bind("completed", task.completed)
                .executeAndReturnGeneratedKeys("id")
                .mapTo(Int::class.java)
                .one()
        }
    }

    fun update(task: Task): Int {
        return jdbi.withHandle<Int, Exception> { handle ->
            handle.createUpdate(
                "UPDATE tasks SET text = :text, description = :description, completed = :completed WHERE id = :id"
            )
                .bind("id", task.id)
                .bind("text", task.text)
                .bind("description", task.description)
                .bind("completed", task.completed)
                .execute()
        }
    }

    fun delete(id: Int): Int {
        return jdbi.withHandle<Int, Exception> { handle ->
            handle.createUpdate("DELETE FROM tasks WHERE id = :id")
                .bind("id", id)
                .execute()
        }
    }
}
