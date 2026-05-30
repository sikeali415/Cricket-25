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

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.clickable
import androidx.compose.runtime.*
import com.example.domain.Player

@Composable
fun CareerHubScreen(
    gameData: GameData
) {
    val userTeam = gameData.teams.find { it.id == gameData.userTeamId }
    var selectedPlayer by remember { mutableStateOf<Player?>(null) }
    
    if (selectedPlayer != null) {
        PlayerProfileScreen(player = selectedPlayer!!) {
            selectedPlayer = null
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Slate950)
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = "${userTeam?.name} Head Office",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Black,
                    color = Color.White
                )
                Text(
                    text = "Career Mode • Season ${gameData.currentSeason}",
                    color = Teal500,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Squad List
                Text(
                    text = "TEAM ROSTER",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                )
                
                LazyColumn(modifier = Modifier.weight(1f).fillMaxWidth()) {
                    items(userTeam?.squad ?: emptyList()) { player ->
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clickable { selectedPlayer = player },
                            color = Slate900,
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(player.name, color = Color.White, fontWeight = FontWeight.Bold)
                                    Text(player.role.name, color = Color.Gray, style = MaterialTheme.typography.labelSmall)
                                }
                                Text("${"%.2f".format(player.marketValue)} Cr", color = Teal500, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Button(
                    onClick = { /* TODO: Launch Match */ },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Teal500)
                ) {
                    Text("START NEXT MATCH", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
