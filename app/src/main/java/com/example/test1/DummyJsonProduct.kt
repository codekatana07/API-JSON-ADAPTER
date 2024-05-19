package com.example.test1

data class DummyJsonProduct(
    val limit: Int,
    val products: List<Product>,
    val skip: Int,
    val total: Int
)