package com.gruzini.entities

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class User (
        @Id @GeneratedValue var id: Long? = null,
        var login : String,
        var firstname : String,
        var lastname : String,
        var description : String? = null
)