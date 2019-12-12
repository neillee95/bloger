package me.lee.bloger.comment

import org.springframework.web.bind.annotation.RestController

@RestController
class CommentController(val commentService: CommentService) {
}