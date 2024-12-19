import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(onBackClick: () -> Unit) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Filled.ExitToApp, // Back arrow icon
                    contentDescription = "Back",
                )
            }
        },
        title = {
            Text(
                text = "Main menu",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF3C4C5E), // Custom background color
            titleContentColor = Color.White,   // Text color
            navigationIconContentColor = Color.White // Icon color
        )
    )
}

@Composable
fun CustomBottomBar() {
    BottomAppBar(
        containerColor = Color(0xFF3C4C5E), // Background color of the bottom bar
        contentPadding = PaddingValues(0.dp), // Remove padding if needed
        actions = {
            // Empty content to match a minimal bottom bar design
            Spacer(modifier = Modifier.height(56.dp))
        }
    )
}

@Composable
@Preview(showBackground = true)
fun PreviewCustomBottomBar() {
    Scaffold(
        bottomBar = { CustomBottomBar() }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.Black) // Background to simulate your UI
        )
    }
}


@Composable
@Preview(showBackground = true)
fun PreviewCustomTopAppBar() {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.Black) {
        Column {
            CustomTopAppBar(onBackClick = { /* Handle back */ })
        }
    }
}
