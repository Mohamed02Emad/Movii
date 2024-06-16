package com.mo.movie.core.local.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.mo.movie.MyDataBase

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(
            schema = MyDataBase.Schema,
            name = "MyDataBase.db"
        )
    }
}