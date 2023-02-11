package com.amalhanaja.movietrek.core.designsystem.compnent

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import org.junit.Rule
import org.junit.Test

class LoadingComponentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testTextNull_loadingTextShouldDoesNotExist() {
        // Arrange
        with(composeTestRule) {
            // Act
            setContent { LoadingComponent() }

            //Assert
            onNodeWithTag("loading-text").assertDoesNotExist()
        }
    }

    @Test
    fun testTextIsNotNull_shouldShowExpectedText() {
        // Arrange
        with(composeTestRule) {
            // Act
            setContent { LoadingComponent(text = "Loading") }

            //Assert
            onNodeWithTag("loading-text").assertIsDisplayed()
                .assertTextEquals("Loading")
        }
    }
}