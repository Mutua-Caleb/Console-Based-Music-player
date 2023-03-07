class User(val username: String, private val password: String) {
    fun authenticate(inputPassword: String): Boolean {
        return inputPassword == password
    }
}