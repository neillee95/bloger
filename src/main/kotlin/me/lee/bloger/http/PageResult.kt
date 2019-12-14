package me.lee.bloger.http

import java.io.Serializable

data class PageResult(val current: Long,
                      val total: Long,
                      val results: Iterable<*>) : Serializable