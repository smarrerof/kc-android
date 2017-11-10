package com.sergiomarrero.dishr.common

import java.net.URL

class JsonRequest(private val url: String) {
    fun run(): String {
        val json = URL(url).readText()
        return json
    }
}