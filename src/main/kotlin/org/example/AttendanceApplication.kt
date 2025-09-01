package org.example

import io.dropwizard.core.Application
import io.dropwizard.core.setup.Environment
import io.dropwizard.jdbi3.JdbiFactory
import org.example.dao.UserDao
import org.example.dao.TaskDao
import org.example.resource.AuthResource
import org.example.resource.TaskResource
import org.example.service.UserService
import jakarta.servlet.DispatcherType
import org.eclipse.jetty.servlets.CrossOriginFilter
import org.jdbi.v3.core.kotlin.KotlinPlugin
import java.util.EnumSet

class AttendanceApplication : Application<AttendanceConfiguration>() {
    override fun run(configuration: AttendanceConfiguration, environment: Environment) {
        // JDBI setup
        val factory = JdbiFactory()
        val jdbi = factory.build(environment, configuration.database, "postgresql")

        // Install Kotlin plugin for constructor mapping
        jdbi.installPlugin(KotlinPlugin())

        // DAOs
        val userDao = UserDao(jdbi)
        val taskDao = TaskDao(jdbi)  // ✅ instantiate class directly

        // Create tables if not exist
        taskDao.createTable()

        // Services
        val userService = UserService(userDao)

        // Resources
        environment.jersey().register(AuthResource(userService))
        environment.jersey().register(TaskResource(taskDao))

        // Enable CORS
        val cors = environment.servlets().addFilter("CORS", CrossOriginFilter::class.java)
        cors.setInitParameter(
            CrossOriginFilter.ALLOWED_ORIGINS_PARAM,
            "*" // For production, restrict this to your frontend URL
        )
        cors.setInitParameter(
            CrossOriginFilter.ALLOWED_HEADERS_PARAM,
            "X-Requested-With,Content-Type,Accept,Origin,Authorization"
        )
        cors.setInitParameter(
            CrossOriginFilter.ALLOWED_METHODS_PARAM,
            "OPTIONS,GET,PUT,POST,DELETE,HEAD"
        )
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType::class.java), true, "/*")
    }
}

fun main(args: Array<String>) {
    AttendanceApplication().run(*args)
}
