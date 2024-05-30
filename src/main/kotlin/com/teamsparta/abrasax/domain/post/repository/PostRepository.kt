package com.teamsparta.abrasax.domain.post.repository

import com.teamsparta.abrasax.domain.post.model.Post
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.*

@Repository
interface PostRepository : JpaRepository<Post, Long> {
    fun findPostByIdAndDeletedAtIsNull(id: Long): Optional<Post>
    fun findByStringifiedTagsEqualsAndCreatedAtBeforeAndDeletedAtIsNull(
        tag: String,
        cursorCreatedAt: LocalDateTime,
        pageable: Pageable
    ): List<Post>

    fun findByCreatedAtBeforeAndDeletedAtIsNull(cursorCreatedAt: LocalDateTime, pageable: Pageable): List<Post>
}

