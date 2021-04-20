package tools

import com.mongodb.client.MongoClients
import me.lee.bloger.utils.Hashing
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.SimpleMongoClientDbFactory
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

fun main(args: Array<String>) {
    if (args.size < 3) {
        error(
            "\nUsage: java -cp <classpath> tools.PasswordUpdater <mongo connection url> <database> <your new password>" +
                    "The format of mongo connect url is mongodb://<username>:<password>@<host>:<port>/<db>"
        )
    }
    val connect = args[0]
    val database = args[1]
    val newPassword = args[2]

    val mongoClient = MongoClients.create(connect)
    val mongoDbFactory = SimpleMongoClientDbFactory(mongoClient, database)
    val mongoTemplate = MongoTemplate(mongoDbFactory)
    val mongoCursor = mongoTemplate.getCollection("user").find().cursor()
    if (mongoCursor.hasNext()) {
        val document = mongoCursor.next()
        println(document)
        document["password"] = BCryptPasswordEncoder().encode(Hashing.sha256().hashString(newPassword))
        mongoTemplate.save(document, "user")
        println("Update successfully!")
    }
}