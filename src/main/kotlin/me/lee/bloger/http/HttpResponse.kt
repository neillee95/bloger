package me.lee.bloger.http

import java.io.Serializable

data class HttpResponse(val code: Int = HttpResponseCode.FAIL,
                        val data: Any? = null) : Serializable
