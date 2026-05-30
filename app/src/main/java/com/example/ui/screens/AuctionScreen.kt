package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.*
import com.example.ui.theme.Slate900
import com.example.ui.theme.Slate950
import com.example.ui.theme.Teal200
import com.example.ui.theme.Teal500
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun AuctionScreen(
    gameData: GameData,
    onAuctionComplete: (List<Team>) -> Unit
) {
    var teams by remember { mutableStateOf(gameData.teams) }
    val playersPool = remember { gameData.allPlayers.shuffled() }
    
    var currentPlayerIdx by remember { mutableIntStateOf(0) }
    var currentBid by remember { mutableDoubleStateOf(0.0) }
    var highestBidderId by remember { mutableStateOf<String?>(null) }
    var isAuctioning by remember { mutableStateOf(false) }
    var isProcessing by remember { mutableStateOf(false) }
    var countdown by remember { mutableIntStateOf(3) }
    var biddingLog by remember { mutableStateOf(listOf("Welcome to the 2026 Draft Room.")) }
    var auctionFinished by remember { mutableStateOf(false) }

    val currentPlayer = playersPool.getOrNull(currentPlayerIdx)
    val userTeam = teams.find { it.id == gameData.userTeamId }

    fun getBidIncrement(current: Double): Double {
        return when {
            current < 1.0 -> 0.25
            current < 5.0 -> 0.50
            current < 10.0 -> 1.00
            else -> 2.00
        }
    }

    fun getPlayerValuation(player: Player, team: Team): Double {
        val rating = maxOf(player.battingSkill, player.secondarySkill)
        val skillFactor = Math.pow(rating / 50.0, 4.0)
        val baseValuation = skillFactor * 2.5
        
        // Simplified need factor
        val roleCount = team.squad.count { it.role == player.role }
        val needFactor = when {
            roleCount >= 5 -> 0.4
            roleCount < 2 -> 1.8
            else -> 1.0
        }
        
        val personalityJitter = 0.8 + (Random.nextDouble() * 0.6)
        return baseValuation * needFactor * personalityJitter
    }

    fun sellPlayer() {
        isAuctioning = false
        isProcessing = true
        val winnerId = highestBidderId
        if (winnerId != null && currentPlayer != null) {
            val winner = teams.find { it.id == winnerId }
            if (winner != null) {
                teams = teams.map { t ->
                    if (t.id == winnerId) {
                        t.copy(
                            purse = t.purse - currentBid,
                            squad = t.squad + currentPlayer
                        )
                    } else t
                }
                biddingLog = listOf("🎉 SOLD! ${currentPlayer.name} to ${winner.name} for ${"%.2f".format(currentBid)} Cr") + biddingLog.take(10)
            }
        }
        currentPlayerIdx++
        isProcessing = false
    }

    fun unsoldPlayer() {
        isAuctioning = false
        isProcessing = true
        if (currentPlayer != null) {
            biddingLog = listOf("❌ UNSOLD: ${currentPlayer.name}") + biddingLog.take(10)
        }
        currentPlayerIdx++
        isProcessing = false
    }

    // Logic Loop
    LaunchedEffect(isAuctioning, currentBid, highestBidderId, countdown) {
        if (!isAuctioning || currentPlayer == null || auctionFinished) return@LaunchedEffect

        delay(1500 + Random.nextLong(1000))
        
        val increment = getBidIncrement(currentBid)
        val nextBid = currentBid + increment
        
        val eligibleTeams = teams.filter { t ->
            t.id != highestBidderId &&
            t.purse >= nextBid &&
            t.squad.size < 18
        }
        
        val biddingTeam = eligibleTeams.find { t ->
            if (t.id == gameData.userTeamId) false
            else nextBid <= getPlayerValuation(currentPlayer, t)
        }
        
        if (biddingTeam != null) {
            currentBid = nextBid
            highestBidderId = biddingTeam.id
            countdown = 3
            biddingLog = listOf("${biddingTeam.name} bids ${"%.2f".format(nextBid)} Cr!") + biddingLog.take(10)
        } else {
            if (countdown > 1) {
                countdown--
                val msg = if (countdown == 2) "Going once..." else "Going twice..."
                biddingLog = listOf("$msg at ${"%.2f".format(currentBid)} Cr") + biddingLog.take(10)
            } else {
                if (highestBidderId != null) sellPlayer()
                else unsoldPlayer()
            }
        }
    }

    // Start next player
    LaunchedEffect(currentPlayerIdx, isAuctioning, isProcessing) {
        if (!isAuctioning && !isProcessing) {
            if (currentPlayerIdx >= playersPool.size) {
                auctionFinished = true
            } else {
                val p = playersPool[currentPlayerIdx]
                currentBid = p.basePrice
                highestBidderId = null
                countdown = 3
                isAuctioning = true
            }
        }
    }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Slate900)
                    .padding(16.dp)
                    .padding(top = 24.dp)
            ) {
                Text(
                    text = "DRAFT ROOM",
                    color = Teal500,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Black
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "SESSION 1",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Black,
                        color = Color.White
                    )
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "YOUR PURSE",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.Gray
                        )
                        Text(
                            text = "${"%.2f".format(userTeam?.purse ?: 0.0)} Cr",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Black,
                            color = Teal200
                        )
                    }
                }
            }
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(Slate900)
                    .padding(16.dp)
            ) {
                Text(
                    text = "LIVE ACTIVITY FEED",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(biddingLog) { log ->
                        Text(
                            text = log,
                            style = MaterialTheme.typography.bodySmall,
                            color = if (log.contains("SOLD") || log.contains("bids")) Teal200 else Color.Gray,
                            modifier = Modifier.padding(vertical = 2.dp)
                        )
                        HorizontalDivider(color = Color.White.copy(alpha = 0.05f))
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Slate950)
                .padding(innerPadding)
        ) {
            // Background Glow
            Box(
                modifier = Modifier
                    .size(300.dp)
                    .align(Alignment.TopStart)
                    .offset(x = (-100).dp, y = (-100).dp)
                    .background(
                        Brush.radialGradient(listOf(Teal500.copy(alpha = 0.1f), Color.Transparent))
                    )
            )

            if (auctionFinished) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("🏠", fontSize = 64.sp)
                    Text(
                        "DRAFT COMPLETED",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Black,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { onAuctionComplete(teams) },
                        colors = ButtonDefaults.buttonColors(containerColor = Teal500)
                    ) {
                        Text("PROCEED TO HUB", fontWeight = FontWeight.Bold)
                    }
                }
            } else if (currentPlayer != null) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Player Card
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(32.dp),
                        color = Slate900,
                        border = ButtonDefaults.outlinedButtonBorder
                    ) {
                        Column(modifier = Modifier.padding(24.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Surface(
                                    color = Color.DarkGray,
                                    shape = RoundedCornerShape(16.dp)
                                ) {
                                    Text(
                                        text = currentPlayer.role.name,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                        style = MaterialTheme.typography.labelSmall,
                                        color = Color.White
                                    )
                                }
                                Text(
                                    text = "SKILL: ${maxOf(currentPlayer.battingSkill, currentPlayer.secondarySkill)}",
                                    color = Teal500,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = currentPlayer.name,
                                style = MaterialTheme.typography.headlineLarge,
                                fontWeight = FontWeight.Black,
                                textAlign = TextAlign.Center,
                                color = Color.White,
                                modifier = Modifier.fillMaxWidth()
                            )
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            Row(modifier = Modifier.fillMaxWidth()) {
                                StatBox("BATTING", currentPlayer.battingSkill, Modifier.weight(1f))
                                Spacer(modifier = Modifier.width(8.dp))
                                StatBox("BOWLING", currentPlayer.secondarySkill, Modifier.weight(1f))
                            }
                            
                            Spacer(modifier = Modifier.height(24.dp))
                            // Bid Display
                            Surface(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(24.dp),
                                color = Color.Black.copy(alpha = 0.4f)
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = if (highestBidderId != null) "CURRENT HIGHEST BID" else "STARTING PRICE",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = Color.Gray
                                    )
                                    Text(
                                        text = "${"%.2f".format(currentBid)} Cr",
                                        style = MaterialTheme.typography.displayMedium,
                                        fontWeight = FontWeight.Black,
                                        color = Color.Yellow
                                    )
                                    highestBidderId?.let { id ->
                                        Text(
                                            text = teams.find { it.id == id }?.name ?: "",
                                            color = Teal200,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                    
                                    Spacer(modifier = Modifier.height(12.dp))
                                    LinearProgressIndicator(
                                        progress = { countdown / 3f },
                                        modifier = Modifier.fillMaxWidth(),
                                        color = if (countdown == 1) Color.Red else Teal500,
                                        trackColor = Color.DarkGray
                                    )
                                }
                            }
                            
                            Spacer(modifier = Modifier.height(24.dp))
                            val nextBid = currentBid + getBidIncrement(currentBid)
                            val canBid = highestBidderId != gameData.userTeamId && (userTeam?.purse ?: 0.0) >= nextBid
                            
                            Button(
                                onClick = {
                                    highestBidderId = gameData.userTeamId
                                    currentBid = nextBid
                                    countdown = 3
                                    biddingLog = listOf("YOU bid ${"%.2f".format(nextBid)} Cr!") + biddingLog.take(10)
                                },
                                modifier = Modifier.fillMaxWidth().height(64.dp),
                                shape = RoundedCornerShape(16.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Teal500,
                                    disabledContainerColor = Color.DarkGray
                                ),
                                enabled = canBid
                            ) {
                                Icon(Icons.Default.Add, null)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("BID ${"%.2f".format(nextBid)} Cr", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                            }
                            
                            Spacer(modifier = Modifier.height(12.dp))
                            Row(modifier = Modifier.fillMaxWidth()) {
                                OutlinedButton(
                                    onClick = { unsoldPlayer() },
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Icon(Icons.Default.Block, null)
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("SKIP")
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                OutlinedButton(
                                    onClick = { /* Show team status */ },
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Icon(Icons.Default.Groups, null)
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("STATUS")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StatBox(label: String, value: Int, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        color = Slate800,
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(label, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
            Text(value.toString(), style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = Color.White)
        }
    }
}
