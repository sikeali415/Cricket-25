package com.example.data

import com.example.domain.*

object MockData {
    val teams = listOf(
        Team(id = "team1", name = "Kings", purse = 100.0),
        Team(id = "team2", name = "Stars", purse = 100.0),
        Team(id = "team3", name = "Sixers", purse = 100.0),
        Team(id = "team4", name = "Gladiators", purse = 100.0),
        Team(id = "team5", name = "Eagles", purse = 100.0),
        Team(id = "team6", name = "Hawks", purse = 100.0)
    )

    val players = listOf(
        Player(id = "int-au-1", name = "Chemar Greaves", age = 26, nationality = "West Indies", role = PlayerRole.ALL_ROUNDER, battingSkill = 70, secondarySkill = 65, isForeign = true, basePrice = 2.0),
        Player(id = "int-au-2", name = "Pankaj Mishra", age = 27, nationality = "India", role = PlayerRole.SPIN_BOWLER, battingSkill = 12, secondarySkill = 75, isForeign = true, basePrice = 1.0),
        Player(id = "int-au-3", name = "Arjun Malhotra", age = 27, nationality = "India", role = PlayerRole.BATSMAN, battingSkill = 78, secondarySkill = 0, isForeign = true, isOpener = true, basePrice = 2.0),
        Player(id = "sb-1", name = "Rahat", age = 24, nationality = "Local", role = PlayerRole.SPIN_BOWLER, battingSkill = 12, secondarySkill = 59, isForeign = false, basePrice = 0.25),
        Player(id = "sb-3", name = "Anwar", age = 26, nationality = "Local", role = PlayerRole.SPIN_BOWLER, battingSkill = 28, secondarySkill = 81, isForeign = false, basePrice = 2.0),
        Player(id = "ar-12", name = "Sike", age = 25, nationality = "Local", role = PlayerRole.ALL_ROUNDER, battingSkill = 87, secondarySkill = 85, isForeign = false, isOpener = true, basePrice = 2.0),
        Player(id = "wk-2", name = "S. Khan", age = 24, nationality = "Local", role = PlayerRole.WICKET_KEEPER, battingSkill = 75, secondarySkill = 87, isForeign = false, isOpener = true, basePrice = 2.0),
        Player(id = "bl-12", name = "Naseem", age = 23, nationality = "Local", role = PlayerRole.FAST_BOWLER, battingSkill = 22, secondarySkill = 81, isForeign = false, basePrice = 2.0),
        Player(id = "bt-6", name = "Nasir", age = 26, nationality = "Local", role = PlayerRole.BATSMAN, battingSkill = 81, secondarySkill = 48, isForeign = false, isOpener = true, basePrice = 2.0)
    )
}
