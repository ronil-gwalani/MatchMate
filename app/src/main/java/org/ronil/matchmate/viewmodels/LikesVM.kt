package org.ronil.matchmate.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.ronil.matchmate.models.Status
import org.ronil.matchmate.models.UserEntity
import org.ronil.matchmate.repository.RoomRepository

class LikesVM(private val roomRepository: RoomRepository) : ViewModel() {
    var selectedProfile by mutableStateOf<UserEntity?>(null)
    var showBottomSheet by mutableStateOf(false)

    fun onRightSwipe(matchProfile: UserEntity) {
        viewModelScope.launch {
            roomRepository.changeStatus(matchProfile.email, Status.Accepted)
        }

    }

    fun onLeftSwipe(matchProfile: UserEntity) {
        viewModelScope.launch {
            roomRepository.changeStatus(matchProfile.email, Status.Rejected)
        }
    }

    val allUsers = roomRepository.allUsers


}



