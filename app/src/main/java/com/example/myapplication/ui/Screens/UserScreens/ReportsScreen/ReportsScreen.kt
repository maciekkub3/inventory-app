package com.example.myapplication.ui.Screens.UserScreens.ReportsScreen


import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.ui.common.BackTopAppBar
import com.example.myapplication.ui.theme.Dark
import com.example.myapplication.ui.theme.DarkSlateGray
import com.example.myapplication.ui.theme.GrayishBlue


@Composable
fun ReportsScreen(
    navController: NavController,
    warehouseId: String,
    context: Context,
    viewModel: ReportsScreenViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            BackTopAppBar(
                onBackClick = { navController.popBackStack() },
                title = "Reports"
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkSlateGray)
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = 20.dp, horizontal = 20.dp)
            ) {
                ReportsButton("Current stock levels", onClick = {viewModel.generateStockReport(context)})
                ReportsButton("Low stock report", onClick = {viewModel.generateLowStockReport(context)})
                ReportsButton("Inventory activity history", onClick = {viewModel.generateInventoryActivityReport(context)})
                ReportsButton("Recent logins", onClick = {viewModel.generateRecentLoginsReport(context)})

            }
        }
    }


}


@Composable
fun ReportsButton(text: String, onClick: () -> Unit) {
    ElevatedButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(containerColor = GrayishBlue),
        shape = RoundedCornerShape(15.dp),
        elevation = ButtonDefaults.elevatedButtonElevation(
            defaultElevation = 1.dp,
            pressedElevation = 0.dp
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = text,
                textAlign = TextAlign.Start,
                color = Color.White,
                modifier = Modifier
                    .padding(start = 8.dp)
            )

            Box(
                modifier = Modifier
                    .height(30.dp)
                    .width(70.dp)
                    .shadow(
                        elevation = 4.dp,
                        shape = RoundedCornerShape(11.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = Color.White,
                        shape = RoundedCornerShape(11.dp)
                    )
                    .background(
                        Dark,
                        RoundedCornerShape(11.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,                ) {
                    Text(
                        text = "PDF",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Image(
                        painter = painterResource(R.drawable.icons8_download),
                        contentDescription = "Download",
                        modifier = Modifier.size(18.dp)
                    )
                }

            }
        }
    }
}

@Preview
@Composable
fun reportButton() {
    ReportsButton("Current stock levels", onClick = { })
}

