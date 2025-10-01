package data

data class LogInState(
    var username: String = "",
    var password: String = "",
    var isLoading: Boolean = false,
    var errorMessage: String? = null,
) {
    fun isNotEmpty(): Boolean {
        return username.isNotEmpty() && password.isNotEmpty()
    }
}