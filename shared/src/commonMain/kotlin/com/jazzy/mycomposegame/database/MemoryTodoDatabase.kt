package com.jazzy.mycomposegame.database

class MemoryTodoDatabase : GameDatabase<GameEntity.Data> {

    private val map = hashMapOf<String, GameEntity.Data>()

    override fun get(id: String): GameEntity.Data? = map[id]

    override fun create(data: GameEntity.Data): GameEntity.Data {
        val id = data.toString()
        val item = GameEntity(id = id, data = data)
        map[id] = data

        return data
    }

    override fun save(id: String, data: GameEntity.Data) {
        map[id] = data
    }

    override fun delete(id: String) {
        map.remove(id)
    }

    override fun getAll(): List<Pair<String, GameEntity.Data>> {
           return map.entries.map { it.key to it.value }
    }
}