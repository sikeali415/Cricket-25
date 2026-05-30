package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.domain.GameData
import com.example.ui.theme.Teal500
import com.example.ui.theme.Slate950

@Composable
fun CareerHubScreen(
    gameData: GameData
) {
    val userTeam = gameData.teams.find { it.id == gameData.userTeamId }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Slate950)
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "${userTeam?.name} Head Office",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Black,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Career Mode Active • Season ${gameData.currentSeason}",
                color = Teal500,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            Text(
                text = "Your squad has ${userTeam?.squad?.size} players.",
                color = Color.White
            )
            
            Spacer(modifier = Modifier.height(48.dp))
            
            Text(
                text = "Logic migration to Kotlin in progress...",
                style = MaterialTheme.typography.labelMedium,
                color = Color.Gray
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Button(
                onClick = { /* TODO: Launch Match */ },
                colors = ButtonDefaults.buttonColors(containerColor = Teal500)
            ) {
                Text("NEXT MATCH", fontWeight = FontWeight.Bold)
            }
        }
    }
}
