package com.example.myapplication.ui.Screens.UserScreens.ReportsScreen

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.firebase.FirebaseAuthClient
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Paragraph
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ReportsScreenViewModel @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthClient,
    private val savedStateHandle: SavedStateHandle

) : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()

    private var warehouseId: String? = null
    init{
        warehouseId = savedStateHandle.get<String>("warehouseId")
    }

    fun generateStockReport(context: Context) {
        val warehouseId = this.warehouseId

        // Check if the warehouse ID is valid
        if (warehouseId.isNullOrEmpty()) {
            println("Error: No warehouse ID found for the logged-in user.")
            return
        }

        // Get the current date in the desired format
        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val fileName = "CurrentStockReport_$currentDate.pdf" // Append the date to the file name
        val outputFile = File(context.getExternalFilesDir(null), fileName)
        val outputStream = FileOutputStream(outputFile)

        // Fetch the warehouse by ID
        firestore.collection("warehouses")
            .document(warehouseId)
            .get()
            .addOnSuccessListener { warehouseDocument ->
                if (warehouseDocument.exists()) {
                    val warehouseName = warehouseDocument.getString("name") ?: "Unknown Warehouse"

                    // Initialize PdfWriter and PdfDocument
                    val pdfWriter = PdfWriter(outputStream)
                    val pdfDocument = PdfDocument(pdfWriter)
                    val document = Document(pdfDocument)

                    // Add title, date, and warehouse info to the document
                    document.add(Paragraph("Current Stock Levels Report"))
                    document.add(Paragraph("\n"))
                    document.add(Paragraph("Report Date: $currentDate")) // Add report date
                    document.add(Paragraph("Warehouse: $warehouseName"))
                    document.add(Paragraph("===================================="))

                    // Fetch products within this warehouse
                    firestore.collection("warehouses")
                        .document(warehouseId)
                        .collection("products")
                        .get()
                        .addOnSuccessListener { productQuerySnapshot ->
                            productQuerySnapshot.forEach { productDocument ->
                                val productName = productDocument.getString("name") ?: "Unknown Product"
                                val productQuantity = productDocument.getLong("quantity") ?: 0
                                val productPrice = productDocument.getDouble("price") ?: 0.0

                                // Add product info to the PDF
                                document.add(Paragraph("Product: $productName"))
                                document.add(Paragraph("Quantity: $productQuantity"))
                                document.add(Paragraph("Price: $productPrice"))
                                document.add(Paragraph("\n"))
                            }

                            // Close the document after all products are added
                            document.close()
                            Toast.makeText(context, "PDF report generated successfully: ${outputFile.absolutePath}", Toast.LENGTH_SHORT).show()
                            println("PDF report generated successfully: ${outputFile.absolutePath}")
                        }
                        .addOnFailureListener { e ->
                            println("Error fetching products: ${e.message}")
                        }
                } else {
                    println("Error: Warehouse with ID $warehouseId does not exist.")
                }
            }
            .addOnFailureListener { e ->
                println("Error fetching warehouse: ${e.message}")
            }
    }
    

    fun generateLowStockReport(context: Context) {
        val warehouseId = this.warehouseId
        if (warehouseId.isNullOrEmpty()) {
            println("Error: No warehouse ID found for the logged-in user.")
            return
        }

        // Get the current date in the desired format
        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val fileName = "LowStockReport_$currentDate.pdf" // Append the date to the file name
        val outputFile = File(context.getExternalFilesDir(null), fileName)
        val outputStream = FileOutputStream(outputFile)

        // Fetch the warehouse by ID
        firestore.collection("warehouses")
            .document(warehouseId)
            .get()
            .addOnSuccessListener { warehouseDocument ->
                if (warehouseDocument.exists()) {
                    val warehouseName = warehouseDocument.getString("name") ?: "Unknown Warehouse"

                    // Initialize PdfWriter and PdfDocument
                    val pdfWriter = PdfWriter(outputStream)
                    val pdfDocument = PdfDocument(pdfWriter)
                    val document = Document(pdfDocument)

                    // Add title, date, and warehouse info to the document
                    document.add(Paragraph("Low Stock Report"))
                    document.add(Paragraph("\n"))
                    document.add(Paragraph("Report Date: $currentDate")) // Add report date
                    document.add(Paragraph("Warehouse: $warehouseName"))
                    document.add(Paragraph("===================================="))

                    // Fetch low stock products within this warehouse
                    firestore.collection("warehouses")
                        .document(warehouseId)
                        .collection("products")
                        .whereLessThan("quantity", 10) // Query for low stock items
                        .get()
                        .addOnSuccessListener { productQuerySnapshot ->
                            if (productQuerySnapshot.isEmpty) {
                                document.add(Paragraph("No products with low stock levels."))
                            } else {
                                productQuerySnapshot.forEach { productDocument ->
                                    val productName = productDocument.getString("name") ?: "Unknown Product"
                                    val productQuantity = productDocument.getLong("quantity") ?: 0
                                    val productPrice = productDocument.getDouble("price") ?: 0.0

                                    // Add product info to the PDF
                                    document.add(Paragraph("Product: $productName"))
                                    document.add(Paragraph("Quantity: $productQuantity"))
                                    document.add(Paragraph("Price: $productPrice"))
                                    document.add(Paragraph("\n"))
                                }
                            }

                            // Close the document after all products are added
                            document.close()
                            Toast.makeText(context, "Low stock PDF report generated successfully: ${outputFile.absolutePath}", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener { e ->
                            println("Error fetching low stock products: ${e.message}")
                        }
                } else {
                    println("Error: Warehouse with ID $warehouseId does not exist.")
                }
            }
            .addOnFailureListener { e ->
                println("Error fetching warehouse: ${e.message}")
            }
    }

    fun generateInventoryActivityReport(context: Context) {
        val warehouseId = this.warehouseId

        if (warehouseId.isNullOrEmpty()) {
            println("Error: No warehouse ID found for the logged-in user.")
            return
        }

        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val fileName = "InventoryActivityReport_$currentDate.pdf"
        val outputFile = File(context.getExternalFilesDir(null), fileName)
        val outputStream = FileOutputStream(outputFile)

        firestore.collection("warehouses")
            .document(warehouseId)
            .collection("history")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .limit(50)
            .get()
            .addOnSuccessListener { historySnapshot ->

                val pdfWriter = PdfWriter(outputStream)
                val pdfDocument = PdfDocument(pdfWriter)
                val document = Document(pdfDocument)

                document.add(Paragraph("Inventory Activity Report"))
                document.add(Paragraph("\n"))
                document.add(Paragraph("Report Date: $currentDate"))
                document.add(Paragraph("Warehouse ID: $warehouseId"))
                document.add(Paragraph("===================================="))

                historySnapshot.documents.forEach { documentSnapshot ->
                    val itemName = documentSnapshot.getString("itemName") ?: "Unknown Item"
                    val quantityChange = documentSnapshot.getLong("quantityChange") ?: 0
                    val timestamp = documentSnapshot.getLong("timestamp") ?: 0L

                    val date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date(timestamp))

                    document.add(Paragraph("Item: $itemName"))
                    document.add(Paragraph("Quantity Change: $quantityChange"))
                    document.add(Paragraph("Date: $date"))
                    document.add(Paragraph("\n"))
                }

                document.close()
                Toast.makeText(context, "PDF report generated successfully: ${outputFile.absolutePath}", Toast.LENGTH_SHORT).show()
                println("PDF report generated successfully: ${outputFile.absolutePath}")
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Failed to generate report: ${e.message}", Toast.LENGTH_LONG).show()
            }
    }

    fun generateRecentLoginsReport(context: Context) {
        val warehouseId = this.warehouseId
        if (warehouseId.isNullOrEmpty()) {
            println("Error: No warehouse ID found for the logged-in user.")
            return
        }

        firestore.collection("warehouses")
            .document(warehouseId)
            .collection("recentLogins")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .limit(20)
            .get()
            .addOnSuccessListener { recentLoginsSnapshot ->
                val outputFileName = "RecentLogins_${getCurrentDateForFilename()}.pdf"
                val outputFile = File(context.getExternalFilesDir(null), outputFileName)
                val outputStream = FileOutputStream(outputFile)

                val pdfWriter = PdfWriter(outputStream)
                val pdfDocument = PdfDocument(pdfWriter)
                val document = Document(pdfDocument)

                document.add(Paragraph("Recent Logins Report"))
                document.add(Paragraph("Date: ${getCurrentFormattedDate()}"))
                document.add(Paragraph("\n"))

                if (recentLoginsSnapshot.isEmpty) {
                    document.add(Paragraph("No recent logins found."))
                    document.close()
                    Toast.makeText(context, "PDF report generated successfully: ${outputFile.absolutePath}", Toast.LENGTH_SHORT).show()
                    return@addOnSuccessListener
                }

                val loginData = mutableListOf<Pair<String, String>>() // Pair<UserName, Email>
                var completedFetches = 0
                val totalFetches = recentLoginsSnapshot.size()

                recentLoginsSnapshot.forEach { loginDocument ->
                    val timestamp = loginDocument.getLong("timestamp") ?: 0L
                    val formattedDate = formatTimestampToDate(timestamp)
                    val userId = loginDocument.getString("userId") // Get the userId field

                    if (userId.isNullOrEmpty()) {
                        println("Error: Missing userId field in login document ${loginDocument.id}")
                        completedFetches++
                        if (completedFetches == totalFetches) {
                            // Write all data to the PDF after all fetches are complete
                            loginData.forEach { (userInfo, email) ->
                                document.add(Paragraph("User: $userInfo"))
                                document.add(Paragraph("Email: $email"))
                                document.add(Paragraph("\n"))
                            }
                            document.close()
                            Toast.makeText(context, "PDF report generated successfully: ${outputFile.absolutePath}", Toast.LENGTH_SHORT).show()
                            println("Recent Logins PDF generated successfully: ${outputFile.absolutePath}")
                        }
                        return@forEach
                    }

                    firestore.collection("users")
                        .document(userId)
                        .get()
                        .addOnSuccessListener { userSnapshot ->
                            val userName = userSnapshot.getString("name") ?: "Unknown User"
                            val userEmail = userSnapshot.getString("email") ?: "No Email Provided"
                            loginData.add(Pair("$userName (Login Time: $formattedDate)", userEmail))
                        }
                        .addOnFailureListener { e ->
                            println("Error fetching user data for userId $userId: ${e.message}")
                        }
                        .addOnCompleteListener {
                            completedFetches++
                            if (completedFetches == totalFetches) {
                                // Write all data to the PDF after all fetches are complete
                                loginData.forEach { (userInfo, email) ->
                                    document.add(Paragraph("User: $userInfo"))
                                    document.add(Paragraph("Email: $email"))
                                    document.add(Paragraph("\n"))
                                }
                                document.close()
                                Toast.makeText(context, "PDF report generated successfully: ${outputFile.absolutePath}", Toast.LENGTH_SHORT).show()
                                println("Recent Logins PDF generated successfully: ${outputFile.absolutePath}")
                            }
                        }
                }
            }
            .addOnFailureListener { e ->
                println("Error fetching recent logins: ${e.message}")
            }
    }



    // Formats the current timestamp for displaying in the report
    private fun getCurrentFormattedDate(): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        return dateFormat.format(Date())
    }

    // Formats the current timestamp for the filename
    private fun getCurrentDateForFilename(): String {
        val dateFormat = SimpleDateFormat("yyyyMMdd_HHmm", Locale.getDefault())
        return dateFormat.format(Date())
    }

    // Formats a Firestore timestamp into a readable date
    private fun formatTimestampToDate(timestamp: Long): String {
        val date = Date(timestamp)
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        return dateFormat.format(date)
    }






}
