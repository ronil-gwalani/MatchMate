package org.ronil.matchmate.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.ronil.matchmate.models.Result
import org.ronil.matchmate.models.UserEntity
import org.ronil.matchmate.models.toUserEntity
import org.ronil.matchmate.network.RestApis

class NetworkRepository(private val restApis: RestApis) {
    private var usersJob: Job? = null
    fun getAllUsers(success:(List<Result?>)->Unit) {
        usersJob?.cancel()
        usersJob = CoroutineScope(Dispatchers.IO).launch {
            val response = restApis.getAllUsers()

            if (response.isSuccessful) {
                if (response.body() != null) {
                    response.body()?.results?.let {
                        success(it)
                    }

                }
            }
        }

    }
}