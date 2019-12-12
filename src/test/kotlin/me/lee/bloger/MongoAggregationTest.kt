package me.lee.bloger

import com.mongodb.client.model.Aggregates
import com.mongodb.client.model.Projections
import me.lee.bloger.article.Article
import org.bson.Document
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.aggregation.Aggregation
import org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation

@SpringBootTest
class MongoAggregationTest(@Autowired val mongoTemplate: ReactiveMongoTemplate) {


    /**
     * db.grades.aggregate(
     *   [
     *     { $project:
     *       { adjustedGrades:
     *         {
     *           $map:
     *             {
     *               input: "$quizzes",
     *               as: "grade",
     *               in: { $add: [ "$$grade", 2 ] }
     *             }
     *           }
     *        }
     *      }
     *    ]
     * )
     */

    @Test
    fun mapTest() {

        val mapDocument = Document()
        mapDocument["input"] = "\$createTime"
        mapDocument["in"] = Document("\$substr", arrayOf("\$createTime", 0, 2))

        val aggregation = newAggregation(Article::class.java,
                Aggregation.project("title", "createTime")
                        .andExpression("\$map", mapDocument)
                        .`as`("createTime"))
        mongoTemplate.aggregate(aggregation, Map::class.java)
        val agg = mongoTemplate.aggregate(aggregation, Map::class.java)
        println(aggregation)
        println(agg.blockFirst())


//
//        val aggregation =
//                Aggregates.project(
//                        Projections.fields(
//                                Projections.include("title", "createTime"),
//                                Projections.computed("mappedCreateTime",
//                                        Document("\$map", Document()))
//                        )
//                )
//        mongoTemplate.getCollection("articles")
//                .aggregate(arrayListOf(aggregation))
    }

}