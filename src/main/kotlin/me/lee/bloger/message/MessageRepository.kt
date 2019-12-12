package me.lee.bloger.message

import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface MessageRepository : ReactiveMongoRepository<Message, String>