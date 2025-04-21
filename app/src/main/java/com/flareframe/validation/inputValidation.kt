package com.flareframe.validation

public  class inputValidation {
    companion object {
        public fun validateUsername(username: String): Boolean {      // used to check username
            val pattern =
                "^(?=.*[A-Z])(?!^[0-9])(?!.*[^a-zA-Z0-9._])(?=.*[0-9])[a-zA-Z0-9._]{6,}$".toRegex()
            return pattern.matches(username)
        }

        fun validatePassword(password: String): Boolean {
            val pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?!.*\\s).{8,}\$".toRegex()
            return pattern.matches(password)
        }




        fun validateEmail(email: String): Boolean {
            if (email.contains('@') && email.length > 2) {
                return true
            }
            return false
        }
    }
}