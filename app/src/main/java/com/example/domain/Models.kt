package com.example.domain

import kotlinx.serialization.Serializable

@Serializable
enum class Format(val displayName: String) {
    T20("Premier T20 League"),
    ODI("Premier One-Day Cup"),
    SHIELD("Premier First-Class Shield")
}

@Serializable
enum class PlayerRole(val code: String) {
    BATSMAN("BT"),
    WICKET_KEEPER("WK"),
    ALL_ROUNDER("AR"),
    SPIN_BOWLER("SB"),
    FAST_BOWLER("BL")
}

@Serializable
enum class BattingStyle { A, D, N, NA }

@Serializable
enum class Strategy { defensive, balanced, attacking, blitzkrieg }

@Serializable
enum class AppState { MAIN_MENU, TEAM_SELECTION, AUCTION, CAREER_HUB }

@Serializable
enum class BowlingSubType { ls, os, lals, laos, lac, mv, fb, fbs, m, none }

@Serializable
data class PlayerStats(
    val matches: Int = 0,
    val inningsBatting: Int = 0,
    val inningsBowling: Int = 0,
    val runs: Int = 0,
    val highestScore: Int = 0,
    val average: Double = 0.0,
    val strikeRate: Double = 0.0,
    val ballsFaced: Int = 0,
    val dismissals: Int = 0,
    val hundreds: Int = 0,
    val fifties: Int = 0,
    val thirties: Int = 0,
    val fours: Int = 0,
    val sixes: Int = 0,
    val wickets: Int = 0,
    val economy: Double = 0.0,
    val bestBowling: String = "-",
    val manOfTheMatchAwards: Int = 0
)

@Serializable
data class Player(
    val id: String,
    val name: String,
    val age: Int,
    val nationality: String,
    val role: PlayerRole,
    val battingSkill: Int,
    val secondarySkill: Int,
    val style: BattingStyle = BattingStyle.N,
    val isOpener: Boolean = false,
    val isForeign: Boolean = false,
    val teamName: String? = null,
    val stats: Map<Format, PlayerStats> = emptyMap(),
    val basePrice: Double = 0.0,
    val bowlingSubType: BowlingSubType = BowlingSubType.none
)

@Serializable
data class Team(
    val id: String,
    val name: String,
    val squad: List<Player> = emptyList(),
    val purse: Double = 100.0,
    val firstAidKits: Int = 2
)

@Serializable
data class TeamData(
    val id: String,
    val name: String,
    val homeGround: String,
    val logo: String, // SVG or Resource Name
    val isYouthTeam: Boolean = false
)

@Serializable
data class Match(
    val matchNumber: Int,
    val teamA: String,
    val teamAId: String,
    val vs: String, // usually "vs"
    val teamB: String,
    val teamBId: String,
    val date: String,
    val group: String // e.g. "Round-Robin"
)

@Serializable
data class GameData(
    val userTeamId: String,
    val teams: List<Team>,
    val allPlayers: List<Player>,
    val currentSeason: Int = 1,
    val currentFormat: Format = Format.T20,
    val popularity: Int = 50
)
