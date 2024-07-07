package com.example.focuslearn.model

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

/**
 * FirebaseRepository는 Repository 인터페이스의 구체적인 구현체로,
 * Firebase Authentication과 Firestore를 사용하여 사용자 데이터를 관리합니다.
 */
class FirebaseRepository : Repository {
    // Firebase Authentication 인스턴스
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    // Firestore 데이터베이스 인스턴스
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    /**
     * Firestore 데이터베이스에서 사용자 이름으로 사용자를 검색합니다.
     * @param username 검색할 사용자 이름입니다.
     * @return User 객체를 반환하거나, 사용자가 존재하지 않으면 null을 반환합니다.
     */
    override suspend fun getUserByUsername(username: String): User? {
        return try {
            // "users" 컬렉션에서 사용자 이름이 일치하는 문서를 검색합니다.
            val snapshot = db.collection("users")
                .whereEqualTo("username", username)
                .get()
                .await()

            // 검색된 문서가 있으면 User 객체로 변환하여 반환합니다.
            if (snapshot.documents.isNotEmpty()) {
                snapshot.documents[0].toObject(User::class.java)
            } else {
                null
            }
        } catch (e: Exception) {
            // 예외가 발생하면 null을 반환합니다.
            null
        }
    }

    /**
     * Firebase Authentication을 사용하여 이메일과 비밀번호로 사용자 로그인을 시도합니다.
     * @param email 사용자의 이메일 주소입니다.
     * @param password 사용자의 비밀번호입니다.
     * @return 로그인 성공 시 true를, 실패 시 false를 반환합니다.
     */
    override suspend fun signIn(email: String, password: String): Boolean {
        return try {
            // 이메일과 비밀번호로 로그인 시도
            auth.signInWithEmailAndPassword(email, password).await()
            true // 로그인 성공 시 true 반환
        } catch (e: Exception) {
            false // 예외 발생 시 false 반환
        }
    }

    /**
     * Firestore 데이터베이스에서 직원 데이터를 가져옵니다.
     * @return List<TrainingEmployee> 객체를 반환하거나, 예외가 발생하면 빈 리스트를 반환합니다.
     */
    override suspend fun fetchEmployees(): List<TrainingEmployee> {
        return try {
            val snapshot = db.collection("employees")
                .get()
                .await()

            snapshot.documents.mapNotNull { document ->
                document.toObject(TrainingEmployee::class.java)
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    /**
     * Firestore 데이터베이스에 새로운 직원 데이터를 추가합니다.
     * @param employee 추가할 TrainingEmployee 객체입니다.
     * @return 성공 시 true를, 실패 시 false를 반환합니다.
     */
    override suspend fun addEmployee(employee: TrainingEmployee): Boolean {
        return try {
            db.collection("employees")
                .add(employee)
                .await()
            true // 데이터 추가 성공 시 true 반환
        } catch (e: Exception) {
            false // 예외 발생 시 false 반환
        }
    }
}
