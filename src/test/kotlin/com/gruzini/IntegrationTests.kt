package com.gruzini

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpStatus

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTests(@Autowired val restTemplate: TestRestTemplate) {

    @BeforeAll
    fun setupTests(){
        println(">> Setting up all tests")
    }

    @Test
    fun `Assert blog page title, content and status code`() {
        val entity = restTemplate.getForEntity<String>("/")
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(entity.body).contains("<h1>Kotlin Blog</h1>", "Prawda")
    }
    @Test
    fun `Assert article page title, content and status code`(){
        val title = "Prawda Futbolu"
        val page = restTemplate.getForEntity<String>("/article/${title.toSlug()}")
        assertThat(page.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(page.body).contains(title, "kazdy ma swoja prawde futbolu")
    }
    @Test
    fun `Assert article page status code for non existing article` (){
        val page = restTemplate.getForEntity<String>("/article/xyz")
        assertThat(page.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
    }

    @AfterAll
    fun teardown(){
        println(">> Tearing down after test execution")
    }

}