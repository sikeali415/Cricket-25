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
        // International Players provided by User
        // Australia
        Player(id = "int-au-11", name = "A. Haddin", age = 32, nationality = "Australia", role = PlayerRole.ALL_ROUNDER, battingSkill = 65, secondarySkill = 56, isForeign = true, basePrice = 1.0, style = BattingStyle.A),
        Player(id = "int-au-12", name = "Langer", age = 24, nationality = "Australia", role = PlayerRole.FAST_BOWLER, battingSkill = 22, secondarySkill = 72, isForeign = true, basePrice = 1.0, style = BattingStyle.D),
        Player(id = "int-au-13", name = "Parsh", age = 26, nationality = "Australia", role = PlayerRole.WICKET_KEEPER, battingSkill = 76, secondarySkill = 0, isForeign = true, basePrice = 2.0, isOpener = true, style = BattingStyle.N),
        Player(id = "int-au-14", name = "Mausechate", age = 23, nationality = "Australia", role = PlayerRole.BATSMAN, battingSkill = 69, secondarySkill = 23, isForeign = true, basePrice = 1.0, style = BattingStyle.A),
        Player(id = "int-au-15", name = "Wade", age = 25, nationality = "Australia", role = PlayerRole.FAST_BOWLER, battingSkill = 25, secondarySkill = 84, isForeign = true, basePrice = 2.0, style = BattingStyle.D),
        Player(id = "int-au-16", name = "Lance", age = 26, nationality = "Australia", role = PlayerRole.BATSMAN, battingSkill = 76, secondarySkill = 0, isForeign = true, basePrice = 1.5, isOpener = true, style = BattingStyle.N),
        Player(id = "int-au-17", name = "M.G. Glaxen", age = 28, nationality = "Australia", role = PlayerRole.ALL_ROUNDER, battingSkill = 82, secondarySkill = 65, isForeign = true, basePrice = 2.0, isOpener = true, style = BattingStyle.A),
        Player(id = "int-au-18", name = "J. Harris", age = 22, nationality = "Australia", role = PlayerRole.ALL_ROUNDER, battingSkill = 56, secondarySkill = 45, isForeign = true, basePrice = 0.50, style = BattingStyle.N),
        Player(id = "int-au-19", name = "Lin", age = 23, nationality = "Australia", role = PlayerRole.FAST_BOWLER, battingSkill = 34, secondarySkill = 68, isForeign = true, basePrice = 0.50, style = BattingStyle.N),
        Player(id = "int-au-20", name = "Wilton", age = 27, nationality = "Australia", role = PlayerRole.ALL_ROUNDER, battingSkill = 72, secondarySkill = 64, isForeign = true, basePrice = 1.0, style = BattingStyle.N),

        // New Zealand
        Player(id = "int-nz-6", name = "Waller", age = 24, nationality = "New Zealand", role = PlayerRole.FAST_BOWLER, battingSkill = 23, secondarySkill = 67, isForeign = true, basePrice = 0.50, style = BattingStyle.N),
        Player(id = "int-nz-7", name = "B. Rington", age = 28, nationality = "New Zealand", role = PlayerRole.ALL_ROUNDER, battingSkill = 45, secondarySkill = 56, isForeign = true, basePrice = 0.50, style = BattingStyle.N),
        Player(id = "int-nz-8", name = "Addams", age = 25, nationality = "New Zealand", role = PlayerRole.ALL_ROUNDER, battingSkill = 55, secondarySkill = 45, isForeign = true, basePrice = 0.50, style = BattingStyle.N),
        Player(id = "int-nz-9", name = "S. Warner", age = 26, nationality = "New Zealand", role = PlayerRole.BATSMAN, battingSkill = 76, secondarySkill = 33, isForeign = true, basePrice = 1.0, isOpener = true, style = BattingStyle.A),
        Player(id = "int-nz-10", name = "Sprike", age = 22, nationality = "New Zealand", role = PlayerRole.WICKET_KEEPER, battingSkill = 80, secondarySkill = 0, isForeign = true, basePrice = 1.5, isOpener = true, style = BattingStyle.A),

        // West Indies
        Player(id = "int-wi-4", name = "Jordan", age = 24, nationality = "West Indies", role = PlayerRole.FAST_BOWLER, battingSkill = 23, secondarySkill = 66, isForeign = true, basePrice = 0.50, style = BattingStyle.N),
        Player(id = "int-wi-5", name = "N. Fill", age = 27, nationality = "West Indies", role = PlayerRole.SPIN_BOWLER, battingSkill = 22, secondarySkill = 68, isForeign = true, basePrice = 0.50, style = BattingStyle.N),
        Player(id = "int-wi-6", name = "A. Chadwick", age = 23, nationality = "West Indies", role = PlayerRole.WICKET_KEEPER, battingSkill = 73, secondarySkill = 0, isForeign = true, basePrice = 1.0, isOpener = true, style = BattingStyle.A),

        // South Africa
        Player(id = "int-sa-2", name = "James", age = 25, nationality = "South Africa", role = PlayerRole.ALL_ROUNDER, battingSkill = 63, secondarySkill = 66, isForeign = true, basePrice = 0.75, style = BattingStyle.A),
        Player(id = "int-sa-3", name = "Aram", age = 29, nationality = "South Africa", role = PlayerRole.ALL_ROUNDER, battingSkill = 45, secondarySkill = 66, isForeign = true, basePrice = 0.75, style = BattingStyle.D),

        // England
        Player(id = "int-en-3", name = "N. Colin", age = 24, nationality = "England", role = PlayerRole.ALL_ROUNDER, battingSkill = 70, secondarySkill = 61, isForeign = true, basePrice = 1.0, style = BattingStyle.N),
        Player(id = "int-en-4", name = "D. Quentin", age = 22, nationality = "England", role = PlayerRole.BATSMAN, battingSkill = 56, secondarySkill = 23, isForeign = true, basePrice = 0.50, style = BattingStyle.N),

        // Previous data with age-cap fix (Amir age 37 -> cap skills at 64)
        Player(id = "ar-7", name = "Amir", age = 37, nationality = "Local", role = PlayerRole.ALL_ROUNDER, battingSkill = 64, secondarySkill = 64, isForeign = false, basePrice = 2.0),
        
        Player(id = "int-au-1", name = "Chemar Greaves", age = 26, nationality = "West Indies", role = PlayerRole.ALL_ROUNDER, battingSkill = 70, secondarySkill = 65, isForeign = true, basePrice = 2.0),
        Player(id = "int-au-2", name = "Pankaj Mishra", age = 27, nationality = "India", role = PlayerRole.SPIN_BOWLER, battingSkill = 12, secondarySkill = 75, isForeign = true, basePrice = 1.0),
        Player(id = "int-au-3", name = "Arjun Malhotra", age = 27, nationality = "India", role = PlayerRole.BATSMAN, battingSkill = 78, secondarySkill = 0, isForeign = true, isOpener = true, basePrice = 2.0),
        
        // Local Pool
        Player(id = "sb-1", name = "Rahat", age = 24, nationality = "Local", role = PlayerRole.SPIN_BOWLER, battingSkill = 12, secondarySkill = 59, isForeign = false, basePrice = 0.25),
        Player(id = "sb-3", name = "Anwar", age = 26, nationality = "Local", role = PlayerRole.SPIN_BOWLER, battingSkill = 28, secondarySkill = 81, isForeign = false, basePrice = 2.0),
        Player(id = "ar-12", name = "Sike", age = 25, nationality = "Local", role = PlayerRole.ALL_ROUNDER, battingSkill = 87, secondarySkill = 85, isForeign = false, isOpener = true, basePrice = 2.0),
        Player(id = "wk-2", name = "S. Khan", age = 24, nationality = "Local", role = PlayerRole.WICKET_KEEPER, battingSkill = 75, secondarySkill = 87, isForeign = false, isOpener = true, basePrice = 2.0),
        Player(id = "bl-12", name = "Naseem", age = 23, nationality = "Local", role = PlayerRole.FAST_BOWLER, battingSkill = 22, secondarySkill = 81, isForeign = false, basePrice = 2.0),
        Player(id = "bt-6", name = "Nasir", age = 26, nationality = "Local", role = PlayerRole.BATSMAN, battingSkill = 81, secondarySkill = 48, isForeign = false, isOpener = true, basePrice = 2.0),
        
        Player(id = "bt-30", name = "Faisal Hasan", age = 27, nationality = "Local", role = PlayerRole.BATSMAN, battingSkill = 83, secondarySkill = 60, isForeign = false, basePrice = 2.0),
        Player(id = "bl-22", name = "Iqrar", age = 28, nationality = "Local", role = PlayerRole.FAST_BOWLER, battingSkill = 19, secondarySkill = 90, isForeign = false, basePrice = 2.0)
    )
}
