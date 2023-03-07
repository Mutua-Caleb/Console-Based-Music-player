class UserManager {
    private val users = mutableMapOf<String, User>()

    fun createUser(username: String, password: String): User {
        val user = User(username, password)
        users[username] = user
        return user
    }

    fun authenticate(username: String, password: String): Boolean {
        val user = users[username]
        return user?.authenticate(password) ?: false
    }
}