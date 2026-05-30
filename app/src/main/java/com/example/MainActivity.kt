package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.ui.theme.MyApplicationTheme

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.MainViewModel
import com.example.ui.screens.*
import com.example.domain.AppState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        val viewModel: MainViewModel = viewModel()
        val gameState by viewModel.gameState.collectAsState()
        val gameData by viewModel.gameData.collectAsState()
        
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
          when (gameState) {
            AppState.MAIN_MENU -> MainMenuScreen(
              onStartNewGame = { viewModel.navigateTo(AppState.TEAM_SELECTION) },
              onResumeGame = { viewModel.navigateTo(AppState.CAREER_HUB) },
              hasSaveData = gameData != null
            )
            AppState.TEAM_SELECTION -> TeamSelectionScreen(
              onTeamSelected = { teamId -> viewModel.startNewGame(teamId) }
            )
            AppState.AUCTION -> {
              gameData?.let {
                AuctionScreen(
                  gameData = it,
                  onAuctionComplete = { updatedTeams -> viewModel.onAuctionComplete(updatedTeams) }
                )
              }
            }
            AppState.CAREER_HUB -> {
              gameData?.let {
                CareerHubScreen(gameData = it)
              }
            }
          }
        }
      }
    }
  }
}
