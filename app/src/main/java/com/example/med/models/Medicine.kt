package com.example.med.models

data class Medicine(
    val name: String,
    val chemicalFormula: String,
    val brand: String,
    val category: String,
    val genericName: String,
    val price: Double // Add this property
)