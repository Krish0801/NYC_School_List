package com.example.nycschoolportal

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform