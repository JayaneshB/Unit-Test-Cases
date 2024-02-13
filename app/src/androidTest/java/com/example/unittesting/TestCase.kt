package com.example.unittesting

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TestCase {

    @get: Rule
    val rule = createComposeRule()

    @Before
    fun initTestSettings() {
        rule.setContent { Testing() }
    }
    @Test
    fun testField() {

        val firstName = "AndroidDeveloper!"
//        val lastName = "Balakrishnan"
        rule.onNodeWithTag("firstName_tag")
        assert(firstName.length > 5 && firstName.any { !it.isLetterOrDigit() })
//        assert(lastName.length > 5 && lastName.any { it.isLowerCase() } && lastName.any { it.isUpperCase() } && lastName.any { !it.isLetterOrDigit() })

    }

    @Test
    fun isValidPassword() {
        val password = "AndroidDeveloper@12"
        assert(PasswordValidator.isValidPassword(password))
    }
}



