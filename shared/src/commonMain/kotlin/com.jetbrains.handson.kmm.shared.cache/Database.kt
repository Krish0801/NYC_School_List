package com.jetbrains.handson.kmm.shared.cache

import com.jetbrains.handson.kmm.shared.entity.NYCSchool

internal class Database(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = AppDatabase(databaseDriverFactory.createDriver())
    private val dbQuery = database.appDatabaseQueries

    internal fun clearDatabase() {
        dbQuery.transaction {
            dbQuery.removeAllNYCSchoolNames()
        }
    }

    internal fun getAllNYCSchoolNames(): List<NYCSchool> {
        return dbQuery.selectAllNYCSchoolNamesInfo(::mapNYCSchoolSelecting).executeAsList()
    }

    private fun mapNYCSchoolSelecting(
        school_name: String?,
        dbn: String?
    ): NYCSchool {
        return NYCSchool(
            school_name = school_name,
            dbn = dbn
        )
    }

    internal fun createNYCSchoolData(nycSchoolData: List<NYCSchool>) {
        dbQuery.transaction {
            nycSchoolData.forEach { nycSchoolData ->
                insertNYCSchoolData(nycSchoolData)
            }
        }
    }

    private fun insertNYCSchoolData(nycSchoolData: NYCSchool) {
        nycSchoolData.school_name.let {
            nycSchoolData.dbn.let { it1 ->
                dbQuery.insertNYCSchoolNames(
                    school_name = it,
                    dbn = it1
                )
            }
        }
    }
}
