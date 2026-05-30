package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.MockData
import com.example.domain.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _gameState = MutableStateFlow<AppState>(AppState.MAIN_MENU)
    val gameState: StateFlow<AppState> = _gameState.asStateFlow()

    private val _gameData = MutableStateFlow<GameData?>(null)
    val gameData: StateFlow<GameData?> = _gameData.asStateFlow()

    fun startNewGame(userTeamId: String) {
        viewModelScope.launch {
            val teams = MockData.teams.map { it.copy() }
            val newGameData = GameData(
                userTeamId = userTeamId,
                teams = teams,
                allPlayers = MockData.players
            )
            _gameData.value = newGameData
            _gameState.value = AppState.AUCTION
        }
    }

    fun onAuctionComplete(updatedTeams: List<Team>) {
        _gameData.update { it?.copy(teams = updatedTeams) }
        _gameState.value = AppState.CAREER_HUB
    }

    fun navigateTo(state: AppState) {
        _gameState.value = state
    }
}
