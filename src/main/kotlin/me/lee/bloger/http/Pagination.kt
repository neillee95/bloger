package me.lee.bloger.http

import java.io.Serializable

data class Pagination(val page: Long,
                      val size: Long) : Serializable