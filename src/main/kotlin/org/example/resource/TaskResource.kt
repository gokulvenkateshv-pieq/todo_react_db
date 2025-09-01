package org.example.resource

import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.example.dao.TaskDao
import org.example.model.Task

@Path("/tasks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class TaskResource(private val taskDAO: TaskDao) {

    @GET
    fun getAll(): List<Task> = taskDAO.getAll()

    @GET
    @Path("/{id}")
    fun getById(@PathParam("id") id: Int): Task {
        return taskDAO.findById(id) ?: throw NotFoundException("Task with id $id not found")
    }

    @POST
    fun create(task: Task): Response {
        val id = taskDAO.insert(task)
        val created = task.copy(id = id)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @PUT
    @Path("/{id}")
    fun update(@PathParam("id") id: Int, task: Task): Task {
        val updated = task.copy(id = id)
        val rows = taskDAO.update(updated)
        if (rows == 0) throw NotFoundException("Task with id $id not found")
        return updated
    }

    @DELETE
    @Path("/{id}")
    fun delete(@PathParam("id") id: Int): Response {
        val rows = taskDAO.delete(id)
        if (rows == 0) throw NotFoundException("Task with id $id not found")
        return Response.noContent().build()
    }
}
