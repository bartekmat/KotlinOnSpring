package com.gruzini

import com.gruzini.entities.Article
import com.gruzini.entities.User
import com.gruzini.repositories.ArticleRepository
import com.gruzini.repositories.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/article")
class ArticleRestController (private val repository: ArticleRepository) {

    @GetMapping("/")
    fun findAll () = repository.findAll()

    @GetMapping("/{slug}")
    fun findOne (@PathVariable slug: String) : Article {
        return repository.findBySlug(slug)?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Such an article does not exist")
    }
}

@RestController
@RequestMapping("/api/user")
class UserRestController (private val repository: UserRepository) {

    @GetMapping("/")
    fun findAll() = repository.findAll()

    @GetMapping("/{login}")
    fun findOne (@PathVariable login : String) : User {
        return repository.findByLogin(login)?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "User with such a login does not exist")
    }
}
