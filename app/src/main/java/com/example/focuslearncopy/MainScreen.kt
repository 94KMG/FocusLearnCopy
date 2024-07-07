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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun MainScreen(navController: NavHostController) {
    var isMenuExpanded by remember { mutableStateOf(false) } // 메뉴 확장 상태를 추적하는 변수

    Box(
        modifier = Modifier
            .fillMaxSize() // 전체 화면을 채우는 Modifier
            .padding(16.dp) // 화면의 모든 가장자리에 16dp의 패딩 추가
    ) {
        Column {
            // 헤더 행
            Row(
                modifier = Modifier
                    .fillMaxWidth(), // 가로 방향으로 최대 너비 채움
                horizontalArrangement = Arrangement.SpaceBetween, // 수평 정렬을 양 끝으로 설정
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Menu, // 메뉴 아이콘 설정
                    contentDescription = "Menu", // 아이콘의 내용 설명
                    tint = Color.Black, // 아이콘 색상 검정색
                    modifier = Modifier
                        .size(40.dp) // 아이콘 크기를 40dp로 설정
                        .clickable { isMenuExpanded = !isMenuExpanded } // 클릭 시 메뉴 확장/축소
                )

                Icon(
                    imageVector = Icons.Default.Person, // 사람 아이콘 설정
                    contentDescription = "Profile", // 아이콘의 내용 설명
                    tint = Color.Unspecified, // 아이콘 색상 설정 안 함
                    modifier = Modifier
                        .size(40.dp) // 아이콘 크기를 40dp로 설정
                )
            }

            // 카드와 메뉴 아이템 간의 간격 조정을 위한 Spacer
            Spacer(modifier = Modifier.height(16.dp))

            // 메인 콘텐츠 카드
            Card(
                modifier = Modifier
                    .fillMaxWidth() // 가로 방향으로 최대 너비 채움
                    .background(Color.White, RoundedCornerShape(8.dp)) // 배경색과 모서리 둥글게 설정
                    .shadow(4.dp, RoundedCornerShape(8.dp)) // 그림자와 모서리 둥글게 설정
                    .clip(RoundedCornerShape(8.dp)) // 클립 모양 설정
                    .align(Alignment.Start), // 시작 부분에 정렬
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp) // 카드의 기본 높이 설정
            ) {
                Column(modifier = Modifier.padding(16.dp)) { // 카드 내부의 내용물에 패딩 추가
                    // 제목 행
                    Row(
                        modifier = Modifier.fillMaxWidth(), // 가로 방향으로 최대 너비 채움
                        horizontalArrangement = Arrangement.SpaceBetween, // 수평 정렬을 양 끝으로 설정
                        verticalAlignment = Alignment.CenterVertically // 수직 정렬을 중앙으로 설정
                    ) {
                        Text(
                            text = "FOCUS LEARN", // 제목 텍스트
                            color = Color.Blue, // 텍스트 색상 파란색
                            fontSize = 18.sp, // 텍스트 크기 18sp
                            fontWeight = FontWeight.Bold // 텍스트 굵게
                        )
                        Icon(
                            imageVector = Icons.Default.Menu, // 메뉴 아이콘 설정
                            contentDescription = "Menu", // 아이콘의 내용 설명
                            tint = Color.Black, // 아이콘 색상 검정색
                            modifier = Modifier.clickable { isMenuExpanded = !isMenuExpanded } // 클릭 시 메뉴 확장/축소
                        )
                    }
                    // "FOCUS LEARN" 아래에 "목록" 추가
                    Text(
                        text = "목록", // 부제목 텍스트
                        color = Color.Gray, // 텍스트 색상 회색
                        fontSize = 18.sp, // 텍스트 크기 18sp
                        fontWeight = FontWeight.Bold, // 텍스트 굵게
                        modifier = Modifier.padding(top = 8.dp, start = 0.dp) // 위쪽 패딩 8dp 추가
                    )
                }
            }
        }

        // "FOCUS LEARN" 아래에 메뉴 아이템 표시
        AnimatedVisibility(
            visible = isMenuExpanded,
            enter = slideInHorizontally(initialOffsetX = { -it }) + fadeIn(),
            exit = slideOutHorizontally(targetOffsetX = { -it }) + fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.5f) // 메뉴가 화면의 반만큼 너비를 차지하도록 설정
                    .padding(top = 48.dp) // 메뉴 버튼 크기만큼 위쪽 패딩 추가
                    .background(Color.White, RoundedCornerShape(8.dp)) // 배경색과 모서리 둥글게 설정
                    .shadow(4.dp, RoundedCornerShape(8.dp)) // 그림자와 모서리 둥글게 설정
                    .clip(RoundedCornerShape(8.dp)) // 클립 모양 설정
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxSize(), // 카드가 상자의 모든 공간을 차지하도록 설정
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp) // 카드의 기본 높이 설정
                ) {
                    Column(modifier = Modifier.padding(16.dp)) { // 카드 내부의 내용물에 패딩 추가
                        MenuItem(text = "직원 아이디 생성", navController = navController, destination = "newEmployeeScreen") // 메뉴 아이템 1
                        MenuItem(text = "직원 교육 현황 관리", navController = navController, destination = "employeeTrainingStatusScreen") // 메뉴 아이템 2
                        MenuItem(text = "교육 이슈 통계 및 보고서", navController = navController, destination = "employeeEduStatisticsScreen") // 메뉴 아이템 3
                        Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp)) // 구분선 추가
                        MenuItem(text = "공지사항 / 알림", navController = navController, destination = "notificationScreen") // 메뉴 아이템 4
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    val navController = rememberNavController() // NavController 생성
    MainScreen(navController = navController) // MainScreen 호출
}
