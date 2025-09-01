package org.example

import io.dropwizard.core.Application
import io.dropwizard.core.setup.Environment
import io.dropwizard.jdbi3.JdbiFactory
import org.example.dao.UserDao
import org.example.resource.AuthResource
import org.example.service.UserService
import jakarta.servlet.DispatcherType
import org.eclipse.jetty.servlets.CrossOriginFilter
import java.util.EnumSet

class AttendanceApplication : Application<AttendanceConfiguration>() {
    override fun run(configuration: AttendanceConfiguration, environment: Environment) {
        // JDBI setup
        val factory = JdbiFactory()
        val jdbi = factory.build(environment, configuration.database, "postgresql")

        // DAOs
        val userDao = UserDao(jdbi)

        // Services
        val userService = UserService(userDao)

        // Resources
        environment.jersey().register(AuthResource(userService))

        val cors = environment.servlets().addFilter("CORS", CrossOriginFilter::class.java)

        cors.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*") // allow all origins (change to your frontend origin for security)
        cors.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM, "X-Requested-With,Content-Type,Accept,Origin,Authorization")
        cors.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "OPTIONS,GET,PUT,POST,DELETE,HEAD")

        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType::class.java), true, "/*")
    }
}

fun main(args: Array<String>) {
    AttendanceApplication().run(*args)
}
