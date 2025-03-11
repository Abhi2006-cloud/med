package com.example.med

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.med.models.Medicine
import com.example.med.ui.theme.MedicineLookupTheme
import com.example.med.viewmodels.MedicineViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // State to manage the theme (light/dark)
            var isDarkTheme by remember { mutableStateOf(false) }

            MedicineLookupTheme(darkTheme = isDarkTheme) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MedicineLookupApp(
                        isDarkTheme = isDarkTheme,
                        onThemeChange = { isDarkTheme = it }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicineLookupApp(
    viewModel: MedicineViewModel = viewModel(),
    isDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit
) {
    val searchQuery = remember { mutableStateOf("") }
    val currentMedicine by viewModel.currentMedicine.collectAsState()
    val similarMedicines by viewModel.similarMedicines.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Medicine Lookup") },
                actions = {
                    // Settings icon to toggle theme
                    IconButton(onClick = { onThemeChange(!isDarkTheme) }) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Toggle Theme"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Search Bar
            OutlinedTextField(
                value = searchQuery.value,
                onValueChange = { searchQuery.value = it },
                label = { Text("Enter Medicine Name") },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(onClick = {
                        if (searchQuery.value.isNotEmpty()) {
                            viewModel.searchMedicineByName(searchQuery.value)
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search"
                        )
                    }
                },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Loading indicator
            if (isLoading) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            // Error message
            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            // Chemical Formula Display
            currentMedicine?.let { medicine ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = medicine.name,
                            style = MaterialTheme.typography.titleLarge
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Chemical Formula: ${medicine.chemicalFormula}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = "Price: $${medicine.price}",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Similar Medicines List
            if (similarMedicines.isNotEmpty()) {
                Text(
                    text = "Medicines with Same Chemical Formula",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                LazyColumn {
                    items(similarMedicines) { medicine ->
                        MedicineItem(
                            medicine = medicine,
                            isRecommended = medicine == similarMedicines.first(), // Recommended flag
                            onClick = {
                                viewModel.selectMedicine(medicine)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MedicineItem(medicine: Medicine, isRecommended: Boolean = false, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(onClick = onClick),
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            // Recommended label
            if (isRecommended) {
                Text(
                    text = "Recommended",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }

            // Medicine name
            Text(
                text = medicine.name,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Brand and category
            Text(
                text = "Brand: ${medicine.brand}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Category: ${medicine.category}",
                style = MaterialTheme.typography.bodyMedium
            )

            // Price
            Text(
                text = "Price: $${medicine.price}",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewMedicineItem() {
    MedicineLookupTheme {
        MedicineItem(
            medicine = Medicine(
                name = "Aspirin",
                chemicalFormula = "C9H8O4",
                brand = "Bayer",
                category = "Pain Reliever",
                genericName = "Acetylsalicylic Acid",
                price = 10.0 // Add price
            ),
            isRecommended = true, // Add recommended flag
            onClick = {}
        )
    }
}

// Preview Composable for MedicineLookupApp
//@Preview(showBackground = true, device = "id:pixel_5")
//@Composable
//fun PreviewMedicineLookupApp() {
//    var isDarkTheme by remember { mutableStateOf(false) }
//
//    MedicineLookupTheme(darkTheme = isDarkTheme) {
//        MedicineLookupApp(
//            isDarkTheme = isDarkTheme,
//            onThemeChange = { isDarkTheme = it }
//        )
//    }
//}