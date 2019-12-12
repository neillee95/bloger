package me.lee.bloger.http

class Response {

    companion object {

        fun success(data: Any? = null): HttpResponse =
                HttpResponse(HttpResponseCode.SUCCESS, data)

        fun fail(data: Any? = null): HttpResponse =
                HttpResponse(data = data)

    }

}