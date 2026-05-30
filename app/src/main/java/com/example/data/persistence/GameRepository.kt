package com.example.data.persistence

import com.example.domain.GameData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class GameRepository(private val dao: GameSaveDao) {
    private val json = Json { ignoreUnknownKeys = true }

    fun getGameData(): Flow<GameData?> {
        return dao.getSave("primary_save").map { entity ->
            entity?.jsonData?.let { json.decodeFromString<GameData>(it) }
        }
    }

    suspend fun saveGameData(gameData: GameData) {
        val jsonData = json.encodeToString(gameData)
        dao.insertSave(GameSaveEntity(jsonData = jsonData))
    }

    suspend fun clearSave() {
        dao.deleteSave("primary_save")
    }
}
