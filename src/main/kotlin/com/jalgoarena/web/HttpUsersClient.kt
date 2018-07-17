package com.jalgoarena.web

import com.jalgoarena.domain.User
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestOperations

@Service
class HttpUsersClient(
        @Autowired private val restTemplate : RestOperations
) : UsersClient {

    @Value("\${jalgoarena.api.url}")
    private lateinit var jalgoarenaApiUrl: String

    override fun findUser(token: String) = handleExceptions(returnOnException = null) {
        val headers = HttpHeaders().apply {
            set("X-Authorization", token)
        }

        val entity = HttpEntity<HttpHeaders>(headers)

        val response = restTemplate.exchange(
                "$jalgoarenaApiUrl/auth/api/user", HttpMethod.GET, entity, User::class.java)
        response.body
    }

    private fun <T> handleExceptions(returnOnException: T, body: () -> T) = try {
        body()
    } catch(e: Exception) {
        LOG.error("Error in querying jalgoarena auth service - $jalgoarenaApiUrl", e)
        returnOnException
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(HttpUsersClient::class.java)
    }
}
