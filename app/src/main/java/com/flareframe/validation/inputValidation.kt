package com.flareframe.validation

public  class inputValidation {
    companion object {
/**
 * Validates the username.
 * Rules:
 * - At least one uppercase letter
 * - Cannot start with a number
 * - Only allows letters, numbers, dots, and underscores (no special characters)
 * - Must contain at least one digit
 * - Must be at least 6 characters long
 * @param username:String
 */
         fun validateUsername(username: String): Boolean {      // used to check username
            val pattern =
                "^(?=.*[A-Z])(?!^[0-9])(?!.*[^a-zA-Z0-9._])(?=.*[0-9])[a-zA-Z0-9._]{6,}$".toRegex()
            return pattern.matches(username)
        }
        /**
         * Validates the password.
         * Rules:
         * - At least one lowercase letter
         * - At least one uppercase letter
         * - At least one number
         * - No whitespace allowed
         * - Minimum 8 characters
         * @param password:String
         */
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