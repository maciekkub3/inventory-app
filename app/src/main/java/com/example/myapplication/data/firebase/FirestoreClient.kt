package com.example.myapplication.data.firebase

import com.example.myapplication.domain.model.Item
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


//collections
const val INVENTORY_HISTORY_COLLECTION = "Inventory_history"
const val ITEMS_COLLECTION = "Items"

//fields
const val ITEMS_WAREHOUSE_ID = "warehouseId"
const val ITEM_QUANTITY = "quantity"

class FirestoreClient {
    private val firestoreDatabase = Firebase.firestore


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



    fun getItem(itemId: String): Item? {
        return null
    }


}

sealed interface FirestoreEvent<out T> {
    data class Success<out T>(val result: T): FirestoreEvent<T>
    data class Failure(
        val error: Throwable? = null,
        val reason: String? = null
    ) : FirestoreEvent<Nothing>
}