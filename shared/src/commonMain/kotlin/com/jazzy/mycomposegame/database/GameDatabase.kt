package com.jazzy.mycomposegame.database

interface GameDatabase<out Data> {

    fun get(id: String): Data?

    fun create(data: @UnsafeVariance Data): Data

    fun save(id: String, data: @UnsafeVariance Data)

    fun delete(id: String)

    fun getAll(): List<Pair<String, Data>>
}