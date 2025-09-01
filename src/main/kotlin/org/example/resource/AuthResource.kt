package org.example.resource

import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.example.dto.LoginRequest
import org.example.service.UserService

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class AuthResource(private val userService: UserService) {

    @POST
    @Path("/login")
    fun login(request: LoginRequest): Response {
        val response = userService.login(request.username, request.password)
        return Response.status(
            if (response.success) Response.Status.OK else Response.Status.UNAUTHORIZED
        ).entity(response).build()
    }
}
