package me.lee.bloger.system

import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface BlogRepository : ReactiveMongoRepository<Blog, String>