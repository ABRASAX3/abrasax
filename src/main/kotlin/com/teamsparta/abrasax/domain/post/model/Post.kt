package com.teamsparta.abrasax.domain.post.model

import com.teamsparta.abrasax.domain.post.comment.dto.CommentResponseDto
import com.teamsparta.abrasax.domain.post.dto.PostResponseDto
import com.teamsparta.abrasax.domain.post.dto.PostResponseWithCommentDto
import jakarta.persistence.*

@Entity
@Table(name = "post")
class Post(
    @Column(name = "title", nullable = false)
    var title: String,

    @Column(name = "content", nullable = false)
    var content: String,

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "author_id", nullable = false)
    @Column(name = "author_id")
    val authorId: Long, //Todo: Member model로 바꾸기

    @Column(name = "tags", nullable = false)
    var stringifiedTags: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    fun update(newTitle: String, newContent: String, newTags: List<String>) {
        title = newTitle
        content = newContent
        stringifiedTags = stringifyTags(newTags)
    }

    companion object {
        fun stringifyTags(tags: List<String>): String {
            return tags.joinToString(",") { it }
        }

        fun parseToTags(stringifiedTags: String): List<String> {
            return if (stringifiedTags == "") listOf() else stringifiedTags.split(",")
        }
    }
}

fun Post.toPostResponseDto(): PostResponseDto {
    return PostResponseDto(
        id = id!!,
        title = title,
        content = content,
        tags = Post.parseToTags(stringifiedTags),
        authorId = authorId
    )
}

fun Post.toPostWithCommentDtoResponse(
    commentResponseDto: List<CommentResponseDto>
): PostResponseWithCommentDto {

    return PostResponseWithCommentDto(

        id = id!!,
        title = title,
        content = content,
        authorId = authorId,
        tags = Post.parseToTags(stringifiedTags),
        comments = commentResponseDto

    )
}