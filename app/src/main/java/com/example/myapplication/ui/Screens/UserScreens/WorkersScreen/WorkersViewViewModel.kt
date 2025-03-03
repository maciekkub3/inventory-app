package com.example.myapplication.ui.Screens.OwnerScreens.UsersScreen.UserViewScreen




import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.firebase.FirebaseAuthClient
import com.example.myapplication.ui.Screens.UserScreens.WorkersScreen.WorkerViewScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class WorkersViewViewModel @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthClient,
    private val savedStateHandle: SavedStateHandle

) : ViewModel() {

    private val _state = MutableStateFlow(WorkerViewScreenState())
    val state: StateFlow<WorkerViewScreenState> = _state

    init {
        val userId = savedStateHandle.get<String>("userId")
        userId?.let {
            fetchUserDetails(it)
        }
    }

    fun fetchUserDetails(userId: String) {
        firebaseAuthRepository.getUserById(userId) { worker ->
            if (worker != null) {
                _state.update {
                    WorkerViewScreenState(
                        name = worker.name,
                        email = worker.email,
                        phoneNumber = worker.phoneNumber,
                        address = worker.address,
                        imageUri = worker.imageUrl.let { Uri.parse(it) } // Convert String to Uri

                    )
                }
            } else {
                // Handle error or empty state
            }
        }
    }






}






