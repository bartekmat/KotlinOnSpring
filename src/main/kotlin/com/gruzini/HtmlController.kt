package com.gruzini

import com.gruzini.entities.Article
import com.gruzini.entities.User
import com.gruzini.repositories.ArticleRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.server.ResponseStatusException

@Controller
class HtmlController(private val repository: ArticleRepository) {

    @GetMapping("/")
    fun blog(model : Model) : String {
        model["title"] = "Kotlin Blog"
        //alternatively
        //model.addAttribute("title", "Kotlin Blog")
        model["articles"] = repository.findAllByOrderByAddedAtDesc().map { it.render() }
        return "blog"
    }

    @GetMapping("/article/{slug}")
    fun article(@PathVariable slug: String, model: Model): String {
        val foundArticle = repository.findBySlug(slug)?.render()?:throw ResponseStatusException(HttpStatus.NOT_FOUND, "Such article does not exist")
        model["title"] = foundArticle.title
        model["article"] = foundArticle
        return "article"
    }

    fun Article.render(): RenderedArticle {
        return RenderedArticle(
                slug,
                title,
                headline,
                content,
                author,
                addedAt.format()
        )
    }

    data class RenderedArticle(
            val slug: String,
            val title: String,
            val headline: String,
            val content: String,
            val author: User,
            val addedAt: String
    )
}