package me.lee.bloger.extension

import me.lee.bloger.exception.BadRequestException
import me.lee.bloger.http.Pagination
import org.springframework.web.reactive.function.server.ServerRequest

fun ServerRequest.pageParam(defaultPage: Long = 1,
                            defaultSize: Long = 10): Pagination {
    val page = toLong(this.queryParam("page").orElse(defaultPage.toString()))
    val size = toLong(this.queryParam("size").orElse(defaultSize.toString()))
    if (page <= 0 || size <= 0) {
        throw BadRequestException()
    }
    return Pagination(page, size)
}

fun toLong(param: String): Long =
        try {
            param.toLong()
        } catch (e: Exception) {
            throw BadRequestException()
        }