package com.example.myapplication.ui.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.firebase.FirebaseAuthClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthClient
) : ViewModel() {

    private val _state = MutableStateFlow(SignInScreenState())
    val state = _state.asStateFlow()

    private val eventChannel = Channel<SignInViewModelEvent>()
    val events = eventChannel.receiveAsFlow()

    private var userType: String? = null

    fun onEvent(event: SignInScreenEvent) {
        when(event) {
            is SignInScreenEvent.OnUserEmailChanged -> {
                handleOnUserEmailChanged(event.email)
            }
            is SignInScreenEvent.OnUserPasswordChanged -> {
                handleOnUserPasswordChanged(event.password)
            }
            is SignInScreenEvent.OnSignInClicked -> {
                handleOnSignInClicked()
            }
            is SignInScreenEvent.OnUserSignedIn -> {
                handleOnUserSignedIn()
            }
        }
    }

    private fun handleOnUserEmailChanged(email: String) {
        _state.update {
            it.copy(
                userEmail = email,
                emailError = null
            )
        }
    }

    private fun handleOnUserPasswordChanged(password: String) {
        _state.update {
            it.copy(
                userPassword = password,
                passwordError = null
            )
        }
    }

    private fun handleOnSignInClicked() {
        viewModelScope.launch {
            firebaseAuthRepository.signInWithEmail(
                email = state.value.userEmail,
                password = state.value.userPassword,
            ).collect { result ->
                if(result.isSuccess) {
                    userType = "" //TODO: assing returned user type from firebase
                    _state.update {
                        it.copy(
                            isSignedIn = true
                        )
                    }
                }
            }
        }
    }

    private fun handleOnUserSignedIn() {
        //navigate here with proper userType
        if(userType == "owner") {
            eventChannel.trySend(SignInViewModelEvent.SignInAsOwnerSuccessful)
        } else if(userType == "worker") {
            eventChannel.trySend(SignInViewModelEvent.SignInAsWorkerSuccessful)
        }
    }
}



sealed interface SignInScreenEvent {
    data class OnUserEmailChanged(val email: String): SignInScreenEvent
    data class OnUserPasswordChanged(val password: String): SignInScreenEvent
    data object OnSignInClicked: SignInScreenEvent
    data object OnUserSignedIn: SignInScreenEvent
}

sealed interface SignInViewModelEvent {
    data object SignInAsOwnerSuccessful: SignInViewModelEvent
    data object SignInAsWorkerSuccessful: SignInViewModelEvent
}