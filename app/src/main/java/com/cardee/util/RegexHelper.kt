package com.cardee.util


object RegexHelper {
    val passRegex: Regex = Regex("^(?=.*[0-9])(?=.*[a-zA-Z])(?=\\S+\$).{8,}\$")
    val emailRegex: Regex = Regex("^[-\\w.]+@([A-z0-9][-A-z0-9]+\\.)+[A-z]{2,4}\$")
}