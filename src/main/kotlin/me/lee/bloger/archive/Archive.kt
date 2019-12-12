package me.lee.bloger.archive

import java.io.Serializable

data class Archive(val timePoint: String,
                   val articles: Collection<MutableMap<Any?, Any?>>) : Serializable