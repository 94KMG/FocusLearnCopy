package com.example.focuslearn

import androidx.compose.animation.*
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.focuslearn.MenuItem

data class Employee(
    val name: String,
    val id: String,
    val phone: String,
    val birthDate: String,
    val department: String
)

@Composable
fun NewEmployeeScreen(navController: NavHostController) {
    val employees = listOf(
        Employee("강민구", "lo1002", "010.8484.3940", "73.01.03", "영업"),
        Employee("이명상", "dj1002", "010.8484.3940", "73.01.03", "영업"),
        Employee("이진원", "lo1002", "010.8484.3940", "73.01.03", "영업"),
        Employee("설경인", "lo1002", "010.8484.3940", "73.01.03", "영업"),
        Employee("성다희", "lo1002", "010.8484.3940", "73.01.03", "영업"),
        Employee("박용준", "lo1002", "010.8484.3940", "73.01.03", "영업")
    )

    var isMenuExpanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column {
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

            // Main Content Card
            Card(
                modifier = Modifier
                    .fillMaxWidth() // Ensure the card takes full width
                    .background(Color.White, RoundedCornerShape(8.dp))
                    .shadow(4.dp, RoundedCornerShape(8.dp))
                    .clip(RoundedCornerShape(8.dp))
                    .padding(top = 8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "새 직원 등록",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Blue,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "이름", fontWeight = FontWeight.Bold)
                        Text(text = "아이디", fontWeight = FontWeight.Bold)
                        Text(text = "전화번호", fontWeight = FontWeight.Bold)
                        Text(text = "생년월일", fontWeight = FontWeight.Bold)
                        Text(text = "부서", fontWeight = FontWeight.Bold)
                    }
                    employees.forEach { employee ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = employee.name)
                            Text(text = employee.id)
                            Text(text = employee.phone)
                            Text(text = employee.birthDate)
                            Text(text = employee.department)
                        }
                    }
                }
            }
        }

        // Display the menu items sliding in from the left
        AnimatedVisibility(
            visible = isMenuExpanded,
            enter = slideInHorizontally(initialOffsetX = { -it }) + fadeIn(),
            exit = slideOutHorizontally(targetOffsetX = { -it }) + fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.5f) // Ensure the menu takes half the width of the screen
                    .padding(top = 40.dp) // Move the menu down by the height of the menu button
                    .background(Color.White, RoundedCornerShape(8.dp))
                    .shadow(4.dp, RoundedCornerShape(8.dp))
                    .clip(RoundedCornerShape(8.dp))
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxSize(),
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
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewEmployeeScreenPreview() {
    val navController = rememberNavController()
    NewEmployeeScreen(navController)
}
