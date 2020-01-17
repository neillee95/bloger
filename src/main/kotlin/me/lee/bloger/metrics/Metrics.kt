package me.lee.bloger.metrics

import java.io.Serializable

data class Metrics(val visitors: Long,
                   val messages: Long,
                   val articles: Long,
                   val comments: Long) : Serializable