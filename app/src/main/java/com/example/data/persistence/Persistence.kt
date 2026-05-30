package com.example.data.persistence

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "game_save")
data class GameSaveEntity(
    @PrimaryKey val id: String = "primary_save",
    val jsonData: String
)

@Dao
interface GameSaveDao {
    @Query("SELECT * FROM game_save WHERE id = :id")
    fun getSave(id: String): Flow<GameSaveEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSave(save: GameSaveEntity)

    @Query("DELETE FROM game_save WHERE id = :id")
    suspend fun deleteSave(id: String)
}

@Database(entities = [GameSaveEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun gameSaveDao(): GameSaveDao
}
