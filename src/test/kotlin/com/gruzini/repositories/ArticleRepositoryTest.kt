package com.gruzini.repositories

import com.gruzini.entities.Article
import com.gruzini.entities.User
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.repository.findByIdOrNull

@DataJpaTest
class ArticleRepositoryTest @Autowired constructor(
        val entityManager: TestEntityManager,
        val articleRepository: ArticleRepository
) {

    @AfterEach
    fun teardown() {
        entityManager.clear()
    }

    @Test
    fun `When findByIdOrNull then return article`() {
        val user = User(login = "bartekmat", firstname = "Bartek", lastname = "Matuszewski")
        entityManager.persist(user)
        val article = Article(title = "Title wey", content = "Blabla", author = user, headline = "Headline")
        entityManager.persist(article)
        entityManager.flush()
        //now article sits in db, everything setup
        val foundArticle = articleRepository.findByIdOrNull(article.id!!)
        println(article.slug)
        assertThat(foundArticle).isEqualTo(article)
    }

    @Test
    fun `When article not found return null`() {
        //nothing saved
        val foundArticle = articleRepository.findByIdOrNull(2)
        assertThat(foundArticle).isNull()
    }

    @Test
    fun `When findBySlug then return article`() {
        val user = User(login = "bartekmat", firstname = "Bartek", lastname = "Matuszewski")
        entityManager.persist(user)
        val article = Article(title = "Title wey", content = "Blabla", author = user, headline = "Headline")
        entityManager.persist(article)
        entityManager.flush()
        //now article sits in db, everything setup
        val foundArticle = articleRepository.findBySlug("title-wey")
        assertThat(foundArticle).isNotNull
        assertThat(foundArticle).isEqualTo(article)
    }

    @Test
    fun `When findAllByOrderByAddedAtDesc then return ordered list`() {
        val user = User(login = "bartekmat", firstname = "Bartek", lastname = "Matuszewski")
        entityManager.persist(user)
        val article1 = Article(title = "Title wey", content = "Blabla", author = user, headline = "Headline")
        val article2 = Article(title = "Hello World", content = "Blabla", author = user, headline = "Headline")
        val article3 = Article(title = "Hey+_+People", content = "Blabla", author = user, headline = "Headline")
        entityManager.persist(article1)
        entityManager.persist(article3)
        entityManager.persist(article2)
        entityManager.flush()
        //now articles sit in db, everything setup
        val foundArticles = articleRepository.findAllByOrderByAddedAtDesc()
        assertThat(foundArticles).isNotNull
        assertThat(foundArticles.count()).isEqualTo(3)
        assertThat(foundArticles.first()).isEqualTo(article1)
        assertThat(foundArticles.last()).isEqualTo(article2)
    }
}