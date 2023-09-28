package com.jetbrains.handson.kmm.shared

import com.jetbrains.handson.kmm.shared.cache.Database
import com.jetbrains.handson.kmm.shared.cache.DatabaseDriverFactory
import com.jetbrains.handson.kmm.shared.entity.NYCSchool
import com.jetbrains.handson.kmm.shared.network.NYCSchoolApi

class NYCSchoolSDK (databaseDriverFactory: DatabaseDriverFactory) {
    private val database = Database(databaseDriverFactory)
    private val api = NYCSchoolApi()

    @Throws(Exception::class)
    suspend fun getNYCSchoolNames(forceReload: Boolean): List<NYCSchool> {
        val cachedNYCSchoolNames = database.getAllNYCSchoolNames()
        return if (cachedNYCSchoolNames.isNotEmpty() && !forceReload) {
            cachedNYCSchoolNames
        } else {
            api.getAllNYCSchoolNames().also {
                database.clearDatabase()
                database.createNYCSchoolData(it)
            }
        }
    }
}