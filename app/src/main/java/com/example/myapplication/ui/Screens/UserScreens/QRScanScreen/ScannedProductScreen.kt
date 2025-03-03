
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.myapplication.ui.common.BackTopAppBar
import com.example.myapplication.ui.common.TwoButtonsBottomBar
import com.example.myapplication.ui.theme.DarkSlateGray

@Composable
fun ScannedProductScreen(
    warehouseId: String,
    scannedValue: String,
    onBack: () -> Unit,
    viewModel: ScannedProductViewModel = viewModel(),
    navController: NavController,
    context: Context = LocalContext.current // Getting context for Toast

) {
    val itemName by viewModel.itemName.collectAsState()
    val currentQuantity by viewModel.currentQuantity.collectAsState()
    val scannedQuantity by viewModel.scannedQuantity.collectAsState()
    val isUpdating by viewModel.isUpdating.collectAsState()
    val imageUrl by viewModel.imageUrl.collectAsState()

    LaunchedEffect(scannedValue) {
        viewModel.processScannedData(scannedValue, warehouseId)
        viewModel.fetchCurrentStock()
    }


    Scaffold(
        topBar = {
            BackTopAppBar(
                title = "Scanned Product",
                onBackClick = { navController.popBackStack() }
            )
        },
        bottomBar = {
            TwoButtonsBottomBar(
                firstColor = Color.Red,
                secondColor = Color.Green,
                firstText = "REMOVE",
                secondText = "ADD",
                onFirstButtonClick = {
                    // Decrement quantity logic
                    val quantityToRemove = scannedQuantity
                    if (quantityToRemove in 1..currentQuantity!!) {
                        viewModel.updateQuantity(quantityToRemove, true, navController)
                        Toast.makeText(context, "x$scannedQuantity $itemName successfully removed", Toast.LENGTH_SHORT).show()

                    } else if (quantityToRemove > currentQuantity!!) {
                        Toast.makeText(context, "Cannot remove more than the available quantity", Toast.LENGTH_SHORT).show()
                    }

                },
                onSecondButtonClick = {
                    // Increment quantity logic
                    val quantityToAdd = scannedQuantity
                    if (quantityToAdd > 0) {
                        viewModel.updateQuantity(quantityToAdd, false, navController)
                        Toast.makeText(context, "x$scannedQuantity $itemName successfully added", Toast.LENGTH_SHORT).show()

                    }
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkSlateGray)
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(10.dp))



            ScannedProductScreen(
                itemName = itemName,
                imageUrl = imageUrl,
                currentQuantity = currentQuantity,
                scannedQuantity = scannedQuantity
            )



        }
    }
}


@Composable
fun ScannedProductScreen(
    itemName: String,
    imageUrl: String?,
    currentQuantity: Int?,
    scannedQuantity: Int,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF3D4753)), // Dark background
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp))

            ) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = itemName,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(100.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = itemName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(80.dp)
                            .background(Color.Gray, shape = RoundedCornerShape(8.dp))
                    ) {
                        Text(
                            text = scannedQuantity.toString(),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "Pcs",
                        fontSize = 18.sp,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Current Stock: ${currentQuantity ?: "Loading..."} pcs",
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
        }
    }
}

