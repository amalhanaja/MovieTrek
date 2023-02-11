package com.amalhanaja.movietrek.core.designsystem.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class ErrorComponentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testWithDefaultParams_shouldShowTitleTextActionTextAndErrorContentDoesNotExist() {
        // Arrange
        with(composeTestRule) {

            // Act
            setContent {
                ErrorComponent(title = "Title", actionText = "Action", onActionClick = { })
            }

            // Assert
            onNodeWithTag("error-description").assertDoesNotExist()
            onNodeWithText("Title").assertIsDisplayed()
            onNodeWithText("Action").assertIsDisplayed()
        }
    }

    @Test
    fun testClickAction_shouldTriggerOnActionClick() {
        // Arrange
        var isClicked = false
        with(composeTestRule) {
            setContent {
                ErrorComponent(title = "Title", actionText = "Action", onActionClick = { isClicked = true })
            }

            // Act
            onNodeWithText("Action").performClick()

            // Assert
            assertTrue(isClicked)
        }
    }

    @Test
    fun testAddDescription_shouldShowDescription() {
        // Arrange
        with(composeTestRule) {

            // Act
            setContent {
                ErrorComponent(
                    title = "Title",
                    actionText = "Action",
                    description = "Description",
                    onActionClick = { },
                )
            }

            // Assert
            onNodeWithText("Description").assertIsDisplayed()
        }
    }

    @Test
    fun testAddIllustration_shouldShowIllustrationContent() {
        // Arrange
        with(composeTestRule) {

            // Act
            setContent {
                ErrorComponent(
                    title = "Title",
                    actionText = "Action",
                    onActionClick = { },
                    illustration = {
                        Icon(Icons.Default.Warning, contentDescription = "Error Icon")
                    }
                )
            }

            // Assert
            onNodeWithContentDescription("Error Icon").assertIsDisplayed()
        }
    }
}