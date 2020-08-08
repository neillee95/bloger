package me.lee.bloger.migration

import com.github.mongobee.changeset.ChangeLog
import com.github.mongobee.changeset.ChangeSet
import me.lee.bloger.security.user.User
import me.lee.bloger.system.Blog
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.util.*

@ChangeLog(order = "001")
class SetupMigration {

    @ChangeSet(id = "init-user", author = "neillee95", order = "001")
    fun initUser(mongoTemplate: MongoTemplate) {
        val passwordEncoder = BCryptPasswordEncoder()
        val password = UUID.randomUUID().toString().replace("-", "").toLowerCase()
        val id = ObjectId().toHexString()
        val user = User(id, "admin", passwordEncoder.encode(password))
        mongoTemplate.save(user)
        println("\n\nInitial user: [ username: admin, password: $password ]\n\n")
    }

    @ChangeSet(id = "init-blog", author = "neillee95", order = "002")
    fun initBlog(mongoTemplate: MongoTemplate) {
        val blog = Blog(name = "Bloger", footer = "One World<br>One Dream", leaveMessage = false)
        mongoTemplate.save(blog)
    }

}