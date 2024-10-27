package com.example.data.repository

import com.example.model.product.Product
import kotlinx.coroutines.delay
import kotlinx.serialization.json.Json
import java.io.File


class ProductsRepository() {

    private val _productsText = File("assets/large_products.json").readText()
    private val _productsData = Json.decodeFromString<List<Product>>(_productsText)

    fun getProduct(productId: Int): Product? {
        return _productsData.firstOrNull { it.id == productId }
    }

    fun getProducts(): List<Product> {
        return _productsData
    }
}