package com.example.utils

import com.example.domain.*
import java.util.UUID
import kotlin.random.Random

object SquadUtils {
    private const val MIN_SQUAD_SIZE = 15
    private const val MAX_SQUAD_SIZE = 18

    fun fillRemainingSquadSlots(teams: List<Team>, pool: List<Player>): List<Team> {
        val usedPlayerIds = teams.flatMap { it.squad.map { p -> p.id } }.toMutableSet()
        val availablePool = pool.filter { !usedPlayerIds.contains(it.id) }.toMutableList()

        return teams.map { team ->
            var currentSquad = team.squad.toMutableList()
            var currentPurse = team.purse

            // 1. First, try to fill with available pool players if squad < MIN_SQUAD_SIZE
            if (currentSquad.size < MIN_SQUAD_SIZE) {
                val needed = MIN_SQUAD_SIZE - currentSquad.size
                val toAdd = availablePool.take(needed)
                currentSquad.addAll(toAdd)
                availablePool.removeAll(toAdd)
                usedPlayerIds.addAll(toAdd.map { it.id })
            }

            // 2. If still < MIN_SQUAD_SIZE (pool exhausted), generate random "Youth/Local" players
            while (currentSquad.size < MIN_SQUAD_SIZE) {
                val role = PlayerRole.values().random()
                val isOpener = role == PlayerRole.BATSMAN && Random.nextBoolean()
                val skill = Random.nextInt(45, 62)
                val secSkill = if (role == PlayerRole.ALL_ROUNDER) Random.nextInt(40, 58) else Random.nextInt(5, 30)
                
                val newPlayer = Player(
                    id = "gen-${UUID.randomUUID()}",
                    name = generateRandomName(),
                    age = Random.nextInt(18, 24),
                    nationality = "Local",
                    role = role,
                    battingSkill = if (role == PlayerRole.BATSMAN || role == PlayerRole.ALL_ROUNDER) skill else Random.nextInt(10, 25),
                    secondarySkill = if (role != PlayerRole.BATSMAN) secSkill else 0,
                    isOpener = isOpener,
                    isForeign = false,
                    basePrice = 0.20
                )
                currentSquad.add(newPlayer)
            }

            team.copy(squad = currentSquad, purse = currentPurse)
        }
    }

    private fun generateRandomName(): String {
        val first = listOf("Ali", "Amir", "Bilal", "Zaid", "Omar", "Hassan", "Usman", "Rohan", "Sami", "Yusuf")
        val last = listOf("Khan", "Ahmed", "Malik", "Shah", "Iqbal", "Butt", "Raza", "Hussain", "Javed", "Aziz")
        return "${first.random()} ${last.random()}"
    }
}
