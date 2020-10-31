package com.gruzini.repositories

import com.gruzini.entities.User
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager

@DataJpaTest
class UserRepositoryTest @Autowired constructor(
        val entityManager: TestEntityManager,
        val userRepository: UserRepository
) {
    private val NON_EXISITNG_LOGIN = "lololo"

    @Test
    fun `When findByLogin then return User`(){
        val existingUser = User(login = "bartekmat", firstname = "Bartek", lastname = "Matuszewski", description = "some dude")
        entityManager.persist(existingUser)
        entityManager.flush()
        //now user is in test db and everything is set up for the test - we attempt to recover it using getByLogin
        val foundUser = userRepository.findByLogin("bartekmat")
        assertThat(foundUser).isEqualTo(existingUser)
    }

    @Test
    fun `When login not found return null`(){
        val existingUser = User(login = "bartekmat", firstname = "Bartek", lastname = "Matuszewski", description = "some dude")
        entityManager.persist(existingUser)
        entityManager.flush()
        //everything set up
        val foundUser = userRepository.findByLogin(NON_EXISITNG_LOGIN)
        assertThat(foundUser).isNull()
    }
}