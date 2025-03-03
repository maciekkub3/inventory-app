package com.example.myapplication.ui.Screens.UserScreens.History



import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.firebase.FirebaseAuthClient
import com.example.myapplication.domain.model.History
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class HistoryScreenViewModel @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthClient,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()


    private var warehouseId: String? = null

    private val _history = MutableStateFlow<List<History>>(emptyList())
    val history: StateFlow<List<History>> = _history





    init {
        warehouseId = savedStateHandle.get<String>("warehouseId")
        warehouseId?.let {
            fetchHistory(it) // Fetch the history when the ViewModel is initialized
        }
    }
    private fun fetchHistory(warehouseId: String) {
        viewModelScope.launch {
            try {
                // Fetch the history collection from Firestore
                val historySnapshot = firestore.collection("warehouses")
                    .document(warehouseId)
                    .collection("history")
                    .orderBy("timestamp", Query.Direction.DESCENDING) // Sort by timestamp descending
                    .limit(20) // Limit to 20 entries
                    .get()
                    .await()

                // Map Firestore documents to the HistoryEntry model
                val historyList = historySnapshot.documents.map { document ->
                    val itemName = document.getString("itemName") ?: ""
                    val quantityChange = document.getLong("quantityChange")?.toInt() ?: 0
                    val timestamp = document.getLong("timestamp") ?: 0L
                    val date = formatTimestampToDate(timestamp) // You can format the timestamp to a date string
                    History(itemName, quantityChange, timestamp, date)
                }

                // Update the StateFlow with the fetched history
                _history.value = historyList
            } catch (e: Exception) {
                // Handle any errors that occur during the fetching process
                // e.g., show a message to the user or log the error
            }
        }
    }

    private fun formatTimestampToDate(timestamp: Long): String {
        val date = java.util.Date(timestamp)
        val format = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
        return format.format(date)
    }
}


