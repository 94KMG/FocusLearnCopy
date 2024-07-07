package com.example.focuslearn

import MockFirebaseRepository
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.focuslearn.model.FirebaseRepository
import com.example.focuslearn.model.Repository
import com.example.focuslearn.ui.theme.FocusLearnTheme
import kotlinx.coroutines.launch

@Composable
fun LogIn(navController: NavHostController? = null, repository: Repository? = FirebaseRepository()) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loginResult by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "FOCUS LEARN",
            color = Color.Blue,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp),
            textAlign = TextAlign.Center
        )

        Text(
            text = "법정 의무 교육 플랫폼에 오신 것을 환영합니다",
            color = Color.Black,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 90.dp),
            textAlign = TextAlign.Center
        )

        CustomTextInputField(
            label = "ID 입력",
            value = username,
            onValueChange = { username = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .background(Color(0xFFEBF0FF))
                .clip(RoundedCornerShape(8.dp))
        )

        CustomTextInputField(
            label = "pw 입력",
            value = password,
            onValueChange = { password = it },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .background(Color(0xFFEBF0FF))
                .clip(RoundedCornerShape(8.dp))
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 20.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "회원가입",
                color = Color.Blue,
                modifier = Modifier
                    .clickable { navController?.navigate("signUp") }
                    .padding(top = 16.dp),
                textAlign = TextAlign.Right,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Button(
            onClick = {
                if (username.isEmpty() || password.isEmpty()) {
                    loginResult = "아이디와 비밀번호를 모두 입력해주세요"
                } else {
                    coroutineScope.launch {
                        logInUser(username, password, repository, navController, onResult = { loginResult = it })
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 16.dp)
                .shadow(elevation = 4.dp, shape = RoundedCornerShape(8.dp)),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
        ) {
            Text(text = "로그인", color = Color.White)
        }

        loginResult?.let {
            Spacer(modifier = Modifier.height(16.dp))
            Text(it)
        }
    }
}

@Composable
fun CustomTextInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        visualTransformation = visualTransformation,
        modifier = modifier
    )
}

suspend fun logInUser(
    username: String,
    password: String,
    repository: Repository?,
    navController: NavHostController?,
    onResult: (String?) -> Unit
) {
    repository?.let {
        try {
            val user = it.getUserByUsername(username)
            if (user != null) {
                val success = it.signIn(user.email, password)
                onResult(if (success) {
                    navController?.navigate("mainScreen")
                    "로그인 성공"
                } else {
                    "유효하지 않은 비밀번호 입니다"
                })
            } else {
                onResult("사용자를 찾을 수 없습니다")
            }
        } catch (e: Exception) {
            Log.e("LogInScreen", "로그인 에러", e)
            onResult("Login error: ${e.message}")
        }
    }
}


@Composable
fun AppNavigator() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LogIn(navController, repository = MockFirebaseRepository()) }
        composable("mainScreen") { MainScreen(navController) }
        composable("signUp") { SignUpScreen(navController) }
        composable("shared") { MenuItem(text = "", navController = navController, destination = "") }
    }
}

@Preview(showBackground = true)
@Composable
fun LogInScreenPreview() {
    FocusLearnTheme {
        AppNavigator()
    }
}
