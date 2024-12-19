package com.example.myapplication.data.firebase

import com.example.myapplication.data.mapper.toDomain
import com.example.myapplication.data.model.ItemDbo
import com.example.myapplication.domain.model.Item
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.tasks.await


//collections
const val INVENTORY_HISTORY_COLLECTION = "Inventory_history"
const val ITEMS_COLLECTION = "Items"

//fields
const val ITEMS_WAREHOUSE_ID = "warehouseId"
const val ITEM_QUANTITY = "quantity"

class FirestoreClient {
    private val firestoreDatabase = Firebase.firestore

    fun updateItems(warehouseId: String, updatedItems: List<Item>) = callbackFlow<FirestoreEvent<Unit>> {
        val warehouseItems = getWarehouseItems(warehouseId).first().let {
            when(it) {
                is FirestoreEvent.Success -> it.result
                is FirestoreEvent.Failure -> throw it.error ?: Exception() //close(it.error)
            }
        }.toMutableList()
        warehouseItems.updateListWithNewItems(updatedItems).map {
            async {
                firestoreDatabase.collection(ITEMS_COLLECTION).document(it.id)
                    .update(ITEM_QUANTITY, it.quantity).await()
            }
        }.awaitAll()
    }

    private fun MutableList<Item>.updateListWithNewItems(newItems: List<Item>) : List<Item> {
        val warehouseItemsMap = this.associateBy { it.id }.toMutableMap()
        newItems.forEach { newItem ->
            val existingItem = warehouseItemsMap[newItem.id]
            val substituteItem = existingItem?.copy(
                quantity = existingItem.quantity + newItem.quantity
            ) ?: newItem
            warehouseItemsMap[newItem.id] = substituteItem
        }
        return warehouseItemsMap.values.toList()
    }

    fun addItem(item: Item) {
        firestoreDatabase.collection(ITEMS_COLLECTION).add(item)
            .addOnCompleteListener { task ->
                if(task.isSuccessful) {

                }
            }
    }

    fun getItem(itemId: String): Item? {
        return null
    }

    fun getWarehouseItems(warehouseId: String) = callbackFlow<FirestoreEvent<List<Item>>> {
        firestoreDatabase.collection(ITEMS_COLLECTION).whereEqualTo(
            ITEMS_WAREHOUSE_ID, warehouseId
        ).get().addOnCompleteListener { task ->
            if(task.isSuccessful) {
                val items = task.result.documents.mapNotNull { document ->
                    document.toObject(ItemDbo::class.java)
                }.map { itemDbo ->
                    itemDbo.toDomain()
                }
                trySend(FirestoreEvent.Success(items))
            } else {
                trySend(FirestoreEvent.Failure(error = task.exception))
            }
        }
    }
}

sealed interface FirestoreEvent<out T> {
    data class Success<out T>(val result: T): FirestoreEvent<T>
    data class Failure(
        val error: Throwable? = null,
        val reason: String? = null
    ) : FirestoreEvent<Nothing>
}