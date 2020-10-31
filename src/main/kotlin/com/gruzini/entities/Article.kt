package com.gruzini.entities

import com.gruzini.toSlug
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class Article (
        @Id @GeneratedValue var id: Long? = null,
        var title: String,
        var headline : String,
        var content : String,
        @ManyToOne var author : User,
        var slug: String = title.toSlug(),
        var addedAt: LocalDateTime = LocalDateTime.now()
)