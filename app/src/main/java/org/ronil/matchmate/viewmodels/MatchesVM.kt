package org.ronil.matchmate.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.ronil.matchmate.models.Status
import org.ronil.matchmate.models.UserEntity
import org.ronil.matchmate.repository.RoomRepository

class MatchesVM(private val roomRepository: RoomRepository) : ViewModel() {
    var selectedProfile by mutableStateOf<UserEntity?>(null)
    var showBottomSheet by mutableStateOf(false)

    fun acceptUser(matchProfile: UserEntity) {
        viewModelScope.launch {
            roomRepository.changeStatus(matchProfile.email, Status.Accepted)
        }

    }

    fun declineUser(matchProfile: UserEntity) {
        viewModelScope.launch {
            roomRepository.changeStatus(matchProfile.email, Status.Rejected)
        }
    }

    fun removeFromHere(matchProfile: UserEntity) {
        viewModelScope.launch {
            roomRepository.changeStatus(matchProfile.email, Status.NoAction)
        }
    }

    val allUsers = roomRepository.allUsers


}



