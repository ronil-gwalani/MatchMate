package org.ronil.matchmate.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import org.ronil.matchmate.R
import org.ronil.matchmate.models.Status
import org.ronil.matchmate.models.toUserEntity
import org.ronil.matchmate.repository.NetworkRepository
import org.ronil.matchmate.repository.RoomRepository
import org.ronil.matchmate.screens.BottomItemModel
import org.ronil.matchmate.utils.NetworkConnectivityManager

class HomeVM(
    private val networkRepository: NetworkRepository,
    private val roomRepository: RoomRepository,
    private val connectivityManager: NetworkConnectivityManager
) : ViewModel() {


    var backPressedOnce by mutableStateOf(false)
    var selectedItemIndex by mutableIntStateOf(0)
    val items = listOf(
        BottomItemModel("Explore", R.drawable.matches),
        BottomItemModel("My Likes", R.drawable.like),
        BottomItemModel("Profile", R.drawable.profile),
    )


    // Track API call state
    private var apiCallState = ApiCallState.NOT_CALLED


    private enum class ApiCallState {
        NOT_CALLED,
        IN_PROGRESS,
        SUCCESS,
        FAILED
    }

    init {
        viewModelScope.launch {
            Log.e("TAG", "${ roomRepository.getAvailableUserCount()}: ", )
            if (
                roomRepository.getAvailableUserCount() < 5
            ) {
                connectivityManager.isConnected.collectLatest { isConnected ->
                    if (isConnected && shouldCallApi()) {
                        callApi()
                    }
                }
            }


        }
    }

    private fun shouldCallApi(): Boolean {
        return apiCallState == ApiCallState.NOT_CALLED || apiCallState == ApiCallState.FAILED
    }

    private fun callApi() {
        if (apiCallState == ApiCallState.IN_PROGRESS) {
            return // Prevent duplicate calls
        }

        apiCallState = ApiCallState.IN_PROGRESS

        networkRepository.getAllUsers { result ->
            viewModelScope.launch {
                try {
                    val userEntities = result.mapNotNull { it?.toUserEntity() }
                    roomRepository.insertUser(userEntities)

                    // Mark as successful only after everything succeeds
                    apiCallState = ApiCallState.SUCCESS
                    Log.d("ViewModel", "API call and data insertion successful")

                } catch (e: Exception) {
                    Log.e("ViewModel", "Error in API call or data insertion", e)
                    apiCallState = ApiCallState.FAILED
                } finally {
                }
            }
        }
    }

    // Method to manually ret~~ry (useful for pull-to-refresh or retry buttons)
    fun reCallApi() {
        if (apiCallState != ApiCallState.IN_PROGRESS) {
            apiCallState = ApiCallState.FAILED // This will trigger the call if connected
            // Check current connectivity and call if connected
            viewModelScope.launch {
                connectivityManager.isConnected.take(1).collect { isConnected ->
                    if (isConnected) {
                        callApi()
                    }
                }
            }
        }
    }

    // Optional: Method to reset the API call state (if needed for refresh)
    fun resetApiCallState() {
        apiCallState = ApiCallState.NOT_CALLED
    }

    override fun onCleared() {
        super.onCleared()
        connectivityManager.cleanup()
    }


}



