package com.teamsparta.abrasax.domain.post.service

import com.teamsparta.abrasax.domain.exception.DeleteNotAllowedException
import com.teamsparta.abrasax.domain.exception.MemberNotFoundException
import com.teamsparta.abrasax.domain.exception.ModelNotFoundException
import com.teamsparta.abrasax.domain.helper.ListStringifyHelper
import com.teamsparta.abrasax.domain.member.repository.MemberRepository
import com.teamsparta.abrasax.domain.post.comment.model.toCommentResponseDto
import com.teamsparta.abrasax.domain.post.comment.repository.CommentRepository
import com.teamsparta.abrasax.domain.post.dto.CreatePostRequestDto
import com.teamsparta.abrasax.domain.post.dto.PostResponseDto
import com.teamsparta.abrasax.domain.post.dto.PostResponseWithCommentDto
import com.teamsparta.abrasax.domain.post.dto.UpdatePostRequestDto
import com.teamsparta.abrasax.domain.post.model.Post
import com.teamsparta.abrasax.domain.post.model.toPostResponseDto
import com.teamsparta.abrasax.domain.post.model.toPostWithCommentDtoResponse
import com.teamsparta.abrasax.domain.post.repository.PostRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class PostService(
    private val postRepository: PostRepository,
    private val commentRepository: CommentRepository,
    private val memberRepository: MemberRepository,
) {
//    fun getPosts(): List<PostResponseDto> {
//        return postRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt")).map { it.toPostResponseDto() }
//    }

    fun getPosts(createdAt: LocalDateTime, pageNumber: Int, pageSize: Int): List<PostResponseDto> {
        val pageable = PageRequest.of(pageNumber, pageSize)
        return postRepository.findByCreatedAtBeforeOrderByCreatedAtDesc(createdAt, pageable)
            .map { it.toPostResponseDto() }
    }


    fun getPostById(id: Long): PostResponseWithCommentDto {

        val post = postRepository.findByIdOrNull(id) ?: throw ModelNotFoundException("Post", id)
        val comments = commentRepository.findAllByPostId(id).map { it.toCommentResponseDto() }

        return post.toPostWithCommentDtoResponse(comments)
    }

    fun getPostsByTag(tag: String): List<PostResponseDto> {
        val post = postRepository.findAllByStringifiedTagsLike(tag)

        return post.map { it.toPostResponseDto() }
    }

    @Transactional
    fun createPost(request: CreatePostRequestDto): PostResponseDto {
        val (title, content, tags, authorId) = request
        val member = memberRepository.findByIdOrNull(authorId)
            ?: throw MemberNotFoundException(authorId)
        val createdAt = LocalDateTime.now()

        val post =
            Post(
                title = title,
                content = content,
                stringifiedTags = ListStringifyHelper.stringifyList(tags),
                member = member,
                createdAt = createdAt,
                updatedAt = createdAt,
            )

        return postRepository.save(post).toPostResponseDto()
    }

    @Transactional
    fun updatePost(id: Long, request: UpdatePostRequestDto): PostResponseDto {
        val (title, content, tags) = request
        val post = postRepository.findByIdOrNull(id) ?: throw ModelNotFoundException("Post", id)

        post.update(title, content, tags)
        return post.toPostResponseDto()
    }

    @Transactional
    fun deletePost(id: Long) {
        val post =
            postRepository.findPostByIdAndDeletedAtIsNull(id).orElseThrow { DeleteNotAllowedException("post", id) }

        post.delete()
        postRepository.save(post)
    }
}
