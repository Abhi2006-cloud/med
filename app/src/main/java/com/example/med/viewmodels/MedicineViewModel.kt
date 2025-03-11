package com.example.med.viewmodels

import androidx.lifecycle.ViewModel
import com.example.med.models.Medicine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MedicineViewModel : ViewModel() {
    private val _currentMedicine = MutableStateFlow<Medicine?>(null)
    val currentMedicine: StateFlow<Medicine?> = _currentMedicine.asStateFlow()

    private val _similarMedicines = MutableStateFlow<List<Medicine>>(emptyList())
    val similarMedicines: StateFlow<List<Medicine>> = _similarMedicines.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage.asStateFlow()

    // Expanded database with 100 chemical formulas and 4 medicines for each
    private val medicineDatabase = mutableListOf<Medicine>().apply {
        // Generate 100 chemical formulas
        for (i in 1..100) {
            val chemicalFormula = "C${i}H${i}O${i}" // Example formula
            val genericName = "GenericName$i"
            val category = "Category$i"

            // Add 4 medicines for each chemical formula with varying prices
            add(Medicine("Medicine${i}A", chemicalFormula, "BrandA$i", category, genericName, 10.0 + i))
            add(Medicine("Medicine${i}B", chemicalFormula, "BrandB$i", category, genericName, 15.0 + i))
            add(Medicine("Medicine${i}C", chemicalFormula, "BrandC$i", category, genericName, 20.0 + i))
            add(Medicine("Medicine${i}D", chemicalFormula, "BrandD$i", category, genericName, 25.0 + i))
        }

        // Add some real-world examples for testing
        addAll(
            listOf(
                // Chemical Formula: C9H8O4 (Aspirin)
                Medicine("Aspirin", "C9H8O4", "Bayer", "Pain Reliever", "Acetylsalicylic Acid", 10.0),
                Medicine("Generic Aspirin", "C9H8O4", "Generic", "Pain Reliever", "Acetylsalicylic Acid", 8.0),
                Medicine("Buffered Aspirin", "C9H8O4", "Bufferin", "Pain Reliever", "Acetylsalicylic Acid", 12.0),
                Medicine("Ecotrin", "C9H8O4", "Ecotrin", "Pain Reliever", "Acetylsalicylic Acid", 15.0),

                // Chemical Formula: C8H9NO2 (Paracetamol/Acetaminophen)
                Medicine("Acetaminophen", "C8H9NO2", "Tylenol", "Pain Reliever", "Paracetamol", 15.0),
                Medicine("Paracetamol", "C8H9NO2", "Generic", "Pain Reliever", "Paracetamol", 10.0),
                Medicine("Panadol", "C8H9NO2", "GSK", "Pain Reliever", "Paracetamol", 20.0),
                Medicine("Calpol", "C8H9NO2", "Johnson & Johnson", "Pain Reliever", "Paracetamol", 18.0),

                // Chemical Formula: C13H18O2 (Ibuprofen)
                Medicine("Ibuprofen", "C13H18O2", "Advil", "NSAID", "Ibuprofen", 20.0),
                Medicine("Motrin", "C13H18O2", "Motrin", "NSAID", "Ibuprofen", 25.0),
                Medicine("Generic Ibuprofen", "C13H18O2", "Generic", "NSAID", "Ibuprofen", 15.0),
                Medicine("Nurofen", "C13H18O2", "Reckitt Benckiser", "NSAID", "Ibuprofen", 22.0),

                // Chemical Formula: C14H14O3 (Naproxen)
                Medicine("Naproxen", "C14H14O3", "Aleve", "NSAID", "Naproxen Sodium", 25.0),
                Medicine("Naprosyn", "C14H14O3", "Roche", "NSAID", "Naproxen Sodium", 30.0),
                Medicine("Generic Naproxen", "C14H14O3", "Generic", "NSAID", "Naproxen Sodium", 20.0),
                Medicine("Anaprox", "C14H14O3", "Syntex", "NSAID", "Naproxen Sodium", 28.0)
            )
        )
    }

    fun searchMedicineByName(query: String) {
        _isLoading.value = true
        _errorMessage.value = ""

        try {
            // Simulate network delay
            Thread.sleep(500)

            // Find the medicine by name (case-insensitive search)
            val foundMedicine = medicineDatabase.find {
                it.name.contains(query, ignoreCase = true)
            }

            if (foundMedicine != null) {
                // Set the current medicine
                _currentMedicine.value = foundMedicine

                // Find similar medicines with the same chemical formula
                val similarMeds = medicineDatabase.filter {
                    it.chemicalFormula == foundMedicine.chemicalFormula && it.name != foundMedicine.name
                }

                // Sort similar medicines by price (low to high)
                _similarMedicines.value = similarMeds.sortedBy { it.price }
            } else {
                // If no medicine is found, show an error message
                _errorMessage.value = "Medicine not found"
                _currentMedicine.value = null
                _similarMedicines.value = emptyList()
            }
        } catch (e: Exception) {
            // Handle any errors
            _errorMessage.value = "Error: ${e.message}"
        } finally {
            // Stop loading
            _isLoading.value = false
        }
    }

    fun selectMedicine(medicine: Medicine) {
        // Set the selected medicine as the current medicine
        _currentMedicine.value = medicine

        // Find similar medicines with the same chemical formula
        val similarMeds = medicineDatabase.filter {
            it.chemicalFormula == medicine.chemicalFormula && it.name != medicine.name
        }

        // Sort similar medicines by price (low to high)
        _similarMedicines.value = similarMeds.sortedBy { it.price }
    }
}