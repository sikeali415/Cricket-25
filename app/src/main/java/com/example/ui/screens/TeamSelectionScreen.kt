package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.MockData
import com.example.domain.Team
import com.example.ui.theme.Teal500
import com.example.ui.theme.Slate900
import com.example.ui.theme.Slate950

@Composable
fun TeamSelectionScreen(
    onTeamSelected: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Slate950)
            .padding(24.dp)
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "SELECT YOUR FRANCHISE",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Black,
            color = Color.White
        )
        Text(
            text = "Choose a team to manage during the 2026 Season.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(MockData.teams) { team ->
                TeamCard(team = team, onClick = { onTeamSelected(team.id) })
            }
        }
    }
}

@Composable
fun TeamCard(team: Team, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        color = Slate900,
        border = ButtonDefaults.outlinedButtonBorder
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(Teal500, RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = team.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Purse: ${team.purse} Cr",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.Gray
                )
            }
        }
    }
}
