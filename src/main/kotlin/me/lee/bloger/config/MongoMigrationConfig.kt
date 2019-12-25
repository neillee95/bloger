package me.lee.bloger.config

import com.github.mongobee.Mongobee
import com.mongodb.MongoClient
import me.lee.bloger.migration.Package
import org.springframework.boot.autoconfigure.mongo.MongoProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.MongoTemplate

@Configuration
class MongoMigrationConfig {

    @Bean
    fun mongobee(mongoClient: MongoClient,
                 mongoTemplate: MongoTemplate,
                 mongoProperties: MongoProperties): Mongobee {
        val mongobee = Mongobee(mongoClient)
        mongobee.setDbName(mongoProperties.database)
        mongobee.setChangelogCollectionName("_changelog")
        mongobee.setChangeLogsScanPackage(Package::class.java.`package`.name)
        mongobee.setMongoTemplate(mongoTemplate)
        return mongobee
    }

}