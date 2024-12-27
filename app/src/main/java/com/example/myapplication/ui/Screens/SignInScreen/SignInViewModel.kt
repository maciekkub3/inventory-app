package com.example.myapplication.ui.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.firebase.FirebaseAuthClient
import com.example.myapplication.ui.Screens.SignInScreen.SignInScreenState
import com.google.firebase.auth.FirebaseAuth
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

    private val auth : FirebaseAuth = FirebaseAuth.getInstance()

    private val _state = MutableStateFlow(SignInScreenState())
    val state = _state.asStateFlow()

    private val eventChannel = Channel<SignInViewModelEvent>()
    val events = eventChannel.receiveAsFlow()

    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState

    private var userType: String? = null

    fun onEvent(event: SignInScreenEvent) {
        when (event) {
            is SignInScreenEvent.OnUserEmailChanged -> handleOnUserEmailChanged(event.email)
            is SignInScreenEvent.OnUserPasswordChanged -> handleOnUserPasswordChanged(event.password)
            is SignInScreenEvent.OnSignInClicked -> handleOnSignInClicked()
            is SignInScreenEvent.OnUserSignedIn -> handleOnUserSignedIn(userType)
            else -> {}
        }
    }

    init{
        checkAuthStatus()
    }


    fun checkAuthStatus() {
        if (auth.currentUser == null) {
            _authState.value = AuthState.Unauthenticated
        } else {
            _authState.value = AuthState.Authenticated
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
            println("handleOnSignInClicked called") // Debug log

            firebaseAuthRepository.signInWithEmail(
                email = state.value.userEmail,
                password = state.value.userPassword,
            ).collect { result ->
                println("Firebase sign-in result: $result") // Debug log

                if (result.isSuccess) {
                    val userId = firebaseAuthRepository.getCurrentUser()?.uid
                    if (userId != null) {
                        firebaseAuthRepository.getUserType(userId).collect { userTypeResult ->
                            println("User type result: $userTypeResult") // Debug log

                            if (userTypeResult.isSuccess) {
                                val userType = userTypeResult.getOrNull()
                                _state.update {
                                    it.copy(
                                        isSignedIn = true,
                                        userType = userType
                                    )
                                }
                                handleOnUserSignedIn(userType)
                            } else {
                                // Handle error
                                _state.update {
                                    it.copy(
                                        isSignedIn = false
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun handleOnUserSignedIn(userType: String?) {
        //navigate here with proper userType
        if(userType == "Admin") {
            eventChannel.trySend(SignInViewModelEvent.SignInAsOwnerSuccessful)
        } else if(userType == "Worker") {
            eventChannel.trySend(SignInViewModelEvent.SignInAsWorkerSuccessful)
        }
    }
}


sealed class AuthState {
    object Authenticated: AuthState()
    object Unauthenticated: AuthState()
    object Loading: AuthState()
    data class Error(val message: String): AuthState()
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
