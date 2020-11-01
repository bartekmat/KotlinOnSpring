package com.gruzini.rest

import com.gruzini.entities.User
import com.gruzini.repositories.ArticleRepository
import com.gruzini.repositories.UserRepository
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import com.ninjasquad.springmockk.MockkBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest
class UserRestApiTest (@Autowired val mockMvc: MockMvc) {

    @MockkBean
    private lateinit var userRepository : UserRepository

    @MockkBean
    private lateinit var articleRepository: ArticleRepository

    @Test
    fun `List all users` (){
        val user1 = User(login = "boyan", firstname = "Michael", lastname = "Boyan")
        val user2 = User(login = "hadi_hariri", firstname = "Hadi", lastname = "Hariri")

        every { userRepository.findAll() } returns listOf(user1, user2)

        mockMvc.perform(get("/api/user/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("\$.[0].login").value(user1.login))
                .andExpect(jsonPath("\$.[0].firstname").value(user1.firstname))
                .andExpect(jsonPath("\$.[0].lastname").value(user1.lastname))
                .andExpect(jsonPath("\$.[1].login").value(user2.login))
                .andExpect(jsonPath("\$.[1].firstname").value(user2.firstname))
                .andExpect(jsonPath("\$.[1].lastname").value(user2.lastname))
    }

    @Test
    fun `Return single user data` (){
        val user1 = User(login = "boyan", firstname = "Michael", lastname = "Boyan")

        every { userRepository.findByLogin(user1.login) } returns user1

        mockMvc.perform(get("/api/user/${user1.login}").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("\$.login").value(user1.login))
                .andExpect(jsonPath("\$.firstname").value(user1.firstname))
                .andExpect(jsonPath("\$.lastname").value(user1.lastname))
    }

    @Test
    fun `404 if user not found` (){
        every { userRepository.findByLogin("login") } returns null

        mockMvc.perform(get("/api/user/login").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound)
    }

}