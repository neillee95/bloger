package me.lee.bloger.comment

import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface CommentRepository : ReactiveMongoRepository<Comment, String>