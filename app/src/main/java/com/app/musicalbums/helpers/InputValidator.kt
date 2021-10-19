package com.app.musicalbums.helpers

object InputValidator {
    fun validateName(name: String): Boolean{
        return name.matches("^[a-zA-Z]*$".toRegex())
    }
}