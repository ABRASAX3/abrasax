package com.teamsparta.abrasax.domain.post.controller

import com.teamsparta.abrasax.domain.post.dto.CreatePostRequestDto
import com.teamsparta.abrasax.domain.post.dto.PostResponseDto
import com.teamsparta.abrasax.domain.post.dto.PostResponseWithCommentDto
import com.teamsparta.abrasax.domain.post.dto.UpdatePostRequestDto
import com.teamsparta.abrasax.domain.post.service.PostService
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/posts")
class PostController(private val postService: PostService) {
//    @GetMapping
//    fun getPosts(): ResponseEntity<List<PostResponseDto>> {
//        return ResponseEntity.status(HttpStatus.OK).body(postService.getPosts())
//    }

    @GetMapping
    fun getPosts(
        @RequestParam(required = false) cursorCreatedAt: LocalDateTime?,
        @RequestParam pageNumber: Int,
        @RequestParam pageSize: Int,
        @RequestParam sortDirection: String
    ): ResponseEntity<List<PostResponseDto>> {
        val direction = Sort.Direction.valueOf(sortDirection.uppercase())
        val cursor = cursorCreatedAt ?: postService.getCurrentTime()
        val posts = postService.getPosts(cursor, pageNumber, pageSize, direction)
        return ResponseEntity.status(HttpStatus.OK).body(posts)
    }

    @GetMapping("/{postId}")
    fun getPostById(@PathVariable("postId") id: Long): ResponseEntity<PostResponseWithCommentDto> {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPostById(id))
    }

    @GetMapping("/tag")
    fun getPostsByTag(
        @RequestParam tag: String,
        @RequestParam(required = false) cursorCreatedAt: LocalDateTime?,
        @RequestParam pageNumber: Int,
        @RequestParam pageSize: Int,
        @RequestParam sortDirection: String,
    ): ResponseEntity<List<PostResponseDto>> {
        val direction = Sort.Direction.valueOf(sortDirection.uppercase())
        val cursor = cursorCreatedAt ?: postService.getCurrentTime()
        val posts = postService.getPostsByTag(tag, cursor, pageNumber, pageSize, direction)
        return ResponseEntity.status(HttpStatus.OK).body(posts)
    }

    @PostMapping
    fun createPost(@RequestBody createPostRequestDto: CreatePostRequestDto): ResponseEntity<PostResponseDto> {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(createPostRequestDto))
    }

    @PutMapping("/{postId}")
    fun updatePost(
        @PathVariable("postId") id: Long,
        @RequestBody updatePostRequestDto: UpdatePostRequestDto
    ): ResponseEntity<PostResponseDto> {
        return ResponseEntity.status(HttpStatus.OK).body(postService.updatePost(id, updatePostRequestDto))
    }

    @DeleteMapping("/{postId}")
    fun deletePost(@PathVariable("postId") id: Long): ResponseEntity<Unit> {
        return ResponseEntity.status(HttpStatus.OK).body(postService.deletePost(id))
    }


}