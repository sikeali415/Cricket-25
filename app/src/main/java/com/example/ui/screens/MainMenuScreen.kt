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
import androidx.compose.ui.unit.sp
import com.example.ui.theme.Slate950
import com.example.ui.theme.Teal500

@Composable
fun MainMenuScreen(
    onStartNewGame: () -> Unit,
    onResumeGame: () -> Unit,
    hasSaveData: Boolean
) {
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
                text = "CRICKET MANAGER",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Black,
                color = Color.White,
                fontSize = 40.sp,
                lineHeight = 44.sp
            )
            Text(
                text = "26 BETA EDITION",
                style = MaterialTheme.typography.labelLarge,
                color = Teal500,
                fontWeight = FontWeight.Bold,
                letterSpacing = 4.sp
            )
            
            Spacer(modifier = Modifier.height(64.dp))
            
            Button(
                onClick = onStartNewGame,
                modifier = Modifier.fillMaxWidth().height(64.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Teal500)
            ) {
                Text("START NEW CAREER", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            if (hasSaveData) {
                Button(
                    onClick = onResumeGame,
                    modifier = Modifier.fillMaxWidth().height(64.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
                ) {
                    Text("RESUME SEASON", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                }
            }
        }
        
        Text(
            text = "v1.0.0 APK Supported",
            style = MaterialTheme.typography.labelSmall,
            color = Color.Gray,
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 16.dp)
        )
    }
}
