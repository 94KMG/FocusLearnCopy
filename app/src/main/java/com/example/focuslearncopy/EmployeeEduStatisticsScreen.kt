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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.Color
import com.example.focuslearn.MenuItem
import com.example.focuslearn.model.TrainingEmployee

@Composable
fun EmployeeEduStatisticsScreen(navController: NavHostController) {
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
            text = "임직원 교육 현황",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Blue,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth() // Ensure the text takes up the full width
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Subtitle
        Text(
            text = "7월 최근 한 달간의 교육 이슈 상태 변화",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Line Chart Placeholder
        Text(
            text = "Line Chart",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Dummy line chart
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color.White, RoundedCornerShape(8.dp))
                .shadow(4.dp, RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp))
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                drawLine(
                    color = Color.Black,
                    start = androidx.compose.ui.geometry.Offset(0f, size.height),
                    end = androidx.compose.ui.geometry.Offset(size.width, 0f),
                    strokeWidth = 5.dp.toPx(),
                    cap = StrokeCap.Round,
                    pathEffect = pathEffect
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Missing employees list
        Text(
            text = "미이수자 목록",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // List Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "이름", fontWeight = FontWeight.Bold)
            Text(text = "부서", fontWeight = FontWeight.Bold)
            Text(text = "고용 형태", fontWeight = FontWeight.Bold)
            Text(text = "미이수 교육 목록", fontWeight = FontWeight.Bold)
            Text(text = "미이수 사유", fontWeight = FontWeight.Bold)
        }

        Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))

        // Dummy Data for Missing Employees
        val missingEmployees = listOf(
            TrainingEmployee("1001", "김철수", "인사부", "사원", "개인정보보호법", "미완료"),
            TrainingEmployee("1002", "이영희", "영업부", "대리", "산업안전법", "미완료")
        )

        missingEmployees.forEach { employee ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = employee.name)
                Text(text = employee.department)
                Text(text = employee.position)
                Text(text = employee.course)
                Text(text = employee.status)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EmployeeEduStatisticsScreenPreview() {
    val navController = rememberNavController()
    EmployeeEduStatisticsScreen(navController)
}
