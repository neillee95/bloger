package me.lee.bloger.metrics

import java.io.Serializable

data class Metrics(val messages: Long,
                   val articles: Long,
                   val commits: Long,
                   val visitors: Long) : Serializable