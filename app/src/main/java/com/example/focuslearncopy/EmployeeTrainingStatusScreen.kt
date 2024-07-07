package com.example.focuslearn

import MockFirebaseRepository
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.focuslearn.model.Repository
import com.example.focuslearn.model.TrainingEmployee
import kotlinx.coroutines.launch

@Composable
fun EmployeeTrainingStatusScreen(navController: NavHostController, repository: Repository) {
    var employees by remember { mutableStateOf<List<TrainingEmployee>>(emptyList()) }
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    var isMenuExpanded by remember { mutableStateOf(false) }
    var currentPage by remember { mutableStateOf(1) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            employees = repository.fetchEmployees()
        }
    }

    // 현재 페이지에 해당하는 직원 목록 가져오기
    val currentEmployees = employees.chunked(20).getOrNull(currentPage - 1).orEmpty()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column {
            // 헤더 행과 아이콘들
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

            // 메뉴가 확장되면 표시될 카드
            AnimatedVisibility(
                visible = isMenuExpanded,
                enter = slideInHorizontally(initialOffsetX = { -it }) + fadeIn(),
                exit = slideOutHorizontally(targetOffsetX = { -it }) + fadeOut()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.5f) // 메뉴가 화면의 반만큼 너비를 차지하도록 설정
                        .padding(top = 40.dp) // 메뉴 버튼 크기만큼 위쪽 패딩 추가
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

            Spacer(modifier = Modifier.height(8.dp))

            // 직원 교육 현황 리스트 제목
            Text(
                text = "직원 교육 현황 리스트",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Blue,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 검색 바
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "검색", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                TextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    label = { Text("이름") },
                    modifier = Modifier.weight(1f).padding(horizontal = 8.dp)
                )
                FilterButton()
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 직원 추가 버튼
            Button(onClick = {
                scope.launch {
                    val newEmployee = TrainingEmployee(
                        id = "100${employees.size + 1}",
                        name = "새 직원 ${employees.size + 1}",
                        department = "새 부서",
                        position = "새 직급",
                        course = "새 교육과정",
                        status = "진행 중"
                    )
                    val success = repository.addEmployee(newEmployee)
                    if (success) {
                        employees = employees + newEmployee
                    }
                }
            }) {
                Text("새 직원 추가")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 직원 목록 헤더
            if (currentEmployees.isNotEmpty()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "직원 ID", fontWeight = FontWeight.Bold)
                    Text(text = "이름", fontWeight = FontWeight.Bold)
                    Text(text = "부서", fontWeight = FontWeight.Bold)
                    Text(text = "직급", fontWeight = FontWeight.Bold)
                    Text(text = "교육과정명", fontWeight = FontWeight.Bold)
                    Text(text = "진행상태", fontWeight = FontWeight.Bold)
                }

                Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))

                // 직원 목록
                currentEmployees.forEach { employee ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = employee.id)
                        Text(text = employee.name)
                        Text(text = employee.department)
                        Text(text = employee.position)
                        Text(text = employee.course)
                        Text(
                            text = employee.status,
                            color = if (employee.status == "완료") Color.Red else Color.Blue
                        )
                    }
                }
            } else {
                // 직원 정보가 없는 경우 빈 화면
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "표시할 직원 정보가 없습니다.")
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // 총 직원 수와 필터링 된 직원 수
            Text(
                text = "총 직원 수 : ${employees.size} , 필터링 된 직원 수 : ${currentEmployees.size}",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            // 페이지네이션
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Pagination(
                    totalPages = (employees.size + 19) / 20, // 총 페이지 수 계산
                    currentPage = currentPage,
                    onPageSelected = { currentPage = it }
                )
            }
        }
    }
}

@Composable
fun FilterButton() {
    Button(
        onClick = { /* Handle filter action */ },
        colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
    ) {
        Text("필터", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        Icon(
            imageVector = Icons.Default.ArrowDropDown,
            contentDescription = "Dropdown Arrow",
            tint = Color.Black // 아이콘 색상 설정
        )
    }
}

@Composable
fun Pagination(
    totalPages: Int,
    currentPage: Int,
    onPageSelected: (Int) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        for (page in 1..totalPages) {
            TextButton(onClick = { onPageSelected(page) }) {
                Text(text = "$page", fontWeight = if (page == currentPage) FontWeight.Bold else FontWeight.Normal)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EmployeeTrainingStatusScreenPreview() {
    val navController = rememberNavController()
    val mockRepository = MockFirebaseRepository()

    EmployeeTrainingStatusScreen(navController, mockRepository)
}
