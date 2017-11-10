package com.sergiomarrero.dishr.common

import java.net.URL

class JsonRequest() {

    private val API_URL = "https://dishrapp.firebaseio.com"

    fun run(endpoint: String): String {
        val json = URL("$API_URL/$endpoint.json").readText()
        return json
    }
}