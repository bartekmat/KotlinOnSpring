package com.gruzini.rest

import com.gruzini.entities.Article
import com.gruzini.repositories.ArticleRepository
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/article")
class ArticleRestController(private val repository: ArticleRepository) {

    @GetMapping("/")
    fun findAll() = repository.findAllByOrderByAddedAtDesc()

    @GetMapping("/{slug}")
    fun findOne(@PathVariable slug: String): Article {
        return repository.findBySlug(slug)
                ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Such an article does not exist")
    }
}