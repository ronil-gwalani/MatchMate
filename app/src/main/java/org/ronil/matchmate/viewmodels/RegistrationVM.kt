package org.ronil.matchmate.viewmodels

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import org.ronil.matchmate.utils.AppConstants
import org.ronil.matchmate.utils.PreferenceManager

class RegistrationVM(private val preferenceManager: PreferenceManager) : ViewModel() {

    init {
        setInitialData()
    }

    private fun setInitialData() {
        preferenceManager.apply {
            viewModelScope.launch {
                getString(AppConstants.Preferences.NAME)?.let {
                    name = it
                }
                getString(AppConstants.Preferences.AGE)?.let {
                    age = it
                }
                getString(AppConstants.Preferences.GENDER)?.let {
                    gender = it
                }
                getString(AppConstants.Preferences.EMAIL)?.let {
                    email = it
                }

            }
        }

    }

    private val _showError = MutableSharedFlow<String>()
    val showError: Flow<String> get() = _showError

    val genders = listOf("Male", "Female", "Other")
    var name by mutableStateOf("")
    var email by mutableStateOf("")
    var age by mutableStateOf("")
    var gender by mutableStateOf("Male")
    var showDialog by mutableStateOf(false)
    var genderExpanded by mutableStateOf(false)


    fun verifyInput() {
        viewModelScope.launch {
            if (name.isEmpty()) {
                _showError.emit("Name cannot be empty")
            } else if (email.isEmpty()) {
                _showError.emit("Email cannot be empty")
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                _showError.emit("Please enter a valid email address")
            } else if (age.isEmpty()) {
                _showError.emit("Age cannot be empty")
            } else if (age.toIntOrNull() != null && (age.toInt() <= 18 || age.toInt() >= 100)) {
                _showError.emit("Age must be in between 18-100")
            } else if (gender.isEmpty()) {
                _showError.emit("Gender cannot be empty")
            } else {
                showDialog = true
            }

        }
    }

    fun saveDataIntoPref() {
        viewModelScope.launch {
            preferenceManager.apply {
                setValue(AppConstants.Preferences.NAME, name)
                setValue(AppConstants.Preferences.EMAIL, email)
                setValue(AppConstants.Preferences.AGE, age)
                setValue(AppConstants.Preferences.GENDER, gender)
            }
        }

    }

}