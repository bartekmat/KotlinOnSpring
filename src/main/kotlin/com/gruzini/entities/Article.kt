package com.gruzini.entities

import com.gruzini.toSlug
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Article (
        @Id @GeneratedValue var id: Long? = null,
        var title: String,
        var headline : String,
        var content : String,
        @ManyToOne(cascade = [CascadeType.MERGE]) var author : User,
        var slug: String = title.toSlug(),
        var addedAt: LocalDateTime = LocalDateTime.now()
)