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
            while (currentSquad.size < MIN_SQUAD_SIZE && availablePool.isNotEmpty()) {
                val player = availablePool.removeAt(0)
                val price = calculateAutoAuctionPrice(player.basePrice)
                
                // If team can afford, buy at that price
                if (currentPurse >= price) {
                    currentPurse -= price
                    currentSquad.add(player.copy(boughtFor = price, marketValue = price * 1.1))
                } else {
                    // Buy at base price if can afford base price but not auto-auction price
                    if (currentPurse >= player.basePrice) {
                        currentPurse -= player.basePrice
                        currentSquad.add(player.copy(boughtFor = player.basePrice, marketValue = player.basePrice * 1.5))
                    } else {
                        // Free agent / Youth tier
                        currentSquad.add(player.copy(boughtFor = 0.05, marketValue = player.basePrice))
                        currentPurse -= 0.05
                    }
                }
                usedPlayerIds.add(player.id)
            }

            // 2. If still < MIN_SQUAD_SIZE (pool exhausted), generate random "Youth/Local" players
            while (currentSquad.size < MIN_SQUAD_SIZE) {
                val role = PlayerRole.values().random()
                val isOpener = role == PlayerRole.BATSMAN && Random.nextBoolean()
                val skill = if (Random.nextInt(50) > 40) Random.nextInt(55, 64) else Random.nextInt(45, 62)
                val secSkill = if (role == PlayerRole.ALL_ROUNDER) Random.nextInt(40, 58) else Random.nextInt(5, 30)
                
                // Skill cap is mainly for veteran generated players if age > 34
                val age = Random.nextInt(18, 38)
                val cappedSkill = if (age > 34 && skill > 64) 64 else skill
                val cappedSecSkill = if (age > 34 && secSkill > 64) 64 else secSkill

                val basePrice = 0.20
                val boughtPrice = calculateAutoAuctionPrice(basePrice)

                val newPlayer = Player(
                    id = "gen-${UUID.randomUUID()}",
                    name = generateRandomName(),
                    age = age,
                    nationality = "Local",
                    role = role,
                    battingSkill = if (role == PlayerRole.BATSMAN || role == PlayerRole.ALL_ROUNDER) cappedSkill else Random.nextInt(10, 25),
                    secondarySkill = if (role != PlayerRole.BATSMAN) cappedSecSkill else 0,
                    isOpener = isOpener,
                    isForeign = false,
                    basePrice = basePrice,
                    boughtFor = boughtPrice,
                    marketValue = boughtPrice * 1.2
                )
                currentSquad.add(newPlayer)
                currentPurse -= boughtPrice
            }

            team.copy(squad = currentSquad, purse = maxOf(0.0, currentPurse))
        }
    }

    private fun calculateAutoAuctionPrice(basePrice: Double): Double {
        return when {
            basePrice <= 0.50 -> basePrice * Random.nextDouble(5.0, 8.0)
            basePrice <= 1.0 -> Random.nextDouble(4.0, 9.0)
            else -> Random.nextDouble(10.0, 30.0)
        }
    }

    private fun generateRandomName(): String {
        val first = listOf("Ali", "Amir", "Bilal", "Zaid", "Omar", "Hassan", "Usman", "Rohan", "Sami", "Yusuf")
        val last = listOf("Khan", "Ahmed", "Malik", "Shah", "Iqbal", "Butt", "Raza", "Hussain", "Javed", "Aziz")
        return "${first.random()} ${last.random()}"
    }
}
