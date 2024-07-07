package com.example.focuslearn

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun NotificationScreen(navController: NavHostController) {
    var isMenuExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header Row with Icons
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu",
                tint = Color.Black,
                modifier = Modifier
                    .size(40.dp)
                    .clickable { isMenuExpanded = !isMenuExpanded }
            )

            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Profile",
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(40.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (isMenuExpanded) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(8.dp))
                    .shadow(4.dp, RoundedCornerShape(8.dp))
                    .clip(RoundedCornerShape(8.dp)),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    MenuItem(
                        text = "직원 아이디 생성",
                        navController = navController,
                        destination = "newEmployeeScreen"
                    )
                    MenuItem(
                        text = "직원 교육 현황 관리",
                        navController = navController,
                        destination = "employeeTrainingStatusScreen"
                    )
                    MenuItem(
                        text = "교육 이슈 통계 및 보고서",
                        navController = navController,
                        destination = "employeeEduStatisticsScreen"
                    )
                    Divider(
                        color = Color.Gray,
                        thickness = 1.dp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    MenuItem(
                        text = "공지사항 / 알림",
                        navController = navController,
                        destination = "notificationScreen"
                    )
                }
            }
        }

        // Centered Title
        Text(
            text = "공지사항 / 알림",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Blue,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(7.dp))

        // Subtitle
        Text(
            text = "새로운 교육을 경험하다",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NotificationScreenPreview() {
    val navController = rememberNavController()
    NotificationScreen(navController)
}


