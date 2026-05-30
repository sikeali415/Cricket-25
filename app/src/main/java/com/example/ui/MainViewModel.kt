package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.MockData
import com.example.domain.*
import com.example.utils.SquadUtils
import com.example.data.persistence.GameRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(private val repository: GameRepository) : ViewModel() {
    private val _gameState = MutableStateFlow<AppState>(AppState.MAIN_MENU)
    val gameState: StateFlow<AppState> = _gameState.asStateFlow()

    private val _gameData = MutableStateFlow<GameData?>(null)
    val gameData: StateFlow<GameData?> = _gameData.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getGameData().collect { savedData ->
                _gameData.value = savedData
            }
        }
    }

    fun startNewGame(userTeamId: String) {
        viewModelScope.launch {
            val teams = MockData.teams.map { it.copy() }
            val newGameData = GameData(
                userTeamId = userTeamId,
                teams = teams,
                allPlayers = MockData.players
            )
            _gameData.value = newGameData
            repository.saveGameData(newGameData)
            _gameState.value = AppState.AUCTION
        }
    }

    fun onAuctionComplete(updatedTeams: List<Team>) {
        viewModelScope.launch {
            val filledTeams = SquadUtils.fillRemainingSquadSlots(updatedTeams, MockData.players)
            val updatedGameData = _gameData.value?.copy(teams = filledTeams)
            if (updatedGameData != null) {
                _gameData.value = updatedGameData
                repository.saveGameData(updatedGameData)
            }
            _gameState.value = AppState.CAREER_HUB
        }
    }

    fun navigateTo(state: AppState) {
        _gameState.value = state
    }
}
