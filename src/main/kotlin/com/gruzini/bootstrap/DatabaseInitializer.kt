package com.gruzini.bootstrap

import com.gruzini.entities.Article
import com.gruzini.entities.User
import com.gruzini.repositories.ArticleRepository
import com.gruzini.repositories.UserRepository
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DatabaseInitializer(private val articleRepository: ArticleRepository, private val userRepository: UserRepository) {

    @Bean
    fun databaseInitialization () = ApplicationRunner {
        val kolton = userRepository.save(User(login = "r_kolton", firstname = "Roman", lastname = "Kolton"))
        articleRepository.save(Article(title = "Prawda Futbolu", headline = "Lorem ipsum", content = "kazdy ma swoja prawde futbolu", author = kolton))
        articleRepository.save(Article(title = "Brzeczek musi odejsc", headline = "Taka jest prawda futbolu", content = "kazdy ma swoja prawde futbolu, ale ta jest moja", author = kolton))
    }
}