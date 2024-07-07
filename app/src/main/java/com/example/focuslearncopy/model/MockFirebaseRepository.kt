import com.example.focuslearn.model.Repository
import com.example.focuslearn.model.TrainingEmployee
import com.example.focuslearn.model.User

// 프리뷰를 위한 MockFirebaseRepository 클래스
class MockFirebaseRepository : Repository {
    override suspend fun getUserByUsername(username: String): User? {
        return User(username = username, email = "test@example.com")
    }

    override suspend fun signIn(email: String, password: String): Boolean {
        return email == "test@example.com" && password == "password"
    }

    override suspend fun fetchEmployees(): List<TrainingEmployee> {
        return listOf(
            TrainingEmployee("1001", "김철수", "인사부", "사원", "개인정보보호법", "완료"),
            TrainingEmployee("1002", "이영희", "영업부", "대리", "산업안전법", "진행 중")
        )
    }

    override suspend fun addEmployee(employee: TrainingEmployee): Boolean {
        return true
    }
}