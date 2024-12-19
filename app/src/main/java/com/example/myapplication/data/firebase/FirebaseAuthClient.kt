package com.example.myapplication.data.firebase

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class FirebaseAuthClient {

    private val auth = Firebase.auth
    private val _event: MutableSharedFlow<AuthEvent> =
        MutableSharedFlow(extraBufferCapacity = 1)
    val event: SharedFlow<AuthEvent> = _event

   fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _event.tryEmit(AuthEvent.SignedUp(auth.currentUser?.uid ?: "", email))
                } else {
                    _event.tryEmit(AuthEvent.SignUpUnsuccessful(task.exception ?: Exception()))
                }
            }
    }

    fun signInWithEmail(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _event.tryEmit(AuthEvent.SignedIn(auth.currentUser?.uid ?: "", email))
                } else {
                    _event.tryEmit(AuthEvent.SignInUnsuccessful(task.exception ?: Exception()))
                }
            }
    }

    fun signOut() {
        auth.signOut()
        _event.tryEmit(AuthEvent.SignedOut)
    }

    fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }
}