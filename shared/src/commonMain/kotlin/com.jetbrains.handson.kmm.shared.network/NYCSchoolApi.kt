package com.jetbrains.handson.kmm.shared.network

import com.jetbrains.handson.kmm.shared.entity.NYCSchool
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class NYCSchoolApi {
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                useAlternativeNames = false
            })
        }
    }
    suspend fun getAllNYCSchoolNames(): List<NYCSchool> {
        return httpClient.get("https://data.cityofnewyork.us/resource/s3k6-pzi2.json").body()
    }
}