package me.lee.bloger.extension

import org.bson.Document
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.BasicQuery
import reactor.core.publisher.Flux

fun <T> ReactiveMongoTemplate.find(queryObject: Document = Document(),
                                   selectedFields: Array<String>,
                                   entityClass: Class<*>,
                                   resultClass: Class<T>): Flux<T> {
    val fieldObject = Document().also { document ->
        selectedFields.forEach {
            document.append(it, true)
        }
    }
    val collectionName = this.getCollectionName(entityClass)
    val query = BasicQuery(queryObject, fieldObject)
    return find(query, resultClass, collectionName)
}