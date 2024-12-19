package com.example.myapplication.data.mapper

import com.example.myapplication.data.model.ItemDbo
import com.example.myapplication.domain.model.Item

fun ItemDbo.toDomain() = Item(
    id = id,
    warehouseId = warehouseId,
    description = description,
    image_id = image_id,
    last_restock_value = last_restock_value,
    name = name,
    price = price,
    quantity = quantity
)

fun Item.toDbo() = ItemDbo(
    warehouseId = warehouseId,
    description = description,
    image_id = image_id,
    last_restock_value = last_restock_value,
    name = name,
    price = price,
    quantity = quantity
)