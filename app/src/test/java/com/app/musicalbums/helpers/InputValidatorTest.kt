package com.app.musicalbums.helpers

import com.app.musicalbums.helpers.InputValidator.validateName
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class InputValidatorTest {
    @Test
    fun nameValidationCorrect(){
       assertTrue(validateName("Dooa"))
    }

    @Test
    fun nameValidationInCorrect(){
        assertFalse(validateName(" Dooa+/"))
    }
}