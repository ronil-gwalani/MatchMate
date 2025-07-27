package org.ronil.matchmate.utils

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Immutable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.ConcurrentLinkedQueue





@Immutable
class ShackBarState {
    enum class ShackBarType {
        POSITIVE, NEGATIVE
    }

     val hostState: SnackbarHostState = SnackbarHostState()
    private val messageQueue = ConcurrentLinkedQueue<String>() // Queue for messages
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private var isShowing = false // To track if a Snackbar is active

    fun showSnackBar(message: String) {
        messageQueue.add(message) // Add message to queue
        if (!isShowing) {
            processQueue()
        }
    }

    private fun processQueue() {
        if (isShowing || messageQueue.isEmpty()) return

        isShowing = true
        val message = messageQueue.poll() ?: return // Get next message

        scope.launch {
            val job = launch {
                hostState.showSnackbar(
                    message = message,
                    duration = SnackbarDuration.Indefinite // Keep open manually
                )
            }
            delay(2500) // Wait for 2500ms
            job.cancel() // Cancel Snackbar coroutine
            hostState.currentSnackbarData?.dismiss() // Dismiss manually
            isShowing = false // Mark as not showing
            processQueue() // Show next message if available
        }

    }

}



