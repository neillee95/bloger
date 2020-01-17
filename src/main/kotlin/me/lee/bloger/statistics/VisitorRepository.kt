package me.lee.bloger.statistics

import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface VisitorRepository : ReactiveMongoRepository<Visitor, String>