package com.gruzini.rest

import com.gruzini.entities.Article
import com.gruzini.entities.User
import com.gruzini.repositories.ArticleRepository
import com.gruzini.repositories.UserRepository
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest
class ArticleRestApiTest(@Autowired val mockMvc: MockMvc) {

    @MockkBean
    private lateinit var articleRepository: ArticleRepository

    @MockkBean
    private lateinit var userRepository: UserRepository //why is this absolutely necessary here? I dont know but it crashes without it

    @Test
    fun `List articles`() {
        val user = User(login = "bartekmat", firstname = "Bartek", lastname = "Matuszewski")
        val article1 = Article(title = "Title", content = "Blabla", author = user, headline = "Headline1")
        val article2 = Article(title = "Title wey", content = "lolololo", author = user, headline = "Headline2")

        every { articleRepository.findAllByOrderByAddedAtDesc() } returns listOf(article1, article2)

        mockMvc.perform(get("/api/article/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("\$.[0].author.login").value(user.login))
                .andExpect(jsonPath("\$.[0].slug").value(article1.slug))
                .andExpect(jsonPath("\$.[0].content").value(article1.content))
                .andExpect(jsonPath("\$.[1].author.login").value(user.login))
                .andExpect(jsonPath("\$.[1].slug").value(article2.slug))
                .andExpect(jsonPath("\$.[1].content").value(article2.content))
    }

    @Test
    fun `Return one article`() {
        val user = User(login = "bartekmat", firstname = "Bartek", lastname = "Matuszewski")
        val article1 = Article(title = "Title", content = "Blabla", author = user, headline = "Headline1")

        every { articleRepository.findBySlug(article1.slug) } returns article1

        mockMvc.perform(get("/api/article/${article1.slug}"))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("\$.author.login").value(user.login))
                .andExpect(jsonPath("\$.title").value(article1.title))
                .andExpect(jsonPath("\$.content").value(article1.content))
    }

    @Test
    fun `404 if not found` (){
        every { articleRepository.findBySlug("slug") } returns null

        mockMvc.perform(get("/api/article/slug").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound)
    }
}